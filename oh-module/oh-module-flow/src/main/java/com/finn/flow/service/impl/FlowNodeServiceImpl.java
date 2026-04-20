package com.finn.flow.service.impl;

import com.finn.framework.datasource.DynamicDataSource;
import com.finn.framework.datasource.wrapper.QueryWrapper;
import com.finn.framework.exception.ServerException;
import com.github.pagehelper.Page;
import com.finn.flow.convert.FlowNodeConvert;
import com.finn.flow.entity.FlowNodeEntity;
import com.finn.flow.mapper.FlowNodeMapper;
import com.finn.flow.query.FlowNodeQuery;
import com.finn.flow.service.FlowNodeService;
import com.finn.flow.vo.FlowNodeVO;
import com.finn.framework.entity.PageResult;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

/**
 * 环节定义表
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2024-01-31
 */
@Service
public class FlowNodeServiceImpl implements FlowNodeService {

    private final FlowNodeMapper flowNodeMapper;

    private final DynamicDataSource dynamicDataSource;

    public FlowNodeServiceImpl(FlowNodeMapper flowNodeMapper, DynamicDataSource dynamicDataSource){
        this.flowNodeMapper = flowNodeMapper;
        this.dynamicDataSource = dynamicDataSource;
    }

    @Override
    public PageResult<FlowNodeVO> page(FlowNodeQuery query) {
        QueryWrapper<FlowNodeEntity> queryWrapper = QueryWrapper.of(FlowNodeEntity.class)
                .eq(FlowNodeEntity::getDbStatus, 1).eq(FlowNodeEntity::getProcDefId, query.getProcDefId())
                .eq(FlowNodeEntity::getActDefId, query.getActDefId());
        if(query.getKeyWord() != null && !query.getKeyWord().isEmpty()){
            queryWrapper.jointSQL("(act_def_id like concat('%', #{keyWord}, '%') or node_name like concat('%', #{keyWord}, '%'))",
                    "keyWord", query.getKeyWord());
        }
        queryWrapper.orderBy(FlowNodeEntity::getSort).page(query.getPageNum(), query.getPageSize());
        try (Page<FlowNodeEntity> page = flowNodeMapper.listByWrapper(queryWrapper)) {
            return new PageResult<>(FlowNodeConvert.INSTANCE.convertList(page.getResult()), page.getTotal());
        }
    }

    @Override
    public void save(FlowNodeVO vo) {
        FlowNodeEntity entity = FlowNodeConvert.INSTANCE.convert(vo);
        flowNodeMapper.insert(entity);
    }

    @Override
    public void update(FlowNodeVO vo) {
        vo.setConditionExpression(null); // 防止表达式特殊字符被转换
        FlowNodeEntity entity = FlowNodeConvert.INSTANCE.convert(vo);
        flowNodeMapper.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        idList.forEach(id -> {
            FlowNodeEntity param = new FlowNodeEntity();
            param.setId(id);
            param.setDbStatus(0);
            flowNodeMapper.updateById(param);
        });
    }

    @Override
    public FlowNodeEntity getById(Long id) {
        return flowNodeMapper.findById(id, FlowNodeEntity.class);
    }

    /**
     * 批量更新
     * @param list
     */
    @Override
    public void updateBatch(List<FlowNodeVO> list) {
        SqlSession sqlSession = null;
        try {
            DataSource dataSource = dynamicDataSource.getResolvedDefaultDataSource();
            SqlSessionFactory sqlSessionFactory = dynamicDataSource.getSqlSessionFactory(dataSource);
            sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
            FlowNodeMapper mapper = sqlSession.getMapper(FlowNodeMapper.class);
            for(FlowNodeVO vo: list){
                vo.setConditionExpression(null); // 防止表达式特殊字符被转换
                mapper.updateById(FlowNodeConvert.INSTANCE.convert(vo));
            }
        } catch (Exception e) {
            throw new ServerException(e.getMessage(), e);
        }finally {
            if(sqlSession != null){
                sqlSession.commit();
                sqlSession.clearCache();
                sqlSession.close();// 用完关闭
            }
        }
    }

}