# 项目开发规范

## 技术栈

- **框架**: SpringBoot 4.0.6 + SpringSecurity 7.1.0-M2 + Mybatis
- **语言**: Java 21
- **构建工具**: Maven
- **数据库**: PostgreSQL（支持 MySQL，通过 mapper 目录区分）
- **连接池**: HikariCP
- **缓存**: Redis
- **消息队列**: Kafka
- **其他**: WebSocket、MapStruct 1.6.3、PageHelper、smart-doc

## 项目结构

### 模块划分

```
oh-boot                    # 父项目
├── oh-framework           # 框架核心模块（公共组件、配置、工具类）
├── oh-module              # 业务模块集合
│   ├── oh-module-app      # 应用管理模块（OpenAPI、数据应用）
│   └── oh-module-flow     # 工作流模块
└── oh-system              # 系统管理模块（用户、角色、权限等）
```

### 包结构

```
com.finn.{module}
├── controller      # 控制层，RESTful API 接口
├── service         # 业务层接口
│   └── impl        # 业务层实现
├── mapper          # MyBatis 数据访问层接口
├── entity          # 实体类（与数据库表对应）
├── vo              # 视图对象（返回给前端的数据）
├── query           # 查询参数对象（继承 Query 基类）
├── convert         # MapStruct 对象转换接口
├── enums           # 枚举类
├── config          # 配置类
├── cache           # 缓存操作类
└── utils           # 工具类
```

## 编码规范

### 1. 命名规范

- **包名**: 全小写，如 `com.finn.system`
- **类名**: 大驼峰，如 `UserEntity`、`UserServiceImpl`
- **接口名**: 大驼峰，如 `UserService`、`UserMapper`
- **方法名**: 小驼峰，如 `getUserById`、`saveOrUpdate`
- **变量名**: 小驼峰，如 `userName`、`deptId`
- **常量名**: 全大写下划线，如 `MAX_RETRY_COUNT`

### 2. 类文件命名约定

| 类型 | 后缀 | 示例 |
|------|------|------|
| 实体类 | Entity | UserEntity |
| 视图对象 | VO | UserVO |
| 查询参数 | Query | UserQuery |
| 转换接口 | Convert | UserConvert |
| 服务接口 | Service | UserService |
| 服务实现 | ServiceImpl | UserServiceImpl |
| 数据访问 | Mapper | UserMapper |
| 控制器 | Controller | UserController |
| 枚举 | Enum | UserStatusEnum |
| 配置类 | Config | SecurityConfig |

### 3. 实体类规范

#### 继承体系

```
SuperEntity (序列化基础)
    └── IDEntity (含 id 字段)
            └── BaseEntity (含 creator、create_time、updater、update_time、db_status)
```

#### 使用场景

| 基类 | 适用场景 |
|------|----------|
| `SuperEntity` | 仅需要序列化能力的简单实体 |
| `IDEntity` | 只需要 ID 字段的实体 |
| `BaseEntity` | 需要完整审计字段（创建人、创建时间、更新人、更新时间、数据状态）的实体 |

#### 编码规范

- 实体类根据业务需求选择继承 `BaseEntity`、`IDEntity` 或 `SuperEntity`
- 使用 `@TableName` 注解指定表名
- 使用 `@TableField` 注解映射字段（下划线转驼峰）
- 非数据库字段使用 `@TableField(exists = false)`
- 必须包含字段注释说明
- **不使用 Lombok**，使用原生 getter/setter 方法

```java
@TableName("sys_user")
public class UserEntity extends BaseEntity {
    /**
     * 用户名
     */
    private String username;

    /**
     * 真实姓名
     */
    @TableField("real_name")
    private String realName;

    // 非数据库字段
    @TableField(exists = false)
    private String deptName;

    // getter/setter 方法
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // ... 其他 getter/setter
}
```

### 4. Controller 规范

- 使用 `@RestController` 注解
- 使用 `@RequestMapping` 指定基础路径，如 `/sys/user`
- 权限控制使用 `@PreAuthorize("hasAuthority('sys:user:page')")`
- 操作日志使用 `@Log(module = "用户管理", name = "保存", type = OperateTypeEnum.INSERT)`
- 参数校验使用 `@Valid`
- 返回统一结果 `Result<T>`
- 构造函数注入依赖

```java
@RestController
@RequestMapping("/sys/user")
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("page")
    @PreAuthorize("hasAuthority('sys:user:page')")
    public Result<PageResult<UserVO>> page(@Valid UserQuery query) {
        PageResult<UserVO> page = userService.page(query);
        return Result.ok(page);
    }
}
```

### 5. Service 规范

- 接口定义在 `service` 包，实现在 `service.impl` 包
- 使用 `@Service` 注解
- 优先使用构造函数注入，必要时使用 `@Resource`
- 分页查询使用 PageHelper（通过 `@Pages` 注解或 PageHelper.startPage）
- 对象转换使用 MapStruct
- 业务异常使用 `ServerException`
- 参数校验使用 `AssertUtils`

