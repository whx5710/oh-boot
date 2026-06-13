package com.finn.system.convert;

import com.finn.framework.operatelog.dto.OperateLogDTO;
import com.finn.framework.utils.ServiceFactory;
import com.finn.system.cache.DictCache;
import com.finn.system.cache.TenantCache;
import com.finn.system.vo.DictDataSingleVO;
import com.finn.system.entity.OperateLogEntity;
import com.finn.system.vo.OperateLogVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义转换
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2025-06-14
 */
public class OperateExtLogConvert implements OperateLogConvert {

    TenantCache tenantCache = ServiceFactory.getBean("tenantCache", TenantCache.class);

    DictCache dictCache = ServiceFactory.getBean("dictCache", DictCache.class);

    private final OperateLogConvert operateLogConvert;

    public OperateExtLogConvert(OperateLogConvert operateLogConvert){
        this.operateLogConvert = operateLogConvert;
    }

    @Override
    public OperateLogEntity convert(OperateLogVO vo) {
        return operateLogConvert.convert(vo);
    }

    @Override
    public OperateLogEntity convert(OperateLogDTO vo) {
        return operateLogConvert.convert(vo);
    }

    @Override
    public OperateLogVO convert(OperateLogEntity entity) {
        OperateLogVO vo = operateLogConvert.convert(entity);
        // 租户名称获取
        vo.setTenantName(tenantCache.getNameByTenantId(entity.getTenantId()));

        // 操作操作类型
        if(entity.getOperateType() != null){
            DictDataSingleVO dictDataSingleVO = dictCache.get("log_operate_type", String.valueOf(entity.getOperateType()));
            if(dictDataSingleVO != null){
                vo.setOperateTypeLabel(dictDataSingleVO.getDictLabel());
            }
        }
        return vo;
    }

    @Override
    public List<OperateLogVO> convertList(List<OperateLogEntity> list) {
        if ( list == null ) {
            return null;
        }
        List<OperateLogVO> list1 = new ArrayList<OperateLogVO>( list.size() );
        for ( OperateLogEntity operateLogEntity : list ) {
            list1.add( convert(operateLogEntity) );
        }
        return list1;
    }
}
