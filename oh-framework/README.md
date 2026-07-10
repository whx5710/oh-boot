## 说明
oh-framework是系统框架，已从项目中剥离独立到`finn-parent`工程中，如果要修改，请到`finn-parent`项目下下修改，或将代码拷贝到该目录下
## 结构说明
- common     公共相关的
- datasource 数据库相关
- entity     实体类
- exception  异常处理
- operatelog 操作日志
- query      查询实体类
- security   鉴权相关
- service    服务集合
- utils      工具集合

## 引入
根据实际版本引入，如下所示：

```xml
<dependency>
    <groupId>com.finn</groupId>
    <artifactId>oh-framework</artifactId>
    <version>${版本号}</version>
</dependency>
```
