package com.iris.framework.common.constant;

/**
 * 常量
 *
 * @author 王小费 whx5710@qq.com
 *
 */
public interface Constant {
    /**
     * 根节点标识
     */
    Long ROOT = 0L;
    /**
     * 当前页码
     */
    String PAGE = "page";
    /**
     * 数据权限
     */
    String DATA_SCOPE = "dataScope";
    /**
     * 超级管理员
     */
    Integer SUPER_ADMIN = 1;
    /**
     * 禁用
     */
    Integer DISABLE = 0;
    /**
     * 启用
     */
    Integer ENABLE = 1;
    /**
     * 失败
     */
    Integer FAIL = 0;
    /**
     * 成功
     */
    Integer SUCCESS = 1;
    /**
     * OK
     */
    String OK = "OK";
    /**
     * token前缀，注意有空格
     */
    String TOKEN_PREFIX = "Bearer ";

    /**
     * 令牌key
     */
    String ACCESS_TOKEN = "access_token";

    /**
     * TOKEN HEADER KEY
     */
    String AUTHORIZATION = "Authorization";

    /**
     * pgsql的driver
     */
    String PGSQL_DRIVER = "org.postgresql.Driver";

    /**
     * 密码最小长度
     */
    int PASSWORD_MIN_LENGTH = 6;

    /**
     * 密码最大长度
     */
    int PASSWORD_MAX_LENGTH = 30;
    /**
     * 业务主库
     */
    String MASTER_DB = "masterDb";

    /**
     * 系统内部数据源
     */
    String SYS_DB = "sysDb";


    /**
     * 客户端ID
     */
    String CLIENT_ID = "OH-CLIENT-ID";
    /**
     * 密钥
     */
    String SECRET_KEY = "OH-SECRET-KEY";
    /**
     * 功能号
     */
    String FUNC_CODE = "OH-FUNC-CODE";
    /**
     * 消息主题
     */
    String TOPIC_SUBMIT = "topic-submit";

    /**
     * 公共接口标识
     */
    String OPEN_API = "openApi";

}