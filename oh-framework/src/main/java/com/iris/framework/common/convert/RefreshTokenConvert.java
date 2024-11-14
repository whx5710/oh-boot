package com.iris.framework.common.convert;

import com.iris.framework.security.user.RefreshTokenInfo;
import com.iris.framework.security.user.UserDetail;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 刷新token信息转换
 *
 * @author 王小费 whx5710@qq.com
 */
@Mapper
public interface RefreshTokenConvert {
    RefreshTokenConvert INSTANCE = Mappers.getMapper(RefreshTokenConvert.class);

    RefreshTokenInfo convert(UserDetail entity);

}
