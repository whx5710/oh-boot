package com.finn.system.service;

import com.finn.core.utils.PageResult;
import com.finn.system.query.LogLoginQuery;
import com.finn.system.vo.AnalysisVO;
import com.finn.system.vo.LogLoginVO;

import java.util.List;

/**
 * 登录日志
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public interface LogLoginService {

    /**
     * Page result.
     *
     * @param query the query
     * @return the page result
     */
    PageResult<LogLoginVO> page(LogLoginQuery query);

    /**
     * 保存登录日志
     *
     * @param username  用户名
     * @param status    登录状态
     * @param operation 操作信息
     */
    void save(String username, Integer status, Integer operation, String tenantId);

    /**
     * 导出登录日志
     */
    void export(LogLoginQuery query);

    /**
     * 统计最近几天相关操作情况
     * @param day 天数,跟 mysql.help_topic 的数量有关，最好保护超过1年
     * @param operation 操作信息   0：登录成功   1：退出成功  2：验证码错误  3：账号密码错误
     * @return 统计情况
     */
    List<AnalysisVO> latestDateCount(int day, int operation);

    /**
     * 删除日志
     * @param idList
     */
    void delete(List<Long> idList);

    /**
     * 根据日期删除日志（删除日期之前的数据）
     * @param date
     */
    void deleteByDate(String date);
}