package com.finn.support.convert;

import com.finn.framework.utils.ServiceFactory;
import com.finn.support.cache.TenantCache;
import com.finn.support.entity.LogLoginEntity;
import com.finn.support.vo.LogLoginVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义转换
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2025-06-14
 */
public class LogLoginExtConvert implements LogLoginConvert{

    private final LogLoginConvert logLoginConvert;

    TenantCache tenantCache = ServiceFactory.getBean("tenantCache", TenantCache.class);

    public LogLoginExtConvert(LogLoginConvert logLoginConvert){
        this.logLoginConvert = logLoginConvert;
    }

    @Override
    public LogLoginEntity convert(LogLoginVO vo) {
        return logLoginConvert.convert(vo);
    }

    @Override
    public LogLoginVO convert(LogLoginEntity entity) {
        LogLoginVO vo = logLoginConvert.convert(entity);
        // 租户名称获取
        vo.setTenantName(tenantCache.getNameByTenantId(entity.getTenantId()));
        return vo;
    }

    @Override
    public List<LogLoginVO> convertList(List<LogLoginEntity> list) {
        if ( list == null ) {
            return null;
        }
        List<LogLoginVO> list1 = new ArrayList<LogLoginVO>( list.size() );
        for ( LogLoginEntity logLoginEntity : list ) {
            list1.add( convert( logLoginEntity ) );
        }
        return list1;
    }
}
