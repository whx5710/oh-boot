## 说明
oh-boot 是采用组件模式，扩展不同的业务功能，可以很方便的实现各种业务需求，且不会导致系统臃肿，若想使用某个组件，按需引入即可，反之亦然。

`oh-module-api` 对外开放的接口

`oh-module-flow`   工作流

`oh-module-system` 系统管理【基础功能】

`oh-module-team`   协同工作


## 引入
如果需要使用对应的组件，如：`oh-module-flow`，则需要在`oh-boot/oh-server/pom.xml`里面引入，如下所示：

```xml

<dependency>
    <groupId>com.iris</groupId>
    <artifactId>oh-module-flow</artifactId>
    <version>${revision}</version>
</dependency>
```

## SQL语句
引入组件时，还需要执行对应的SQL文件，初始化表结构和菜单等。