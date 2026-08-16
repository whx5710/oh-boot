package com.finn.system.convert;

import com.finn.system.entity.LoginLogEntity;
import com.finn.system.vo.LoginLogVO;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 登录日志
 * @since 1.0.0 2023-10-03
 * @author 王小费 whx5710@qq.com
 * 
 */
@Mapper
@DecoratedWith(LogLoginExtConvert.class) // 指定实现类
public interface LoginLogConvert {
    LoginLogConvert INSTANCE = Mappers.getMapper(LoginLogConvert.class);

    LoginLogEntity convert(LoginLogVO vo);

    LoginLogVO convert(LoginLogEntity entity);

    List<LoginLogVO> convertList(List<LoginLogEntity> list);

}