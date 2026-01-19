package com.finn.system.convert;

import com.finn.framework.utils.ServiceFactory;
import com.finn.system.cache.DictCache;
import com.finn.system.cache.TenantCache;
import com.finn.system.entity.LogLoginEntity;
import com.finn.system.vo.DictDataSingleVO;
import com.finn.system.vo.LogLoginVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义转换
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2025-06-14
 */
public class LogLoginExtConvert implements LogLoginConvert {

    private final LogLoginConvert logLoginConvert;

    TenantCache tenantCache = ServiceFactory.getBean("tenantCache", TenantCache.class);

    DictCache dictCache = ServiceFactory.getBean("dictCache", DictCache.class);

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

        // 登录状态 0：失败   1：成功
        if(entity.getStatus() == 1){
            vo.setStatusLabel("成功");
        }else{
            vo.setStatusLabel("失败");
        }

        // 登录操作信息 操作信息   0：登录成功   1：退出成功  2：验证码错误  3：账号密码错误
        if(entity.getOperation() != null){
            DictDataSingleVO dictDataSingleVO = dictCache.get("login_operation", String.valueOf(entity.getOperation()));
            if(dictDataSingleVO != null){
                vo.setOperationLabel(dictDataSingleVO.getDictLabel());
            }
        }
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
