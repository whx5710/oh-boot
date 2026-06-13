package com.finn.system.convert;

import com.finn.framework.utils.ServiceFactory;
import com.finn.system.cache.TenantCache;
import com.finn.system.entity.ErrorLogEntity;
import com.finn.system.vo.ErrorLogVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义转换
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2026-05-03
 */
public class ErrorLogExtConvert implements ErrorLogConvert{

    private final ErrorLogConvert errorLogConvert;
    // 租户缓存工具
    TenantCache tenantCache = ServiceFactory.getBean("tenantCache", TenantCache.class);

    public ErrorLogExtConvert(ErrorLogConvert errorLogConvert){
        this.errorLogConvert = errorLogConvert;
    }

    @Override
    public ErrorLogEntity convert(ErrorLogVO vo) {
        return errorLogConvert.convert(vo);
    }

    @Override
    public ErrorLogVO convert(ErrorLogEntity entity) {
        ErrorLogVO vo = errorLogConvert.convert(entity);
        // 租户名称获取
        vo.setTenantName(tenantCache.getNameByTenantId(entity.getTenantId()));
        return vo;
    }

    @Override
    public List<ErrorLogVO> convertList(List<ErrorLogEntity> list) {
        if (list == null) {
            return null;
        } else {
            List<ErrorLogVO> list1 = new ArrayList<>(list.size());

            for (ErrorLogEntity errorLogEntity : list) {
                list1.add(this.convert(errorLogEntity));
            }
            return list1;
        }
    }
}
