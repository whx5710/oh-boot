# 生产部署说明

## 目录结构

```
deploy/
├── Dockerfile              # 构建 oh-boot 镜像
├── docker-compose.yml      # 生产环境容器编排
├── nginx.conf              # nginx 反向代理与 SSL 配置
├── s3-config.json          # SeaweedFS S3 配置
└── .env.example            # 环境变量示例
```

## 前置条件

1. 服务器已安装 Docker 和 Docker Compose
2. 已备案域名并解析到服务器公网 IP（国内服务器必须备案）
3. 如果是微信小程序，配置「服务器域名」白名单

## 部署步骤

### 1. 准备环境变量

```bash
cd deploy
cp .env.example .env
# 编辑 .env，修改密码、数据库名、机器码等敏感信息
```

### 2. 准备 SSL 证书

将证书文件放到 `deploy/ssl/` 目录，文件命名为：

- `cert.pem`：证书文件（合并中间证书）
- `key.pem`：私钥文件

如果使用 Let's Encrypt，默认路径通常是：

- `/etc/letsencrypt/live/api.yourdomain.com/fullchain.pem`
- `/etc/letsencrypt/live/api.yourdomain.com/privkey.pem`

复制到 `deploy/ssl/` 后重命名为 `cert.pem` 和 `key.pem`。

### 3. 修改 nginx.conf 中的域名

将 `nginx.conf` 中的 `api.yourdomain.com` 替换为你的真实域名。

### 4. 构建后端镜像

```bash
cd ..
mvn clean package -DskipTests
# 假设打包产物为 oh-system/target/oh-server.jar
cp oh-system/target/oh-server.jar deploy/oh-server.jar

cd deploy
docker build -t oh-boot:1.0.0 .
```

### 5. 启动服务

```bash
cd deploy
docker compose up -d
```

### 6. 验证

- 访问 `https://api.yourdomain.com:8443/nginx-health` 检查 nginx
- 访问 `https://api.yourdomain.com:8443/sys/openUser/update` 测试后端接口

> 注意：当前使用非标准端口 `8443`（HTTPS）和 `8081`（HTTP 跳转）。微信小程序后台配置服务器域名时，需要填写完整地址，如 `https://api.yourdomain.com:8443`。

## 关于端口

当前配置使用非标准端口：

- HTTPS：`8443`
- HTTP（仅用于跳转）：`8081`

如果后续域名备案完成、服务器放行 80/443，可以将 `nginx.conf` 和 `docker-compose.yml` 中的端口改回标准端口：

```yaml
# docker-compose.yml
ports:
  - "8081:8081"
  - "8443:8443"
```

```nginx
# nginx.conf
server {
    listen 8081;
    return 301 https://$host$request_uri;
}

server {
    listen 443 ssl http2;
    # ...
}
```

## 安全说明

- Redis 和 PostgreSQL **未暴露公网端口**，仅 oh-server 可通过 Docker 内网访问
- oh-server 的 5710 端口仅绑定到 `127.0.0.1`，避免被外网直接访问
- SeaweedFS 各端口也仅绑定到 `127.0.0.1`，如需公网访问请配置防火墙白名单
- 生产环境务必修改默认密码，并通过 `.env` 管理

## 常见问题

### 证书续期

Let's Encrypt 证书有效期为 90 天，建议配置 certbot 自动续期，或在宿主机用 cron 定期复制新证书到 `deploy/ssl/` 后执行：

```bash
cd deploy && docker compose restart nginx
```

### 日志查看

```bash
# 所有服务日志
docker compose logs -f

# 单个服务日志
docker compose logs -f oh-server
docker compose logs -f nginx
```
