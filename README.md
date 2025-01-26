## 项目说明

oh-boot 是采用SpringBoot3.0、SpringSecurity6.0、Mybatis（如需改Mybatis-Plus请自行引入依赖包）、Druid连接池、Kafka等框架开发的一套SpringBoot快速开发平台。
- 采用组件模式，扩展不同的业务功能，可以很方便的实现各种业务需求，若想使用某个组件，按需引入即可。
- 支持定时任务（分布式可使用xxl-job）、文件存储、短信对接等。
- 前端集成bpmn.js，使用camunda流程引擎，画流程如此简单。
- 完善的资源监控，可监控服务器资源，数据库连接等。
- 保姆级注释，确保每行代码都能看懂，开发无忧。
- 严格的接口角色权限控制，拒绝垂直越权。
- 通用接口，可对接入客户端接口级别授权。加入Kafka/RocketMq，支持MQ异步接口，支持海量请求。
- 通过Druid连接池支持多数据源，通过前端参数或后端AOP、@Ds注解切换数据源，@Pages分页 【2024年10月】
- 通过@TableName、@TableField和@TableId注解，结合ProviderService动态SQL拼接，支持简单的新增、修改和删除功能，少写SQL 【2024年12月】
- 幂等注解 @Idempotent、@RequestKeyParam 加锁防止重复提交，限制请求频率 【2024年12月】
- 增加租户功能，隔离业务数据 【2025年1月】
- 工程代码：https://gitee.com/whx233/oh-boot
- - 独立系统管理 https://gitee.com/whx233/oh-sys (从oh-boot剥离)
- - 前端工程    https://gitee.com/whx233/oh-admin (暂未开源 Vue3)
- 开发文档：
- 演示环境：
- 官网地址：

## 代码目录  
`oh-core`、`oh-framework`属于系统框架类模块，分别是核心包和框架包，各自存放相关的代码；项目上可独立打成jar包引入
```
oh-core      核心系统框架，最基础的系统功能，包括缓存、异常、基础工具类
oh-framework 系统框架，包括数据库、基础实体类、鉴权
oh-support   基础组织机构信息，如人员、角色、部门等，业务模块中可按需引入
oh-server    系统启动入口【根据实际情况，可集成到某个模块中】
oh-module    业务模块
    - oh-module-sys    系统管理模块，包括登陆鉴权、基础配置等
    - oh-module-app    对外服务接口
    - oh-module-flow   工作流程管理
    - oh-module-team   协同管理【业务功能，未开发】
    - ...              扩展其他业务模块
```
通过`oh-core`、`oh-framework`即可很方便、快捷的搭建开发环境；比如需将系统管理的基础功能独立成一个工程

## 快速开始
环境：JDK17+、MySQL8+、Redis、Kafka(RocketMq)
- 1、克隆项目 `git clone https://gitee.com/whx233/oh-boot.git`
- 2、创建数据库，分别创建 oh-sys 和 oh-boot数据库（可合并成一个库），并分别执行db\mysql目录下的SQL脚本
- 3、根据实际情况修改application-xxx.yml 配置，包括MySQL、Redis、Kafka/RocketMq（如果需要）、文件存储路径等
- 4、启动服务 `com.iris.ServerApplication`
- 5、API文档地址：http://localhost:8080/doc.html 数据库监控地址：http://localhost:8080/druid/login.html

## 配置说明
```yaml
iris:
  security:
    access-token-expire: 7200      # 过期时间-秒，2小时过期
    refresh-token-expire: 43200    # 刷新token-秒 12小时
    auth-count: 5                   # 多少次鉴权失败锁定，0表示不开启
    lock-time: 3600                 # 账号锁定时间(秒)
    ignore-urls:                    # 忽略鉴权的url
      - /swagger-ui/**
      - /druid/**
  open-api:
    type: 2 # 1直接保存 2使用MQ异步保存，默认直接保存
    auto-start-up: false # Kafka监听是否开启,默认不开启false
    cache-time: 604800 # 缓存时间-秒，0不进行缓存，缓存日志可从redis中读取日志保存到表中
  xss:
    enabled: true
    exclude-urls:
      - /oh-generator/**
  # 文件存储相关
  storage:
    enabled: true
    config:
      type: local  # 存储类型：local、aliyun、tencent、qiniu、huawei、minio
      domain: http://localhost:8080
    local.path: /data/图片/upload
# https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html
spring:
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource #数据源的类型
    multi-tenant: # 多租户配置
      dialect: mysql # 数据库方言，默认mysql
      tenant-id-field: tenant_id # 隔离字段名称，默认tenant_id
      table-pattern: ^sys_.* # 需要隔离的表名称（正则表达式）
      # 排除隔离的表（逗号分隔） sys_params,sys_version_info,sys_menu,sys_role_menu,sys_user_role,sys_user_post 已写到代码中
      ignore-table: sys_dict_type,sys_dict_data
    sys-data-source:
      primary: masterDb # 主数据源或者数据源组,默认 masterDb
      sys-default: sysDb # 系统管理的数据源，默认 sysDb，用于基础管理的库，如果合并为一个库，则与主数据库相同
    dynamic: # 数据源配置，支持多数据源
      sysDb: # 数据源1 
        driver-class-name: com.mysql.cj.jdbc.Driver
        url: jdbc:mysql://127.0.0.1:3306/oh-sys?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&nullCatalogMeansCurrent=true
        username: root
        password: 123456
        # 以下与druid的配置对应
        initialSize: 10
        minIdle: 10
        maxActive: 100
        filters: wall,stat
        connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=500
        checkConnection: true # 初始化时是否检查连接，默认false
      mysqlDb: # 数据源2 配置同 sysDb
        driver-class-name: com.mysql.cj.jdbc.Driver
    druid:
      stat-view-servlet:
        enabled: true
        url-pattern: /druid/*
        login-username: admin
        login-password: admin # 正式环境需配置复杂密码
      web-stat-filter: # Druid Web统计过滤器配置
        enabled: true # 启用Web统计过滤器
        session-stat-enable: true # 启用会话统计功能
        session-stat-max-count: 1000 # 最大会话统计数量
# 日志信息
logging:
  # 字符集设置
  charset:
    file: UTF-8
    console: UTF-8
  level:  # 默认的全局日志级别 TRACE, DEBUG, INFO, WARN, ERROR, FATAL, OFF
    com.iris: DEBUG
    org.springframework.web: debug # web相关的日志级别

```
## 沟通交流

邮箱：whx5710@qq.com 【王小费】


## 效果图

![输入图片说明](images/md-0.png)

![输入图片说明](images/md-1.png)

![输入图片说明](images/md-2.png)

![输入图片说明](images/md-3.png)

![输入图片说明](images/md-4.png)

![输入图片说明](images/md-5.png)

![输入图片说明](images/md-6.png)

![输入图片说明](images/md-7.png)

![输入图片说明](images/md-8.png)

![输入图片说明](images/md-9.png)
