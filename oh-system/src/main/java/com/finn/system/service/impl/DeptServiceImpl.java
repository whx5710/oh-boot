package com.finn.system.service.impl;

import com.finn.framework.cache.RedisCache;
import com.finn.framework.cache.RedisKeys;
import com.finn.framework.exception.ServerException;
import com.finn.common.utils.AssertUtils;
import com.finn.common.utils.JsonUtils;
import com.finn.framework.entity.PageResult;
import com.finn.common.utils.TreeUtils;
import com.finn.framework.datasource.wrapper.CountWrapper;
import com.finn.framework.datasource.wrapper.QueryWrapper;
import com.finn.system.convert.DeptConvert;
import com.finn.system.entity.DeptEntity;
import com.finn.system.entity.UserEntity;
import com.finn.system.mapper.DeptMapper;
import com.finn.system.mapper.UserMapper;
import com.finn.system.query.DeptQuery;
import com.finn.system.service.DeptService;
import com.finn.system.vo.DeptVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门管理
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Service
public class DeptServiceImpl implements DeptService {
    private final UserMapper userMapper;
    private final DeptMapper deptMapper;
    private final RedisCache redisCache;
    public DeptServiceImpl(UserMapper userMapper, DeptMapper deptMapper,
                          RedisCache redisCache) {
        this.userMapper = userMapper;
        this.deptMapper = deptMapper;
        this.redisCache = redisCache;
    }

    @Override
    public List<DeptVO> getList(DeptQuery query) {
        // 数据权限
        // params.put(Constant.DATA_SCOPE, getDataScope("t1", "id"));

        // 部门列表
        List<DeptEntity> entityList = deptMapper.getList(query);

        List<DeptVO> voList = DeptConvert.INSTANCE.convertList(entityList);

        return TreeUtils.build(voList);
    }

    /**
     * 部门分页列表
     * @param query 参数
     * @return e
     */
    @Override
    public PageResult<DeptVO> page(DeptQuery query) {
        Page<DeptEntity> page = PageHelper.startPage(query.getPageNum(), query.getPageSize());
        // 部门列表
        List<DeptEntity> list = deptMapper.getList(query);

        List<DeptVO> voList = DeptConvert.INSTANCE.convertList(list);
        return new PageResult<>(voList, page.getTotal());
    }

    @Override
    public void save(DeptVO vo) {
        DeptEntity entity = DeptConvert.INSTANCE.convert(vo);
        if(entity.getParentId() == null){
            entity.setParentId(0L);
        }
        // 判断单位名称是否重复
        long l = deptMapper.count(CountWrapper.of(DeptEntity.class).eq(DeptEntity::getName, entity.getName())
                .eq(DeptEntity::getDbStatus, 1).eq(DeptEntity::getParentId, entity.getParentId()));
        if(l > 0){
            throw new ServerException("同一部门下，名称同名");
        }
        deptMapper.insert(entity);
    }

    @Override
    public void update(DeptVO vo) {
        DeptEntity entity = DeptConvert.INSTANCE.convert(vo);

        // 上级部门不能为自身
        if(entity.getId().equals(entity.getParentId())){
            throw new ServerException("上级部门不能为自身");
        }
        // 上级部门不能为下级
        List<Long> subDeptList = getSubDeptIdList(entity.getId());
        if(subDeptList.contains(entity.getParentId())){
            throw new ServerException("上级部门不能为下级");
        }
        if(entity.getParentId() == null){
            entity.setParentId(0L);
        }
        // 判断单位名称是否重复
        long l = deptMapper.count(CountWrapper.of(DeptEntity.class).eq(DeptEntity::getName, entity.getName())
                .eq(DeptEntity::getDbStatus, 1).eq(DeptEntity::getParentId, entity.getParentId())
                .ne(DeptEntity::getId, entity.getId()));
        if(l > 0){
            throw new ServerException("同一部门下，名称同名");
        }
        deptMapper.updateById(entity);
    }

    @Override
    public void updateById(DeptEntity dept) {
        deptMapper.updateById(dept);
    }

    @Override
    public void delete(Long id) {
        // 判断是否有子部门
        int deptCount = deptMapper.countByParentId(id);
        if(deptCount > 0){
            throw new ServerException("请先删除子部门");
        }
        // 判断部门下面是否有用户
        long userCount = userMapper.count(CountWrapper.of(UserEntity.class).ne(UserEntity::getDbStatus, 0)
                .eq(UserEntity::getDeptId, id));
        if(userCount > 0){
            throw new ServerException("部门下面有用户，不能删除");
        }
        DeptEntity dept = deptMapper.findById(id, DeptEntity.class);
        if(dept == null){
            throw new ServerException("部门不存在，请检查");
        }
        if(dept.getDbStatus() == 0){
            throw new ServerException("部门已删除，不能重复操作");
        }
        // 删除
        dept.setDbStatus(0);
        deptMapper.updateById(dept);
    }

    @Override
    public List<Long> getSubDeptIdList(Long id) {
        // 所有部门的id、pid列表
        List<DeptEntity> deptList = deptMapper.listByWrapper(QueryWrapper.of(DeptEntity.class)
                .eq(DeptEntity::getDbStatus, 1));

        // 递归查询所有子部门ID列表
        List<Long> subIdList = new ArrayList<>();
        getTree(id, deptList, subIdList);

        // 本部门也添加进去
        subIdList.add(id);

        return subIdList;
    }

    @Override
    public DeptEntity getById(Long id) {
        AssertUtils.isNull(id, "部门ID");
        return deptMapper.findById(id, DeptEntity.class);
    }

    /**
     * 根据ID获取部门信息
     * @param id 用户ID
     * @param cache 为true则优先读取缓存
     * @return 部门信息
     */
    @Override
    public DeptEntity getById(Long id, Boolean cache) {
        AssertUtils.isNull(id, "部门ID");
        if(cache == null){
            cache = false;
        }
        String key = RedisKeys.getDeptCacheKey(id);
        if(!redisCache.hasKey(key)){
            DeptEntity dept = deptMapper.findById(id, DeptEntity.class);
            if(dept != null && dept.getId() != null){
                redisCache.set(key, dept, 7200);// 缓存2小时
            }
            return dept;
        }else{
            if(cache){
                return JsonUtils.convertValue(redisCache.get(key), DeptEntity.class);
            }else{
                return deptMapper.findById(id, DeptEntity.class);
            }
        }
    }

    /**
     * 递归查询ID下面的部门
     * @param id
     * @param deptList
     * @param subIdList
     */
    private void getTree(Long id, List<DeptEntity> deptList, List<Long> subIdList) {
        for(DeptEntity dept : deptList){
            if (dept.getParentId().equals(id)){
                getTree(dept.getId(), deptList, subIdList);
                subIdList.add(dept.getId());
            }
        }
    }
}
