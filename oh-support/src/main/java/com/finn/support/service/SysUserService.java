package com.finn.support.service;

import com.finn.core.utils.PageResult;
import com.finn.support.entity.SysUserEntity;
import com.finn.support.query.SysRoleUserQuery;
import com.finn.support.query.SysUserQuery;
import com.finn.support.vo.SysUserVO;
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
     * 根据用户ID获取用户信息
     * @param userId
     * @return
     */
    SysUserEntity getUser(Long userId);

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
    /**
     * 更新租户
     * @param tenantID 组合ID
     * @param userIdList 用户ID
     * @param flag 1 绑定 2 解绑
     */
    void updateTenantUser(String tenantID, List<Long> userIdList, Integer flag);

    /**
     * 重置密码
     * @param userId 用户ID
     * @return 密码
     */
    String resetPwd(Long userId);
}
