package com.iris.system.base.service;

import com.iris.framework.common.utils.PageResult;
import com.iris.system.base.query.SysRoleUserQuery;
import com.iris.system.base.query.SysUserQuery;
import com.iris.system.base.vo.SysUserVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 用户管理
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public interface SysUserService {

    PageResult<SysUserVO> page(SysUserQuery query);

    // 被锁定、待锁定的用户列表（由于有次数限制，此方法不判断多少次被锁定）
    PageResult<SysUserVO> clockPage(SysUserQuery query);

    // 解锁用户
    void unlock(String userName);

    void save(SysUserVO vo);

    void update(SysUserVO vo);

    void delete(List<Long> idList);

    SysUserVO getByMobile(String mobile);

    SysUserVO info(Long userId);

    /**
     * 修改密码
     *
     * @param id          用户ID
     * @param newPassword 新密码
     */
    void updatePassword(Long id, String newPassword);

    /**
     * 分配角色，用户列表
     */
    PageResult<SysUserVO> roleUserPage(SysRoleUserQuery query);

    /**
     * 批量导入用户
     *
     * @param file     excel文件
     * @param password 密码
     */
    void importByExcel(MultipartFile file, String password);

    /**
     * 导出用户信息表格
     */
    void export();
}
