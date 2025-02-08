package com.finn.sys.base.convert;

import com.finn.sys.base.entity.SysMenuEntity;
import com.finn.sys.base.vo.SysMenuTreeVO;
import com.finn.sys.base.vo.SysMenuVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 系统菜单
 * @since 1.0.0 2023-10-03
 * @author 王小费 whx5710@qq.com
 *
 */
@Mapper
public interface SysMenuConvert {
    SysMenuConvert INSTANCE = Mappers.getMapper(SysMenuConvert.class);

    SysMenuEntity convert(SysMenuTreeVO vo);

    SysMenuTreeVO convert(SysMenuEntity entity);

    List<SysMenuTreeVO> convertTreeList(List<SysMenuEntity> list);

    List<SysMenuVO> convertList(List<SysMenuEntity> list);

}
