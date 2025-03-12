package com.finn.support.service.impl;

import com.finn.core.utils.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.finn.core.cache.RedisCache;
import com.finn.core.cache.RedisKeys;
import com.finn.core.constant.CommonEnum;
import com.finn.framework.security.user.SecurityUser;
import com.finn.support.cache.TenantCache;
import com.finn.support.mapper.SysUserMapper;
import com.finn.support.enums.SuperAdminEnum;
import com.finn.support.query.SysRoleUserQuery;
import com.finn.support.query.SysUserQuery;
import com.finn.support.vo.SysUserExcelVO;
import com.finn.support.vo.SysUserVO;
import com.finn.support.convert.SysUserConvert;
import com.finn.support.service.SysParamsService;
import com.finn.support.service.SysUserPostService;
import com.finn.support.service.SysUserService;
import com.finn.support.service.SysUserRoleService;
import com.finn.core.exception.ServerException;
import com.finn.support.entity.SysUserEntity;
import jakarta.annotation.Resource;
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
public class SysUserServiceImpl  implements SysUserService {
    private final SysUserRoleService sysUserRoleService;
    private final SysUserPostService sysUserPostService;
    private final RedisCache redisCache;
    private final SysUserMapper sysUserMapper;
    private final TenantCache tenantCache;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private SysParamsService sysParamsService;

    public SysUserServiceImpl(SysUserRoleService sysUserRoleService, SysUserPostService sysUserPostService,
                              RedisCache redisCache, SysUserMapper sysUserMapper, TenantCache tenantCache) {
        this.sysUserRoleService = sysUserRoleService;
        this.sysUserPostService = sysUserPostService;
        this.redisCache = redisCache;
        this.sysUserMapper = sysUserMapper;
        this.tenantCache = tenantCache;
    }

    @Override
    public PageResult<SysUserVO> page(SysUserQuery query) {
        // 分页查询
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        // 数据列表
        List<SysUserEntity> list = sysUserMapper.getList(query);
        PageInfo<SysUserEntity> pageInfo = new PageInfo<>(list);
        List<SysUserVO> voList = SysUserConvert.INSTANCE.convertList(pageInfo.getList());
        for(SysUserVO vo: voList){
            vo.setTenantName(tenantCache.getNameByTenantId(vo.getTenantId()));
        }
        return new PageResult<>(voList, pageInfo.getTotal());
    }

