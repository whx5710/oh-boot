package com.finn.support.service.impl;

import com.finn.core.constant.Constant;
import com.finn.core.exception.ServerException;
import com.finn.core.utils.ExcelUtils;
import com.finn.core.utils.HttpContextUtils;
import com.finn.core.utils.IpUtils;
import com.finn.core.utils.PageResult;
import com.finn.framework.datasource.annotations.Ds;
import com.finn.framework.datasource.utils.Wrapper;
import com.finn.framework.datasource.utils.DeleteWrapper;
import com.finn.framework.datasource.utils.QueryWrapper;
import com.finn.support.convert.LogLoginConvert;
import com.finn.support.entity.LogLoginEntity;
import com.finn.support.mapper.LogLoginMapper;
import com.finn.support.query.LogLoginQuery;
import com.finn.support.service.LogLoginService;
import com.finn.support.vo.AnalysisVO;
import com.finn.support.vo.LogLoginVO;
import com.github.pagehelper.Page;
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
@Ds(Constant.DYNAMIC_SYS_DB)
public class LogLoginServiceImpl implements LogLoginService {
    private final LogLoginMapper logLoginMapper;

    public LogLoginServiceImpl(LogLoginMapper logLoginMapper) {
        this.logLoginMapper = logLoginMapper;
    }

    @Override
    public PageResult<LogLoginVO> page(LogLoginQuery query) {
        try (Page<LogLoginEntity> page = logLoginMapper.selectPageByWrapper(this.buildParams(query).page(query.getPageNum(), query.getPageSize()))) {
            return new PageResult<>(LogLoginConvert.INSTANCE.convertList(page.getResult()), page.getTotal());
        }
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
        logLoginMapper.save(entity);
//        logLoginMapper.insert(entity);
    }

    @Override
    public void export(LogLoginQuery query) {
        List<LogLoginEntity> list = logLoginMapper.selectListByWrapper(buildParams(query));
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
    public List<AnalysisVO> latestDateCount(int day, int operation) {
        return logLoginMapper.latestDateCount(day, operation);
    }

    @Override
    public void delete(List<Long> idList) {
        if(idList == null || idList.isEmpty()){
            throw new ServerException("ID不能为空");
        }
        for(Long id: idList){
            LogLoginEntity log = new LogLoginEntity();
            log.setId(id);
            logLoginMapper.delete(log);
        }
    }

    /**
     * 根据日期删除日志（删除日期之前的数据）
     * @param date
     */
    @Override
    public void deleteByDate(String date) {
        logLoginMapper.deleteByWrapper(DeleteWrapper.of(LogLoginEntity.class).le(LogLoginEntity::getCreateTime, date));
    }

    private Wrapper<LogLoginEntity> buildParams(LogLoginQuery query){
        return QueryWrapper.of(LogLoginEntity.class)
                .like(LogLoginEntity::getUsername, query.getUsername())
                .like(LogLoginEntity::getAddress, query.getAddress())
                .eq(LogLoginEntity::getStatus, query.getStatus())
                .ge(LogLoginEntity::getCreateTime, query.getStartTime())
                .le(LogLoginEntity::getCreateTime, query.getEndTime())
                .eq(LogLoginEntity::getTenantId, query.getTenantId())
                .orderBy("id desc");
    }
}