```java
@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public PageResult<UserVO> page(UserQuery query) {
        Page<UserEntity> page = PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<UserEntity> list = userMapper.getList(query);
        List<UserVO> voList = UserConvert.INSTANCE.convertList(list);
        return new PageResult<>(voList, page.getTotal());
    }
}
```

### 6. Mapper 规范

- 继承框架 `BaseMapper<T>`
- 使用 `@Mapper` 注解
- 简单查询使用 `@Select` 注解
- 复杂查询使用 XML 配置
- 分页方法使用 `@Pages` 注解
- 参数使用 `@Param` 注解

```java
@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {
    List<UserEntity> getList(@Param("params") UserQuery params);

    @Pages
    List<UserEntity> pageByRole(@Param("params") UserQuery params);

    @Select("select * from sys_user where id = #{id}")
    UserEntity getById(@Param("id") Long id);
}
```

### 7. XML 规范

- 存放路径：`mapper/postgres/` 或 `mapper/mysql/`
- 使用 `<if>` 进行动态条件判断
- 字符串判空：`params.name != null and params.name.trim() != ''`
- 集合判空：`params.list != null and params.list.size() > 0`
- 模糊查询使用 `concat('%',#{params.name},'%')`

### 8. VO 规范

- 实现 `Serializable` 接口
- 使用 `serialVersionUID`
- 使用 Bean Validation 注解进行参数校验
- 敏感字段使用 `@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)`

```java
public class UserVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "用户名不能为空")
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    // getter/setter 方法
}
```

### 9. Query 规范

- 继承 `Query` 基类（提供 pageNum、pageSize、sort、order 等通用分页参数）
- 使用 Bean Validation 注解进行参数校验
- 日期范围查询使用 startDate、endDate 字段

```java
public class UserQuery extends Query {
    /**
     * 用户名
     */
    private String username;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 开始时间
     */
    private LocalDate startDate;

    /**
     * 结束时间
     */
    private LocalDate endDate;

    // getter/setter 方法
}
```

### 10. 枚举规范

- 定义 `value` 和 `name` 属性
- 提供 `getNameByValue` 和 `getValueByName` 静态方法
- 使用 `final` 修饰属性

```java
public enum UserStatusEnum {
    DISABLE(0, "停用"),
    ENABLED(1, "正常");

    private final int value;
    private final String name;

    UserStatusEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static String getNameByValue(int value) {
        for (UserStatusEnum e : values()) {
            if (e.value == value) {
                return e.name;
            }
        }
        return "";
    }

    public static Integer getValueByName(String name) {
        for (UserStatusEnum e : values()) {
            if (e.name.equals(name)) {
                return e.value;
            }
        }
        return null;
    }
}
```

### 11. MapStruct 转换规范

- 转换接口使用 `@Mapper` 注解
- 复杂转换使用 `@DecoratedWith` 指定扩展实现
- 定义 `INSTANCE` 单例

```java
@Mapper
@DecoratedWith(UserExtConvert.class)
public interface UserConvert {
    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    UserVO convert(UserEntity entity);
    UserEntity convert(UserVO vo);
    List<UserVO> convertList(List<UserEntity> list);
}
```

## 安全规范

- 密码必须使用 `PasswordEncoder` 加密存储
- 敏感字段序列化时限制访问权限
- 接口必须使用 `@PreAuthorize` 进行权限控制
- 用户操作使用 `@Log` 记录操作日志

## 数据库规范

- 逻辑删除字段：`db_status`（1 正常，0 删除）
- 时间字段：`create_time`、`update_time`（LocalDateTime 类型）
- 操作人字段：`creator`、`updater`（Long 类型）
- 表名前缀：`sys_`（系统表）、`app_`（应用模块）、`flow_`（工作流模块）
- 字段命名：下划线命名法

## 配置文件规范

- 基础配置放在 `application.yml`
- 环境配置放在 `application-dev.yml`、`application-test.yml`、`application-prod.yml`
- MyBatis mapper 路径：`classpath*:/mapper/postgres/*.xml` 或 `classpath*:/mapper/mysql/*.xml`
- 实体扫描路径：`com.finn.*.entity`

## 常用注解说明

| 注解 | 用途 | 位置 |
|------|------|------|
| `@TableName` | 指定数据库表名 | Entity 类 |
| `@TableField` | 指定字段映射关系 | Entity 字段 |
| `@TableId` | 指定主键字段 | Entity 主键字段 |
| `@Pages` | 标记分页方法 | Mapper 方法 |
| `@Log` | 记录操作日志 | Controller 方法 |
| `@PreAuthorize` | 权限控制 | Controller 方法 |
| `@Idempotent` | 接口幂等控制 | Controller 方法 |
| `@Ds` | 动态数据源切换 | Mapper 方法或 Service 方法 |
