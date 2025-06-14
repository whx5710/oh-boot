package com.finn.sys.base.convert;

import com.finn.framework.utils.ServiceFactory;
import com.finn.support.cache.TenantCache;
import com.finn.sys.base.entity.LogOperateEntity;
import com.finn.sys.base.vo.LogOperateVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义转换
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2025-06-14
 */
public class LogOperateExtConvert implements LogOperateConvert{

    TenantCache tenantCache = ServiceFactory.getBean("tenantCache", TenantCache.class);

    private final LogOperateConvert logOperateConvert;

    public LogOperateExtConvert(LogOperateConvert logOperateConvert){
        this.logOperateConvert = logOperateConvert;
    }

    @Override
    public LogOperateEntity convert(LogOperateVO vo) {
        return logOperateConvert.convert(vo);
    }

    @Override
    public LogOperateVO convert(LogOperateEntity entity) {
        LogOperateVO vo = logOperateConvert.convert(entity);
        // 租户名称获取
        vo.setTenantName(tenantCache.getNameByTenantId(entity.getTenantId()));
        return vo;
    }

    @Override
    public List<LogOperateVO> convertList(List<LogOperateEntity> list) {
        if ( list == null ) {
            return null;
        }
        List<LogOperateVO> list1 = new ArrayList<LogOperateVO>( list.size() );
        for ( LogOperateEntity logOperateEntity : list ) {
            list1.add( convert( logOperateEntity ) );
        }
        return list1;
    }
}
