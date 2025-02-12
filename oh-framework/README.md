## 说明
oh-framework是系统框架，依赖于`oh-core`包，包括鉴权拦截、数据库、多数据源、异常以及基础类等。
- 异步消息消费；实现JobService接口可异步消费消息，通过JobServiceConsumer.jobConsume 执行业务代码
- 在方法上使用 @OperateLog 注解可记录操作日志
- 使用PageHelper进行分页，也可以使用@Pages 注解进行分页操作 【2024年10月】
- 通过AOP、@Ds注解切换数据源,初始至少有2个数据源，分别是系统内置数据源和主数据源，两个数据源可以相同 【2024年10月】
- 通过@TableName、@TableField和@TableId注解，结合ProviderService动态SQL拼接，支持简单的新增、修改和删除功能，少写SQL 【2024年12月】
- 幂等注解 @Idempotent、@RequestKeyParam 加锁防止重复提交，限制请求频率 【2024年12月】
- 增加租户功能，隔离业务数据 【2025年1月】
- 支持Druid、Hikari连接池，可对连接池使用情况监控【2025年2月】
## 引入
根据实际版本引入，如下所示：

```xml
<dependency>
    <groupId>com.finn</groupId>
    <artifactId>oh-framework</artifactId>
    <version>${版本号}</version>
</dependency>
```
配置说明
```yaml
finn:
  multi-tenant: # 多租户配置
    dialect: mysql # 数据库方言，默认mysql
    tenant-id-field: tenant_id # 隔离字段名称，默认tenant_id
    table-pattern: ^sys_.* # 需要隔离的表名称（正则表达式）
    # 排除隔离的表（逗号分隔） sys_params,sys_version_info,sys_menu,sys_role_menu,sys_user_role,sys_user_post 已写到代码中
    ignore-table: sys_dict_type,sys_dict_data
  security:
    access-token-expire: 43200      # token有效期
    refresh-token-expire: 604800    # 刷新token有效期
    auth-count: 5                   # 多少次鉴权失败锁定，0表示不开启
    lock-time: 3600                 # 账号锁定时间(秒)
    ignore-urls:                    # 忽略鉴权的url
      - /swagger/**
      - /swagger-ui/**
      - /doc.html
  open-api: # 异步调用接口
    type: 2 # 1直接保存 2使用MQ异步保存
    auto-start-up: false # Kafka监听是否开启
    cache-time: 604800 # 日志缓存时间-秒，0不进行缓存，缓存日志可从redis中读取日志保存到表中
  xss:
    enabled: true
    exclude-urls:
      - /oh-generator/**
spring:
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource #数据源的类型，通过此配置判断连接池，支持 com.zaxxer.hikari.HikariDataSource
    sys-data-source:
      primary: masterDb # 主数据源或者数据源组,默认 masterDb
      sys-default: sysDb # 系统管理的数据源，默认 sysDb，用于基础管理的库，如果合并为一个库，则与主数据库相同
    dynamic: # 数据源配置，支持多数据源
      sysDb: # 数据源1 
        driver-class-name: com.mysql.cj.jdbc.Driver
        url: jdbc:mysql://127.0.0.1:3306/oh-sys?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&nullCatalogMeansCurrent=true
        username: root # 用户名
        password: 123456 # 密码
        initialSize: 10 # 初始连接数
        minIdle: 10 # 最小连接数
        maxActive: 100 # 最大连接数
        maxWait: 30000 # 获取连接时的最大等待时间，单位为毫秒。配置了maxWait后，默认启用公平锁
        maxLifetime: 1800000 # Hikari属性,控制池中连接的最长生命周期，值0表示无限生命周期，默认30分钟
        hikariLog: true # 是否开启hikari监控日志打印，默认false
        checkConnection: true # 初始化时是否检查连接，默认false
        filters: wall,stat # druid监控
        connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=500
      mysqlDb: # 数据源2 配置同 sysDb
        driver-class-name: com.mysql.cj.jdbc.Driver
```