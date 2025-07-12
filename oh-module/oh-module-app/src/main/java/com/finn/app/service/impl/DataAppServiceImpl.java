package com.finn.app.service.impl;

import com.finn.core.exception.ServerException;
import com.finn.app.convert.DataAppConvert;
import com.finn.app.entity.DataAppEntity;
import com.finn.app.mapper.DataAppMapper;
import com.finn.app.query.DataAppQuery;
import com.finn.app.service.DataAppService;
import com.finn.core.utils.AssertUtils;
import com.finn.core.utils.PageResult;
import com.finn.framework.entity.api.DataAppDTO;
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
public class DataAppServiceImpl implements DataAppService {

    private final DataAppMapper dataAppMapper;

    public DataAppServiceImpl(DataAppMapper dataAppMapper){
        this.dataAppMapper = dataAppMapper;
    }

    @Override
    public PageResult<DataAppDTO> page(DataAppQuery query) {
        List<DataAppEntity> list = dataAppMapper.getList(query);
        return new PageResult<>(DataAppConvert.INSTANCE.convertList(list), query.getTotal());
    }

    @Override
    public void save(DataAppDTO vo) {
        DataAppEntity entity = DataAppConvert.INSTANCE.convert(vo);
        AssertUtils.isBlank(entity.getClientId(), "客户端ID");
        DataAppQuery params = new DataAppQuery();
        params.setClientId(vo.getClientId());
        List<DataAppEntity> list = dataAppMapper.getList(params);
        if(!ObjectUtils.isEmpty(list)){
            throw new ServerException("客户端ID已存在");
        }
        dataAppMapper.insertDataApp(entity);
    }

    @Override
    public void update(DataAppDTO vo) {
        DataAppEntity entity = DataAppConvert.INSTANCE.convert(vo);

        dataAppMapper.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        idList.forEach(id -> {
            DataAppEntity params = new DataAppEntity();
            params.setId(id);
            params.setDbStatus(0);
            dataAppMapper.updateById(params);
        });
    }

    /**
     * 根据客户端ID查询
     * @param clientId
     * @return
     */
    @Override
    public DataAppDTO findByClientId(String clientId) {
        AssertUtils.isBlank(clientId,"客户端ID");
        DataAppQuery params = new DataAppQuery();
        params.setClientId(clientId);
        List<DataAppEntity> list = dataAppMapper.getList(params);
        DataAppEntity app = list.getFirst();
        return DataAppConvert.INSTANCE.convert(app);
    }

    @Override
    public DataAppEntity getById(Long id) {
        return dataAppMapper.getById(id);
    }
}