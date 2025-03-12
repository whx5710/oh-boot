package com.finn.sys.base.service.impl;

import com.finn.core.utils.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.finn.sys.base.convert.SysLogLoginConvert;
import com.finn.sys.base.entity.SysLogLoginEntity;
import com.finn.sys.base.mapper.SysLogLoginMapper;
import com.finn.sys.base.query.SysLogLoginQuery;
import com.finn.sys.base.service.SysLogLoginService;
import com.finn.sys.base.vo.AnalysisVO;
import com.finn.sys.base.vo.SysLogLoginVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 登录日志
 *
 * @author 王小费 whx5710@qq.com
 *
 */
@Service
public class SysLogLoginServiceImpl implements SysLogLoginService {
    private final SysLogLoginMapper sysLogLoginMapper;

    public SysLogLoginServiceImpl(SysLogLoginMapper sysLogLoginMapper) {
        this.sysLogLoginMapper = sysLogLoginMapper;
    }

    @Override
    public PageResult<SysLogLoginVO> page(SysLogLoginQuery query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<SysLogLoginEntity> list = sysLogLoginMapper.getList(query);
        PageInfo<SysLogLoginEntity> pageInfo = new PageInfo<>(list);
        return new PageResult<>(SysLogLoginConvert.INSTANCE.convertList(pageInfo.getList()), pageInfo.getTotal());
    }

    @Override
    public void save(String username, Integer status, Integer operation, String tenantId) {
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        assert request != null;
        String userAgent = request.getHeader(HttpHeaders.USER_AGENT);
        String ip = IpUtils.getIpAddress(request);
        String address = "未知"; // AddressUtils.getAddressByIP(ip);

        SysLogLoginEntity entity = new SysLogLoginEntity();
        entity.setUsername(username);
        entity.setStatus(status);
        entity.setOperation(operation);
        entity.setIp(ip);
        entity.setAddress(address);
        entity.setUserAgent(userAgent);
        entity.setTenantId(tenantId);
//        sysLogLoginMapper.save(entity);
        sysLogLoginMapper.insert(entity);
    }

    @Override
    public void export() {
        List<SysLogLoginEntity> list = sysLogLoginMapper.getList(new SysLogLoginQuery());
        List<SysLogLoginVO> sysLogLoginVOS = SysLogLoginConvert.INSTANCE.convertList(list);
        ExcelUtils.excelExport(SysLogLoginVO.class, "登录日志", "日志", sysLogLoginVOS);
    }

    /**
     * 统计最近几天相关操作情况
     * @param day 天数,跟 mysql.help_topic 的数量有关，最好保护超过1年
     * @param operation 操作信息   0：登录成功   1：退出成功  2：验证码错误  3：账号密码错误
     * @return 统计情况
     */
    @Override
    public List<AnalysisVO> latestDate(int day, int operation) {
        return sysLogLoginMapper.latestDateCount(day, operation);
    }

}