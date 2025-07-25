package com.finn.sys.base.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.finn.core.constant.Constant;
import com.finn.core.utils.PageResult;
import com.finn.sys.base.cache.SmsPlatformCache;
import com.finn.sys.base.convert.SmsPlatformConvert;
import com.finn.sys.base.entity.SmsPlatformEntity;
import com.finn.sys.base.mapper.SmsPlatformMapper;
import com.finn.sys.base.query.SmsPlatformQuery;
import com.finn.sys.base.service.SmsPlatformService;
import com.finn.sys.base.vo.SmsPlatformVO;
import com.finn.sys.sms.config.SmsConfig;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 短信平台
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@Service
public class SmsPlatformServiceImpl implements SmsPlatformService {
    private final SmsPlatformCache smsPlatformCache;
    private final SmsPlatformMapper smsPlatformMapper;

    public SmsPlatformServiceImpl(SmsPlatformCache smsPlatformCache, SmsPlatformMapper smsPlatformMapper) {
        this.smsPlatformCache = smsPlatformCache;
        this.smsPlatformMapper = smsPlatformMapper;
    }

    @Override
    public PageResult<SmsPlatformVO> page(SmsPlatformQuery query) {
        Page<SmsPlatformEntity> page = PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<SmsPlatformEntity> list = smsPlatformMapper.getList(query);
        return new PageResult<>(SmsPlatformConvert.INSTANCE.convertList(list), page.getTotal());
    }

    @Override
    public List<SmsConfig> listByEnable() {
        // 从缓存读取
        List<SmsConfig> cacheList = smsPlatformCache.list();

        // 如果缓存没有，则从DB读取，然后保存到缓存里
        if(cacheList == null) {
            SmsPlatformQuery param = new SmsPlatformQuery();
            param.setStatus(Constant.ENABLE);
            List<SmsPlatformEntity> list = smsPlatformMapper.getList(param);
            cacheList = SmsPlatformConvert.INSTANCE.convertList2(list);
            smsPlatformCache.save(cacheList);
        }

        return cacheList;
    }

    @Override
    public void save(SmsPlatformVO vo) {
        SmsPlatformEntity entity = SmsPlatformConvert.INSTANCE.convert(vo);
        smsPlatformMapper.insertPlatform(entity);
        smsPlatformCache.delete();
    }

    @Override
    public void update(SmsPlatformVO vo) {
        SmsPlatformEntity entity = SmsPlatformConvert.INSTANCE.convert(vo);
        smsPlatformMapper.updateById(entity);
        smsPlatformCache.delete();
    }

    @Override
    public void delete(List<Long> idList) {
        idList.forEach(id -> {
            SmsPlatformEntity param = new SmsPlatformEntity();
            param.setId(id);
            param.setDbStatus(0);
            smsPlatformMapper.updateById(param);
        });
        smsPlatformCache.delete();
    }

    @Override
    public SmsPlatformEntity getById(Long id) {
        return smsPlatformMapper.getById(id);
    }

}