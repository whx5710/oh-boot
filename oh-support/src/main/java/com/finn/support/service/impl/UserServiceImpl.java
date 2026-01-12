package com.finn.support.service.impl;

import com.finn.core.utils.*;
import com.finn.framework.security.user.UserDetail;
import com.finn.support.enums.DataScopeEnum;
import com.finn.support.enums.UserStatusEnum;
import com.finn.support.mapper.RoleDataScopeMapper;
import com.finn.support.mapper.RoleMapper;
import com.finn.support.service.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.finn.core.cache.RedisCache;
import com.finn.core.cache.RedisKeys;
import com.finn.core.constant.CommonEnum;
import com.finn.framework.security.user.SecurityUser;
import com.finn.support.mapper.UserMapper;
import com.finn.support.enums.SuperAdminEnum;
import com.finn.support.query.RoleUserQuery;
import com.finn.support.query.UserQuery;
import com.finn.support.vo.UserExcelVO;
import com.finn.support.vo.UserVO;
import com.finn.support.convert.UserConvert;
import com.finn.core.exception.ServerException;
import com.finn.support.entity.UserEntity;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;

/**
 * 用户管理
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserRoleService userRoleService;
    private final UserPostService userPostService;
    private final RedisCache redisCache;
    private final UserMapper userMapper;
    private final DeptService deptService;
    private final RoleDataScopeMapper roleDataScopeMapper;
    private final RoleMapper roleMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private ParamsService paramsService;

    public UserServiceImpl(UserRoleService userRoleService, UserPostService userPostService,
                           RedisCache redisCache, UserMapper userMapper,
                           DeptService deptService, RoleDataScopeMapper roleDataScopeMapper,
                           RoleMapper roleMapper) {
        this.userRoleService = userRoleService;
        this.userPostService = userPostService;
        this.redisCache = redisCache;
        this.userMapper = userMapper;
        this.deptService = deptService;
        this.roleDataScopeMapper = roleDataScopeMapper;
        this.roleMapper = roleMapper;
    }

    @Override
    public PageResult<UserVO> page(UserQuery query) {
        // 分页查询
        Page<UserEntity> page = PageHelper.startPage(query.getPageNum(), query.getPageSize());
        // 数据列表
        List<UserEntity> list = userMapper.getList(query);
        List<UserVO> voList = UserConvert.INSTANCE.convertList(list);
        return new PageResult<>(voList, page.getTotal());
    }

    /**
     * 被锁定、待锁定的用户列表（由于有次数限制，此方法不判断多少次被锁定）
     * username 不为空，说明只查单个用户
     * @param query
     * @return
     */
    @Override
    public PageResult<UserVO> clockPage(UserQuery query) {
        Set<String> set = redisCache.keys(RedisKeys.getAuthCountKey("*"));
        if(!set.isEmpty()){
            List<String> names = new ArrayList<>();
            Set<String> tmp = new HashSet<>();
            for(String key: set){
                tmp.add(key.substring(key.lastIndexOf(":") + 1));
            }
            // 查具体某个账号
            if(!ObjectUtils.isEmpty(query.getUsername())){
                if(tmp.contains(query.getUsername())){
                    names.add(query.getUsername());
                }else{
                    return new PageResult<>(new ArrayList<UserVO>(),0);
                }
            }else{
                for(String key: set){
                    names.add(key.substring(key.lastIndexOf(":") + 1));
                }
            }
            query.setUserNames(names);
            return page(query);
        }else{
            return new PageResult<>(new ArrayList<UserVO>(),0);
        }
    }

    // 解锁用户
    @Override
    public void unlock(String userName) {
        if(ObjectUtils.isEmpty(userName) || userName.equals("*")){
            throw new ServerException("用户名不能为空或非法");
        }
        redisCache.delete(RedisKeys.getAuthCountKey(userName));
    }

    @Override
    public void save(UserVO vo) {
        // 密码验证
        passwordStrength(vo.getPassword());
        // 密码加密
        vo.setPassword(passwordEncoder.encode(vo.getPassword()));

        UserEntity entity = UserConvert.INSTANCE.convert(vo);
        entity.setSuperAdmin(SuperAdminEnum.NO.getValue());

        // 判断用户名是否存在
        UserEntity user = userMapper.getByUsername(entity.getUsername());
        if (user != null) {
            throw new ServerException("用户名已经存在");
        }

        // 判断手机号是否存在
        user = userMapper.getByMobile(entity.getMobile());
        if (user != null) {
            throw new ServerException("手机号已经存在");
        }
        if(entity.getStatus()==null){
            entity.setStatus(1);
        }
        // 保存用户
        //sysUserMapper.insertUser(entity);
        userMapper.insert(entity); // 保存用户-动态sql

        // 保存用户角色关系
        userRoleService.saveOrUpdate(entity.getId(), vo.getRoleIdList());

        // 更新用户岗位关系
        userPostService.saveOrUpdate(entity.getId(), vo.getPostIdList());
    }

    @Override
    public void update(UserVO vo) {
        AssertUtils.isNull(vo.getId(), "用户ID");
        // 如果密码不为空，则进行加密处理
        if (vo.getPassword() == null || vo.getPassword().isEmpty()) {
            vo.setPassword(null);
        } else {
            // 密码验证
            passwordStrength(vo.getPassword());
            vo.setPassword(passwordEncoder.encode(vo.getPassword()));
        }
        UserEntity entity = UserConvert.INSTANCE.convert(vo);

        // 判断用户名是否存在
        UserEntity user = userMapper.getByUsername(entity.getUsername());
        if (user != null && !user.getId().equals(entity.getId())) {
            throw new ServerException("用户名已经存在");
        }

        // 判断手机号是否存在
        user = userMapper.getByMobile(entity.getMobile());
        if (user != null && !user.getId().equals(entity.getId())) {
            throw new ServerException("手机号已经存在");
        }

        // 更新用户
        userMapper.updateById(entity);

        // 更新用户角色关系
        userRoleService.saveOrUpdate(entity.getId(), vo.getRoleIdList());

        // 更新用户岗位关系
        userPostService.saveOrUpdate(entity.getId(), vo.getPostIdList());
    }

    @Override
    public void delete(List<Long> idList) {
        // 删除用户
//        removeByIds(idList);
        idList.forEach(id -> {
            UserEntity entity = new UserEntity();
            entity.setId(id);
            entity.setDbStatus(0);
            userMapper.updateById(entity);
        });

        // 删除用户角色关系
        userRoleService.deleteByUserIdList(idList);

        // 删除用户岗位关系
        userPostService.deleteByUserIdList(idList);
    }

    @Override
    public UserVO getByMobile(String mobile) {
        UserEntity user = userMapper.getByMobile(mobile);
        return UserConvert.INSTANCE.convert(user);
    }

    /**
     * 获取用户信息，用户ID为空时，获取当前登录系统的用户信息
     * @param userId 用户ID
     * @return 用户信息
     */
    @Override
    public UserVO info(Long userId) {
        UserVO user;
        if(ObjectUtils.isEmpty(userId)){
            user = UserConvert.INSTANCE.convert(SecurityUser.getUser());
            userId = user.getId();
        }
        // 重新查询，防止修改基本信息后，缓存未更新的情况
        UserEntity entity = userMapper.getById(userId);
        user = UserConvert.INSTANCE.convert(entity);
        // 用户角色列表
        List<Long> roleIdList = userRoleService.getRoleIdList(userId);
        user.setRoleIdList(roleIdList);

        // 用户岗位列表
        List<Long> postIdList = userPostService.getPostIdList(userId);
        user.setPostIdList(postIdList);
        return user;
    }

    /**
     * 根据用户ID获取用户
     * @param userId 用户ID
     * @return e
     */
    @Override
    public UserEntity getUser(Long userId) {
        AssertUtils.isNull(userId, "用户ID");
        return userMapper.getById(userId);
    }

    @Override
    public void updatePassword(Long id, String newPassword) {
        // 密码验证
        passwordStrength(newPassword);
        // 修改密码
        UserEntity user = userMapper.getById(id);
        newPassword = passwordEncoder.encode(newPassword);
        user.setPassword(newPassword);

        userMapper.updateById(user);
    }

    @Override
    public PageResult<UserVO> roleUserPage(RoleUserQuery query) {
        // 数据列表
        List<UserEntity> list = userMapper.getRoleUserList(query);
        List<UserVO> voList = UserConvert.INSTANCE.convertList(list);
        return new PageResult<>(voList, query.getTotal());
    }

    @Override
    public void importByExcel(MultipartFile file, String password) {

        /*ExcelUtils.readAnalysis(file, SysUserExcelVO.class, new ExcelFinishCallBack<SysUserExcelVO>() {
            @Override
            public void doAfterAllAnalysed(List<SysUserExcelVO> result) {
                saveUser(result);
            }

            @Override
            public void doSaveBatch(List<SysUserExcelVO> result) {
                saveUser(result);
            }

            private void saveUser(List<SysUserExcelVO> result) {
                List<SysUserEntity> sysUserEntities = SysUserConvert.INSTANCE.convertListEntity(result);
                sysUserEntities.forEach(user -> user.setPassword(password));
                sysUserEntities.forEach(sysUserMapper::insert);
            }
        });*/

    }

    @Override
    public void export(UserQuery query) {
        List<UserEntity> list = userMapper.getList(query);
        if(list == null || list.isEmpty()){
            throw new ServerException("无数据，导出失败");
        }
        List<UserExcelVO> userExcelVOS = UserConvert.INSTANCE.convert2List(list);
        ExcelUtils.excelExport(UserExcelVO.class, "用户信息", "用户信息", userExcelVOS);
    }

    /**
     * 解除绑定租户用户
     * @param tenantID 租户ID
     * @param userIdList 用户ID
     */
    @Override
    public void unBindTenantUser(String tenantID, List<Long> userIdList) {
        AssertUtils.isNull(userIdList, "用户ID");
        AssertUtils.isNull(tenantID, "租户ID");
        for(Long userId: userIdList){
            UserEntity user = userMapper.getById(userId);
            if(user == null || user.getId() == null){
                throw new ServerException("用户不存在");
            }
            if(user.getTenantId() == null || user.getTenantId().isEmpty()){
                throw new ServerException("用户未绑定租户，不能解绑");
            }
            if(!tenantID.equals(user.getTenantId())){
                throw new ServerException("租户ID不准确，不能解绑【" + user.getTenantId() + "】");
            }
            user.setTenantId(null);
            userMapper.unbindUser(user);// 解绑用户
        }
    }

    /**
     * 绑定租户用户
     * @param tenantID 租户ID
     * @param userIdList 用户ID
     */
    @Override
    public void bindTenantUser(String tenantID, List<Long> userIdList) {
        AssertUtils.isNull(userIdList, "用户ID");
        AssertUtils.isNull(tenantID, "租户ID");
        for(Long userId: userIdList){
            UserEntity user = userMapper.getById(userId);
            if(user == null || user.getId() == null){
                throw new ServerException("用户不存在");
            }
            if(user.getTenantId() != null && !user.getTenantId().isEmpty()){
                throw new ServerException("用户已存在其他租户中【" + user.getTenantId() + "】");
            }
            user.setTenantId(tenantID);
            userMapper.updateById(user);
        }
    }

    /**
     * 重置密码
     * @param userId 用户ID
     * @return 密码
     */
    @Override
    public String resetPwd(Long userId) {
        AssertUtils.isNull(userId, "用户ID");
        UserEntity user = userMapper.getById(userId);
        if(user == null || user.getId() == null){
            throw new ServerException("未找到对应的用户，重置密码失败！");
        }
        String pwd = Tools.getRandom(8);
        user.setPassword(passwordEncoder.encode(pwd));
        user.setUpdateTime(LocalDateTime.now());
        user.setUpdater(SecurityUser.getUserId());
        userMapper.updateById(user);
        return pwd;
    }

    @Override
    public UserDetails getUserDetails(UserEntity userEntity) {
        // 转换成UserDetail对象
        UserDetail userDetail = UserConvert.INSTANCE.convertDetail(userEntity);

        // 账号不可用
        if (userEntity.getStatus() == UserStatusEnum.DISABLE.getValue()) {
            userDetail.setEnabled(false);
        }

        // 数据权限范围
        List<Long> dataScopeList = getDataScope(userDetail);
        userDetail.setDataScopeList(dataScopeList);

        // 用户权限列表
        Set<String> authoritySet = userRoleService.getUserAuthority(userDetail);
        userDetail.setAuthoritySet(authoritySet);

        return userDetail;
    }

    @Override
    public UserEntity getByUsername(String username) {
        return userMapper.getByUsername(username);
    }

    /**
     * 密码校验
     * @param password 原始密码
     */
    private void passwordStrength(String password){
        // 密码验证等级
        int grade = paramsService.getDefaultInt("PASSWORD_GRADE");
        grade = grade==-1?3:grade;
        // 密码校验
        if(!AssertUtils.passwordStrength(grade, password)){
            throw new ServerException("密码: " + CommonEnum.getName(grade));
        }
    }

    /**
     * 获取数据范围
     * @param userDetail
     * @return
     */
    private List<Long> getDataScope(UserDetail userDetail) {
        Integer dataScope = roleMapper.getDataScopeByUserId(userDetail.getId());
        if (dataScope == null) {
            return new ArrayList<>();
        }

        if (dataScope.equals(DataScopeEnum.ALL.getValue())) {
            // 全部数据权限，则返回null
            return null;
        } else if (dataScope.equals(DataScopeEnum.ORG_AND_CHILD.getValue())) {
            // 本部门及子部门数据
            List<Long> dataScopeList = deptService.getSubDeptIdList(userDetail.getDeptId());
            // 自定义数据权限范围
            dataScopeList.addAll(roleDataScopeMapper.getDataScopeList(userDetail.getId()));

            return dataScopeList;
        } else if (dataScope.equals(DataScopeEnum.ORG_ONLY.getValue())) {
            // 本部门数据
            List<Long> dataScopeList = new ArrayList<>();
            dataScopeList.add(userDetail.getDeptId());
            // 自定义数据权限范围
            dataScopeList.addAll(roleDataScopeMapper.getDataScopeList(userDetail.getId()));

            return dataScopeList;
        } else if (dataScope.equals(DataScopeEnum.CUSTOM.getValue())) {
            // 自定义数据权限范围
            return roleDataScopeMapper.getDataScopeList(userDetail.getId());
        }

        return new ArrayList<>();
    }

    // 缓存用户信息
    /*
    public void init() {
        // 查询列表
        List<SysUserEntity> list = sysUserMapper.getList(new SysUserQuery());
        list.forEach(item -> {
            BaseUserEntity baseUser = SysUserConvert.INSTANCE.convertSimpleVO(item);
            String key = userCacheKey + baseUser.getId();
            if(redisCache.hasKey(key)){
                redisCache.delete(key);
            }
            // 缓存数据
            redisCache.set(key, baseUser);
        });
    }*/
}
