package com.iris.support.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iris.common.excel.ExcelFinishCallBack;
import com.iris.common.utils.ExcelUtils;
import com.iris.core.cache.RedisCache;
import com.iris.core.cache.RedisKeys;
import com.iris.core.constant.CommonEnum;
import com.iris.core.utils.AssertUtils;
import com.iris.core.utils.DateUtils;
import com.iris.core.utils.JsonUtils;
import com.iris.core.utils.PageResult;
import com.iris.framework.security.user.SecurityUser;
import com.iris.support.mapper.SysUserMapper;
import com.iris.support.enums.SuperAdminEnum;
import com.iris.support.query.SysRoleUserQuery;
import com.iris.support.query.SysUserQuery;
import com.iris.support.vo.SysUserExcelVO;
import com.iris.support.vo.SysUserVO;
import com.iris.support.convert.SysUserConvert;
import com.iris.support.service.SysParamsService;
import com.iris.support.service.SysUserPostService;
import com.iris.support.service.SysUserService;
import com.iris.support.service.SysUserRoleService;
import com.iris.core.exception.ServerException;
import com.iris.support.entity.SysUserEntity;
import jakarta.annotation.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

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

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private SysParamsService sysParamsService;

    public SysUserServiceImpl(SysUserRoleService sysUserRoleService, SysUserPostService sysUserPostService,
                              RedisCache redisCache, SysUserMapper sysUserMapper) {
        this.sysUserRoleService = sysUserRoleService;
        this.sysUserPostService = sysUserPostService;
        this.redisCache = redisCache;
        this.sysUserMapper = sysUserMapper;
    }

    @Override
    public PageResult<SysUserVO> page(SysUserQuery query) {
        // 分页查询
        PageHelper.startPage(query.getPage(), query.getLimit());
        // 数据列表
        List<SysUserEntity> list = sysUserMapper.getList(query);
        PageInfo<SysUserEntity> pageInfo = new PageInfo<>(list);
        return new PageResult<>(SysUserConvert.INSTANCE.convertList(pageInfo.getList()), pageInfo.getTotal());
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
        sysUserMapper.insert(entity); // 保存用户-动态sql

        // 保存用户角色关系
        sysUserRoleService.saveOrUpdate(entity.getId(), vo.getRoleIdList());

        // 更新用户岗位关系
        sysUserPostService.saveOrUpdate(entity.getId(), vo.getPostIdList());
    }

    @Override
    public void update(SysUserVO vo) {
        // 如果密码不为空，则进行加密处理
        if (StrUtil.isBlank(vo.getPassword())) {
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
        return user;
    }

    /**
     * 根据用户ID获取用户
     * @param userId
     * @return
     */
    @Override
    public SysUserEntity getUser(Long userId) {
        AssertUtils.isNull(userId, "用户ID");
        return sysUserMapper.getById(userId);
    }

    /**
     * 根据用户ID获取用户(优先读取缓存)
     * @param userId 用户ID
     * @param cache 为true则优先读取缓存
     * @return 用户信息
     */
    @Override
    public SysUserEntity getUser(Long userId, Boolean cache) {
        AssertUtils.isNull(userId, "用户ID");
        if(cache == null){
            cache = false;
        }
        String key = RedisKeys.getUserCacheKey(userId);
        if(!redisCache.hasKey(key)){
            SysUserEntity user = sysUserMapper.getById(userId);
            if(user != null && user.getId() != null){
                redisCache.set(key, user, 7200);// 缓存2小时
            }
            return user;
        }else{
            if(cache){
                return JsonUtils.convertValue(redisCache.get(key), SysUserEntity.class);
            }else{
                return sysUserMapper.getById(userId);
            }
        }
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
        PageHelper.startPage(query.getPage(), query.getLimit());
        // 数据列表
        List<SysUserEntity> list = sysUserMapper.getRoleUserList(query);
        PageInfo<SysUserEntity> pageInfo = new PageInfo<>(list);
        return new PageResult<>(SysUserConvert.INSTANCE.convertList(pageInfo.getList()), pageInfo.getTotal());
    }

    @Override
    public void importByExcel(MultipartFile file, String password) {

        ExcelUtils.readAnalysis(file, SysUserExcelVO.class, new ExcelFinishCallBack<SysUserExcelVO>() {
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
                sysUserEntities.forEach(sysUserMapper::insertUser);
            }
        });

    }

    @Override
    public void export() {
        List<SysUserEntity> list = sysUserMapper.getList(new SysUserQuery()); // list(Wrappers.lambdaQuery(SysUserEntity.class).eq(SysUserEntity::getSuperAdmin, SuperAdminEnum.NO.getValue()));
        List<SysUserExcelVO> userExcelVOS = SysUserConvert.INSTANCE.convert2List(list);
        // transService.transBatch(userExcelVOS);
        // 写到浏览器打开
        ExcelUtils.excelExport(SysUserExcelVO.class, "system_user_excel" + DateUtils.format(new Date()), null, userExcelVOS);
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
