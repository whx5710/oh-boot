package com.iris.system.pim.service.impl;

import cn.hutool.core.util.StrUtil;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fhs.trans.service.impl.TransService;
import com.iris.framework.common.utils.*;
import com.iris.system.pim.service.SysLogLoginService;
import com.iris.system.pim.vo.AnalysisVO;
import jakarta.servlet.http.HttpServletRequest;
import com.iris.framework.mybatis.service.impl.BaseServiceImpl;
import com.iris.system.pim.convert.SysLogLoginConvert;
import com.iris.system.pim.dao.SysLogLoginDao;
import com.iris.system.pim.entity.SysLogLoginEntity;
import com.iris.system.pim.query.SysLogLoginQuery;
import com.iris.system.pim.vo.SysLogLoginVO;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 登录日志
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Service
public class SysLogLoginServiceImpl extends BaseServiceImpl<SysLogLoginDao, SysLogLoginEntity> implements SysLogLoginService {
    private final TransService transService;

    public SysLogLoginServiceImpl(TransService transService) {
        this.transService = transService;
    }

    @Override
    public PageResult<SysLogLoginVO> page(SysLogLoginQuery query) {
        IPage<SysLogLoginEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));

        return new PageResult<>(SysLogLoginConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
    }

    private LambdaQueryWrapper<SysLogLoginEntity> getWrapper(SysLogLoginQuery query) {
        LambdaQueryWrapper<SysLogLoginEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.like(StrUtil.isNotBlank(query.getUsername()), SysLogLoginEntity::getUsername, query.getUsername());
        wrapper.like(StrUtil.isNotBlank(query.getAddress()), SysLogLoginEntity::getAddress, query.getAddress());
        wrapper.like(query.getStatus() != null, SysLogLoginEntity::getStatus, query.getStatus());
        wrapper.orderByDesc(SysLogLoginEntity::getId);

        return wrapper;
    }

    @Override
    public void save(String username, Integer status, Integer operation) {
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        assert request != null;
        String userAgent = request.getHeader(HttpHeaders.USER_AGENT);
        String ip = IpUtils.getIpAddr(request);
        String address = AddressUtils.getAddressByIP(ip);

        SysLogLoginEntity entity = new SysLogLoginEntity();
        entity.setUsername(username);
        entity.setStatus(status);
        entity.setOperation(operation);
        entity.setIp(ip);
        entity.setAddress(address);
        entity.setUserAgent(userAgent);

        baseMapper.insert(entity);
    }

    @Override
    public void export() {
        List<SysLogLoginEntity> list = list();
        List<SysLogLoginVO> sysLogLoginVOS = SysLogLoginConvert.INSTANCE.convertList(list);
        transService.transBatch(sysLogLoginVOS);
        // 写到浏览器打开
        ExcelUtils.excelExport(SysLogLoginVO.class, "system_login_log_excel" + DateUtils.format(new Date()), null, sysLogLoginVOS);
    }

    /**
     * 统计最近几天相关操作情况
     * @param day 天数,跟 mysql.help_topic 的数量有关，最好保护超过1年
     * @param operation 操作信息   0：登录成功   1：退出成功  2：验证码错误  3：账号密码错误
     * @return 统计情况
     */
    @Override
    public List<AnalysisVO> latestDate(int day, int operation) {
        return baseMapper.latestDateCount(day, operation);
    }

}