    /**
     * 被锁定、待锁定的用户列表（由于有次数限制，此方法不判断多少次被锁定）
     * username 不为空，说明只查单个用户
     * @param query
     * @return
     */
    @Override
    public PageResult<SysUserVO> clockPage(SysUserQuery query) {
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
                    return new PageResult<>(new ArrayList<SysUserVO>(),0);
                }
            }else{
                for(String key: set){
                    names.add(key.substring(key.lastIndexOf(":") + 1));
                }
            }
            query.setUserNames(names);
            return page(query);
        }else{
            return new PageResult<>(new ArrayList<SysUserVO>(),0);
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
    public void save(SysUserVO vo) {
        // 密码验证
        passwordStrength(vo.getPassword());
        // 密码加密
        vo.setPassword(passwordEncoder.encode(vo.getPassword()));

        SysUserEntity entity = SysUserConvert.INSTANCE.convert(vo);
        entity.setSuperAdmin(SuperAdminEnum.NO.getValue());

        // 判断用户名是否存在
        SysUserEntity user = sysUserMapper.getByUsername(entity.getUsername());
        if (user != null) {
            throw new ServerException("用户名已经存在");
        }

        // 判断手机号是否存在
        user = sysUserMapper.getByMobile(entity.getMobile());
        if (user != null) {
            throw new ServerException("手机号已经存在");
        }
        if(entity.getStatus()==null){
            entity.setStatus(1);
        }
        // 保存用户
        //sysUserMapper.insertUser(entity);
        sysUserMapper.insertUser(entity); // 保存用户-动态sql

        // 保存用户角色关系
        sysUserRoleService.saveOrUpdate(entity.getId(), vo.getRoleIdList());

        // 更新用户岗位关系
        sysUserPostService.saveOrUpdate(entity.getId(), vo.getPostIdList());
    }

    @Override
    public void update(SysUserVO vo) {
        // 如果密码不为空，则进行加密处理
        if (vo.getPassword() == null || vo.getPassword().isEmpty()) {
            vo.setPassword(null);
        } else {
            // 密码验证
            passwordStrength(vo.getPassword());
            vo.setPassword(passwordEncoder.encode(vo.getPassword()));
        }
        SysUserEntity entity = SysUserConvert.INSTANCE.convert(vo);

        // 判断用户名是否存在
        SysUserEntity user = sysUserMapper.getByUsername(entity.getUsername());
        if (user != null && !user.getId().equals(entity.getId())) {
            throw new ServerException("用户名已经存在");
        }

        // 判断手机号是否存在
        user = sysUserMapper.getByMobile(entity.getMobile());
        if (user != null && !user.getId().equals(entity.getId())) {
            throw new ServerException("手机号已经存在");
        }

        // 更新用户
        sysUserMapper.updateById(entity);

        // 更新用户角色关系
        sysUserRoleService.saveOrUpdate(entity.getId(), vo.getRoleIdList());

        // 更新用户岗位关系
        sysUserPostService.saveOrUpdate(entity.getId(), vo.getPostIdList());
    }

    @Override
    public void delete(List<Long> idList) {
        // 删除用户
//        removeByIds(idList);
        idList.forEach(id -> {
            SysUserEntity entity = new SysUserEntity();
            entity.setId(id);
            entity.setDbStatus(0);
            sysUserMapper.updateById(entity);
        });

        // 删除用户角色关系
        sysUserRoleService.deleteByUserIdList(idList);

        // 删除用户岗位关系
        sysUserPostService.deleteByUserIdList(idList);
    }

    @Override
    public SysUserVO getByMobile(String mobile) {
        SysUserEntity user = sysUserMapper.getByMobile(mobile);
        return SysUserConvert.INSTANCE.convert(user);
    }

    /**
     * 获取用户信息，用户ID为空时，获取当前登录系统的用户信息
     * @param userId 用户ID
     * @return 用户信息
     */
    @Override
    public SysUserVO info(Long userId) {
        SysUserVO user;
        if(ObjectUtils.isEmpty(userId)){
            user = SysUserConvert.INSTANCE.convert(SecurityUser.getUser());
            userId = user.getId();
        }
        // 重新查询，防止修改基本信息后，缓存未更新的情况
        SysUserEntity entity = sysUserMapper.getById(userId);
        user = SysUserConvert.INSTANCE.convert(entity);
        // 用户角色列表
        List<Long> roleIdList = sysUserRoleService.getRoleIdList(userId);
        user.setRoleIdList(roleIdList);

        // 用户岗位列表
        List<Long> postIdList = sysUserPostService.getPostIdList(userId);
        user.setPostIdList(postIdList);

        // 租户名
        if(user.getTenantId() != null){
            user.setTenantName(tenantCache.getNameByTenantId(user.getTenantId()));
        }
        return user;
    }

    /**
     * 根据用户ID获取用户
     * @param userId 用户ID
     * @return e
     */
    @Override
    public SysUserEntity getUser(Long userId) {
        AssertUtils.isNull(userId, "用户ID");
        return sysUserMapper.getById(userId);
    }

    @Override
    public void updatePassword(Long id, String newPassword) {
        // 密码验证
        passwordStrength(newPassword);
        // 修改密码
        SysUserEntity user = sysUserMapper.getById(id);
        newPassword = passwordEncoder.encode(newPassword);
        user.setPassword(newPassword);

        sysUserMapper.updateById(user);
    }

    @Override
    public PageResult<SysUserVO> roleUserPage(SysRoleUserQuery query) {
        // 数据列表
        List<SysUserEntity> list = sysUserMapper.getRoleUserList(query);
        List<SysUserVO> voList = SysUserConvert.INSTANCE.convertList(list);
        for(SysUserVO vo: voList){
            vo.setTenantName(tenantCache.getNameByTenantId(vo.getTenantId()));
        }
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
    public void export() {
        List<SysUserEntity> list = sysUserMapper.getList(new SysUserQuery());
        List<SysUserExcelVO> userExcelVOS = SysUserConvert.INSTANCE.convert2List(list);
        ExcelUtils.excelExport(SysUserExcelVO.class, "用户信息", "用户信息", userExcelVOS);
    }

    /**
     * 更新租户
     * @param tenantID 组合ID
     * @param userIdList 用户ID
     * @param flag 1 绑定 2 解绑
     */
    @Override
    public void updateTenantUser(String tenantID, List<Long> userIdList, Integer flag) {
        AssertUtils.isNull(userIdList, "用户ID");
        AssertUtils.isNull(tenantID, "租户ID");
        for(Long userId: userIdList){
            SysUserEntity user = sysUserMapper.getById(userId);
            if(user == null || user.getId() == null){
                throw new ServerException("用户不存在");
            }
            if(flag == 1){
                if(user.getTenantId() != null && !user.getTenantId().isEmpty()){
                    throw new ServerException("用户已存在其他租户中【" + user.getTenantId() + "】");
                }
                user.setTenantId(tenantID);
                sysUserMapper.updateById(user);
            }else if(flag == 2){
                if(user.getTenantId() == null || user.getTenantId().isEmpty()){
                    throw new ServerException("用户未绑定租户，不能解绑");
                }
                if(!tenantID.equals(user.getTenantId())){
                    throw new ServerException("租户ID不准确，不能解绑");
                }
                user.setTenantId(null);
                sysUserMapper.unbindUser(user);// 解绑用户
            }else{
                throw new ServerException("未知操作，租户用户操作失败！");
            }
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
        SysUserEntity user = sysUserMapper.getById(userId);
        if(user == null || user.getId() == null){
            throw new ServerException("未找到对应的用户，重置密码失败！");
        }
        String pwd = Tools.getRandom(8);
        user.setPassword(passwordEncoder.encode(pwd));
        user.setUpdateTime(LocalDateTime.now());
        user.setUpdater(SecurityUser.getUserId());
        sysUserMapper.updateById(user);
        return pwd;
    }

    /**
     * 密码校验
     * @param password 原始密码
     */
    private void passwordStrength(String password){
        // 密码验证等级
        int grade = sysParamsService.getDefaultInt("PASSWORD_GRADE");
        grade = grade==-1?3:grade;
        // 密码校验
        if(!AssertUtils.passwordStrength(grade, password)){
            throw new ServerException("密码: " + CommonEnum.getName(grade));
        }
    }

    // 缓存用户信息
    /*@PostConstruct
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
