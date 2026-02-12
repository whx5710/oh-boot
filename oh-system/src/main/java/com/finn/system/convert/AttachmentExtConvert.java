package com.finn.system.convert;

import com.finn.framework.utils.ServiceFactory;
import com.finn.system.cache.TenantCache;
import com.finn.system.entity.AttachmentEntity;
import com.finn.system.vo.AttachmentVO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 自定义转换
 * 租户ID转租户名
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2025-06-08
 */
public class AttachmentExtConvert implements AttachmentConvert{
    private final AttachmentConvert attachmentConvert;
    // 租户缓存工具
    TenantCache tenantCache = ServiceFactory.getBean("tenantCache", TenantCache.class);

    public AttachmentExtConvert(AttachmentConvert attachmentConvert){
        this.attachmentConvert = attachmentConvert;
    }
    @Override
    public AttachmentEntity convert(AttachmentVO vo) {
        return attachmentConvert.convert(vo);
    }

    @Override
    public AttachmentVO convert(AttachmentEntity entity) {
        AttachmentVO vo = attachmentConvert.convert(entity);
        // 租户名称获取
        vo.setTenantName(tenantCache.getNameByTenantId(entity.getTenantId()));
        return vo;
    }

    @Override
    public List<AttachmentVO> convertList(List<AttachmentEntity> list) {
        if (list == null) {
            return null;
        } else {
            List<AttachmentVO> list1 = new ArrayList(list.size());
            Iterator var3 = list.iterator();

            while(var3.hasNext()) {
                AttachmentEntity attachmentEntity = (AttachmentEntity)var3.next();
                list1.add(this.convert(attachmentEntity));
            }
            return list1;
        }
    }
}
