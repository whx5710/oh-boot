package com.finn.sys.base.convert;

import com.finn.sys.base.entity.MenuEntity;
import com.finn.sys.base.vo.MenuTreeVO;
import com.finn.sys.base.vo.MenuVO;
import com.finn.sys.base.vo.RouteMetaVO;
import com.finn.sys.base.vo.RouteVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 系统菜单
 * @since 1.0.0 2023-10-03
 * @author 王小费 whx5710@qq.com
 *
 */
@Mapper
public interface MenuConvert {
    MenuConvert INSTANCE = Mappers.getMapper(MenuConvert.class);

    MenuEntity convert(RouteVO vo);

    @Mapping(target = "authority", ignore = true) // 忽略authority
    MenuEntity convert(RouteMetaVO vo);

    MenuTreeVO convert(MenuEntity entity);

    List<MenuTreeVO> convertTreeList(List<MenuEntity> list);

    List<MenuVO> convertList(List<MenuEntity> list);

}
