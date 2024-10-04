package com.iris.sys.app.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iris.framework.common.entity.api.DataAppDTO;
import com.iris.framework.common.exception.ServerException;
import com.iris.framework.common.utils.AssertUtils;
import com.iris.framework.common.utils.PageResult;
import com.iris.sys.app.convert.DataAppConvert;
import com.iris.sys.app.mapper.DataAppMapper;
import com.iris.sys.app.entity.DataAppEntity;
import com.iris.sys.app.query.DataAppQuery;
import com.iris.sys.app.query.DataFunctionAuthorityQuery;
import com.iris.sys.app.service.DataAppService;
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
        PageHelper.startPage(query.getPage(), query.getLimit());
        List<DataAppEntity> list = dataAppMapper.getList(query);
        PageInfo<DataAppEntity> pageInfo = new PageInfo<>(list);
        return new PageResult<>(DataAppConvert.INSTANCE.convertList(pageInfo.getList()), pageInfo.getTotal());
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

    @Override
    public List<DataAppDTO> listAuthority(DataFunctionAuthorityQuery params) {
        return dataAppMapper.listAuthority(params);
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