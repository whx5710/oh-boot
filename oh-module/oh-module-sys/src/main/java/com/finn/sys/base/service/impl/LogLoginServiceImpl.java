package com.finn.sys.base.service.impl;

import com.finn.core.utils.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.finn.sys.base.convert.LogLoginConvert;
import com.finn.sys.base.entity.LogLoginEntity;
import com.finn.sys.base.mapper.LogLoginMapper;
import com.finn.sys.base.query.LogLoginQuery;
import com.finn.sys.base.service.LogLoginService;
import com.finn.sys.base.vo.AnalysisVO;
import com.finn.sys.base.vo.LogLoginVO;
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
public class LogLoginServiceImpl implements LogLoginService {
    private final LogLoginMapper logLoginMapper;

    public LogLoginServiceImpl(LogLoginMapper logLoginMapper) {
        this.logLoginMapper = logLoginMapper;
    }

    @Override
    public PageResult<LogLoginVO> page(LogLoginQuery query) {
        Page<LogLoginEntity> page = PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<LogLoginEntity> list = logLoginMapper.getList(query);
        return new PageResult<>(LogLoginConvert.INSTANCE.convertList(list), page.getTotal());
    }

    @Override
    public void save(String username, Integer status, Integer operation, String tenantId) {
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        assert request != null;
        String userAgent = request.getHeader(HttpHeaders.USER_AGENT);
        String ip = IpUtils.getIpAddress(request);
        String address = "未知"; // AddressUtils.getAddressByIP(ip);

        LogLoginEntity entity = new LogLoginEntity();
        entity.setUsername(username);
        entity.setStatus(status);
        entity.setOperation(operation);
        entity.setIp(ip);
        entity.setAddress(address);
        entity.setUserAgent(userAgent);
        entity.setTenantId(tenantId);
//        sysLogLoginMapper.save(entity);
        logLoginMapper.insert(entity);
    }

    @Override
    public void export() {
        List<LogLoginEntity> list = logLoginMapper.getList(new LogLoginQuery());
        List<LogLoginVO> logLoginVOS = LogLoginConvert.INSTANCE.convertList(list);
        ExcelUtils.excelExport(LogLoginVO.class, "登录日志", "日志", logLoginVOS);
    }

    /**
     * 统计最近几天相关操作情况
     * @param day 天数,跟 mysql.help_topic 的数量有关，最好保护超过1年
     * @param operation 操作信息   0：登录成功   1：退出成功  2：验证码错误  3：账号密码错误
     * @return 统计情况
     */
    @Override
    public List<AnalysisVO> latestDate(int day, int operation) {
        return logLoginMapper.latestDateCount(day, operation);
    }

}