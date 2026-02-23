package com.finn.system.service;

import com.finn.core.utils.PageResult;
import com.finn.system.entity.UserEntity;
import com.finn.system.query.RoleUserQuery;
import com.finn.system.query.UserQuery;
import com.finn.system.vo.UserVO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 用户管理
 *
 * @author 王小费 whx5710@qq.com
 * 
 */
public interface UserService {

    PageResult<UserVO> page(UserQuery query);

    PageResult<UserVO> pageByRole(UserQuery query);

    // 被锁定、待锁定的用户列表（由于有次数限制，此方法不判断多少次被锁定）
    PageResult<UserVO> clockPage(UserQuery query);

    // 解锁用户
    void unlock(String userName);

    void save(UserVO vo);

    void update(UserVO vo);

    /**
     * 逻辑删除
     * @param idList
     */
    void delete(List<Long> idList);

    UserVO getByMobile(String mobile);

    UserVO info(Long userId);

    /**
     * 根据用户ID获取用户信息
     * @param userId
     * @return
     */
    UserEntity getUser(Long userId);

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
    PageResult<UserVO> roleUserPage(RoleUserQuery query);

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
    void export(UserQuery query);
    /**
     * 解除绑定租户用户
     * @param tenantID 租户ID
     * @param userIdList 用户ID
     */
    void unBindTenantUser(String tenantID, List<Long> userIdList);

    /**
     * 绑定租户用户
     * @param tenantID 租户ID
     * @param userIdList 用户ID
     */
    void bindTenantUser(String tenantID, List<Long> userIdList);
    /**
     * 重置密码
     * @param userId 用户ID
     * @return 密码
     */
    String resetPwd(Long userId);

    /**
     * 获取 UserDetails 对象
     */
    UserDetails getUserDetails(UserEntity userEntity);


    UserEntity getByUsername(String username);

}
