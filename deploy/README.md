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

### 2. 当前为 HTTP 模式（无 SSL）

当前 `nginx.conf` 默认使用 **HTTP 模式**，不强制要求 SSL 证书，方便先跑起来。

访问地址为：

```
http://www.cwalker.top:8443/api/sys/openUser/update
```

> ⚠️ 微信小程序正式上线必须使用 HTTPS，HTTP 模式仅用于本地/内网测试或临时调试。

### 3. 切换到 HTTPS（上线前必须做）

当你有了正式 SSL 证书后：

1. 将证书文件放到 `deploy/ssl/` 目录，命名为 `cert.pem` 和 `key.pem`
2. 编辑 `nginx.conf`：
   - 注释掉当前 `listen 8443;` 的 HTTP server 块
   - 取消注释下方的 HTTPS server 块
3. 编辑 `docker-compose.yml` 中的 nginx 端口（可选）：
   - 如需保留 HTTP 跳转入口，增加 `"8081:8081"`
4. 重启 nginx：`docker compose restart nginx`

#### SSL 证书获取方式

**临时自签名证书（仅本地测试）：**

```bash
cd deploy
mkdir -p ssl
openssl req -x509 -nodes -days 365 -newkey rsa:2048 \
  -keyout ssl/key.pem \
  -out ssl/cert.pem \
  -subj "/C=CN/ST=Beijing/L=Beijing/O=Test/CN=www.cwalker.top"
```

**正式证书（推荐）：**

- 腾讯云/阿里云免费证书
- Let's Encrypt（可用 certbot 自动续期）

### 4. 修改 nginx.conf 中的域名

当前已配置为 `www.cwalker.top`，如需更换域名，将 `nginx.conf` 中的 `www.cwalker.top` 替换为你的真实域名。

### 5. 构建后端镜像

```bash
cd ..
mvn clean package -DskipTests
# 假设打包产物为 oh-system/target/oh-server.jar
cp oh-system/target/oh-server.jar deploy/oh-server.jar

cd deploy
docker build -t oh-boot:1.0.0 .
```

### 6. 启动服务

```bash
cd deploy
docker compose up -d
```

### 7. 验证

当前为 HTTP 模式，验证地址：

- `http://www.cwalker.top:8443/nginx-health`
- `http://www.cwalker.top:8443/api/sys/openUser/update`

切换到 HTTPS 后，再把 `http://` 改成 `https://`。

> 注意：当前使用非标准端口 `8443`。微信小程序正式上线必须使用 HTTPS 和备案域名。

## 关于端口

当前配置使用非标准端口 `8443`（HTTP 模式）。

如果后续域名备案完成、服务器放行 80/443，可以改回标准端口，并将 `nginx.conf` 切换为 HTTPS 配置：

```yaml
# docker-compose.yml
ports:
  - "80:80"
  - "443:443"
```

```nginx
# nginx.conf
server {
    listen 80;
    return 301 https://$host$request_uri;
}

server {
    listen 443 ssl;
    http2 on;
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
