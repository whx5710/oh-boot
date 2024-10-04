package com.iris.sys.base.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iris.framework.common.utils.*;
import com.iris.sys.base.mapper.SysLogLoginMapper;
import com.iris.sys.base.query.SysLogLoginQuery;
import com.iris.sys.base.vo.AnalysisVO;
import com.iris.sys.base.vo.SysLogLoginVO;
import com.iris.sys.base.service.SysLogLoginService;
import jakarta.servlet.http.HttpServletRequest;
import com.iris.sys.base.convert.SysLogLoginConvert;
import com.iris.sys.base.entity.SysLogLoginEntity;
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
public class SysLogLoginServiceImpl implements SysLogLoginService {
//    private final TransService transService;
    private final SysLogLoginMapper sysLogLoginMapper;

    public SysLogLoginServiceImpl(SysLogLoginMapper sysLogLoginMapper) {
        //this.transService = transService;
        this.sysLogLoginMapper = sysLogLoginMapper;
    }

    @Override
    public PageResult<SysLogLoginVO> page(SysLogLoginQuery query) {
        PageHelper.startPage(query.getPage(), query.getLimit());
        List<SysLogLoginEntity> list = sysLogLoginMapper.getList(query);
        PageInfo<SysLogLoginEntity> pageInfo = new PageInfo<>(list);
        return new PageResult<>(SysLogLoginConvert.INSTANCE.convertList(pageInfo.getList()), pageInfo.getTotal());
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
        sysLogLoginMapper.save(entity);
    }

    @Override
    public void export() {
        List<SysLogLoginEntity> list = sysLogLoginMapper.getList(new SysLogLoginQuery());
        List<SysLogLoginVO> sysLogLoginVOS = SysLogLoginConvert.INSTANCE.convertList(list);
//        transService.transBatch(sysLogLoginVOS);
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
        return sysLogLoginMapper.latestDateCount(day, operation);
    }

}