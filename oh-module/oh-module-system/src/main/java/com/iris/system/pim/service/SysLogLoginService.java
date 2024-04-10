package com.iris.system.pim.service;

import com.iris.system.pim.entity.SysLogLoginEntity;

import java.util.List;

import com.iris.framework.common.utils.PageResult;
import com.iris.framework.datasource.service.BaseService;
import com.iris.system.pim.query.SysLogLoginQuery;
import com.iris.system.pim.vo.AnalysisVO;
import com.iris.system.pim.vo.SysLogLoginVO;

/**
 * 登录日志
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public interface SysLogLoginService extends BaseService<SysLogLoginEntity> {

    /**
     * Page result.
     *
     * @param query the query
     * @return the page result
     */
    PageResult<SysLogLoginVO> page(SysLogLoginQuery query);

    /**
     * 保存登录日志
     *
     * @param username  用户名
     * @param status    登录状态
     * @param operation 操作信息
     */
    void save(String username, Integer status, Integer operation);

    /**
     * 导出登录日志
     */
    void export();

    /**
     * 统计最近几天相关操作情况
     * @param day 天数,跟 mysql.help_topic 的数量有关，最好保护超过1年
     * @param operation 操作信息   0：登录成功   1：退出成功  2：验证码错误  3：账号密码错误
     * @return 统计情况
     */
    List<AnalysisVO> latestDate(int day, int operation);
}