## 说明
oh-framework是系统框架，包括鉴权拦截、数据库、多数据源、异常以及基础类等。
- 数据源切换使用 @Ds 注解进行切换，可作用到类和方法上
- 使用PageHelper进行分页，也可以使用@Page注解进行分页操作
- 异步消息消费；实现JobService接口可异步消费消息，通过JobServiceConsumer.jobConsume 执行业务代码
- 在方法上使用 @OperateLog 注解可记录操作日志
## 引入
根据实际版本引入，如下所示：

```xml

<dependency>
    <groupId>com.iris</groupId>
    <artifactId>oh-framework</artifactId>
    <version>${版本号}</version>
</dependency>
```
