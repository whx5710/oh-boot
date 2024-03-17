package com.iris.system.pim.dao;

import java.util.List;

import com.iris.system.pim.entity.SysLogLoginEntity;
import com.iris.framework.mybatis.dao.BaseDao;
import com.iris.system.pim.vo.AnalysisVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 登录日志
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
@Mapper
public interface SysLogLoginDao extends BaseDao<SysLogLoginEntity> {

    /**
     * 统计最近几天相关操作情况
     * @param day 天数,跟 mysql.help_topic 的数量有关，最好保护超过1年
     * @param operation 操作信息   0：登录成功   1：退出成功  2：验证码错误  3：账号密码错误
     * @return 统计情况
     */
    List<AnalysisVO> latestDateCount(@Param("day")int day, @Param("operation")int operation);
}