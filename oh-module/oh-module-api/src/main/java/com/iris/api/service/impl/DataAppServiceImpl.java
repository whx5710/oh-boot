package com.iris.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iris.api.convert.DataAppConvert;
import com.iris.api.dao.DataAppDao;
import com.iris.api.entity.DataAppEntity;
import com.iris.api.query.DataAppQuery;
import com.iris.api.query.DataFunctionAuthorityQuery;
import com.iris.api.service.DataAppService;
import com.iris.api.vo.DataAppVO;
import com.iris.framework.common.exception.ServerException;
import com.iris.framework.common.utils.AssertUtils;
import com.iris.framework.common.utils.PageResult;
import com.iris.framework.datasource.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * 客户端
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2023-07-29
 */
@Service
public class DataAppServiceImpl extends BaseServiceImpl<DataAppDao, DataAppEntity> implements DataAppService {

    @Override
    public PageResult<DataAppVO> page(DataAppQuery query) {
        IPage<DataAppEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));

        return new PageResult<>(DataAppConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
    }

    private LambdaQueryWrapper<DataAppEntity> getWrapper(DataAppQuery query){
        LambdaQueryWrapper<DataAppEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(DataAppEntity::getDbStatus, 1);
        if(!ObjectUtils.isEmpty(query.getKeyWord())){
            wrapper.and(w -> w.like(DataAppEntity::getClientId, query.getKeyWord())
                    .or().like(DataAppEntity::getName, query.getKeyWord()));
        }
        return wrapper;
    }

    @Override
    public void save(DataAppVO vo) {
        DataAppEntity entity = DataAppConvert.INSTANCE.convert(vo);
        AssertUtils.isBlank(entity.getClientId(), "客户端ID");
        LambdaQueryWrapper<DataAppEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(DataAppEntity::getClientId, entity.getClientId());
        List<DataAppEntity> list = this.baseMapper.selectList(wrapper);
        if(!ObjectUtils.isEmpty(list)){
            throw new ServerException("客户端ID已存在");
        }
        baseMapper.insert(entity);
    }

    @Override
    public void update(DataAppVO vo) {
        DataAppEntity entity = DataAppConvert.INSTANCE.convert(vo);

        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        removeByIds(idList);
    }

    @Override
    public List<DataAppVO> listAuthority(DataFunctionAuthorityQuery params) {
        return baseMapper.listAuthority(params);
    }

    /**
     * 根据客户端ID查询
     * @param clientId
     * @return
     */
    @Override
    public DataAppVO findByClientId(String clientId) {
        AssertUtils.isBlank(clientId,"客户端ID");
        LambdaQueryWrapper<DataAppEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(DataAppEntity::getDbStatus, 1).eq(DataAppEntity::getClientId, clientId);
        DataAppEntity dataAppEntity = this.baseMapper.selectOne(wrapper);
        return DataAppConvert.INSTANCE.convert(dataAppEntity);
    }
}