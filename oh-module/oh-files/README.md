- 1、文件服务支持本地存储、SeaweedFS 存储，通过 `finn.storage.type` 切换，无需修改代码
- 2、使用 SeaweedFS 存储文件时，需要安装相关服务，使用 S3 协议
- 3、可独立部署，也可以集成在系统中一起使用
- 4、如果 SeaweedFS 服务异常，不影响整个系统的使用；当 `finn.storage.type != seaweedfs` 时，S3 相关 Bean 不会加载，彻底避免依赖问题
- 5、本地存储通过 `FileController` 的 `/file/upload`、`/file/download`、`/file/preview` 完成上传、下载、预览
- 6、`cache-record` 开启后，上传的文件信息会写入 Redis，由 `oh-system` 的 `AttachmentService` 异步保存到 `sys_attachment` 表

```yaml
finn:
  # 文件存储相关，通过配置，可指定存储类型，默认本地存储
  storage:
    enabled: true # 控制文件存储是否启用
    type: local  # 存储类型：local、seaweedfs、minio（当前仅支持 local、seaweedfs）
    cache-record: true                      # 是否redis缓存文件信息，用于异步保存到表中，默认false
    # 本地存储配置
    local:
      path: D:\data\upFiles
      url: upload                           # 本地资源前缀，默认 upload（下载/预览通过 FileController 处理，该前缀仅用于文件 key 兼容）
    # SeaweedFS 配置
    seaweedfs:
      endpoint: http://localhost:8333         # SeaweedFS S3 网关地址
      access-key: admin                       # SeaweedFS 默认不校验，如果 SeaweedFS S3 网关未配置认证，可任意填
      secret-key: admin123                    # 同上
      bucket: default-bucket                  # 默认存储桶名
      region: us-east-1                       # SeaweedFS 不校验 region，任意填
      path-style-access: true                 # 必须使用 path-style,必须开启，SeaweedFS 不支持 virtual-hosted style
      # 允许上传的文件扩展名，如 .jpg
      file-suffix:
        - .jpg
        - .png
        - .jpeg
        - .zip
```
