# 构建镜像，执行命令：【docker build -t oh-boot:1.0.0 .】
# 启动容器，执行命令： docker run --name oh-boot -d -p 5710:5710 oh-boot:1.0.0

# 使用 Eclipse Temurin JDK 21（官方推荐，Alpine 版更轻量）
FROM eclipse-temurin:21-jre-alpine

LABEL maintainer="Finn"

# 定义环境变量（带默认值，运行时可覆盖）
ENV LICENSE_MACHINE_CODE=default-machine-code
ENV JAVA_OPTS=""

# 将 jar 文件添加到容器中
ADD oh-server.jar /app/oh-server.jar

# 设置时区
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \
    echo 'Asia/Shanghai' >/etc/timezone

# 暴露 Spring Boot 端口
EXPOSE 5710

# ENTRYPOINT ["java", "-server", "-Xms1024M", "-Xmx1024M", "-Djava.security.egd=file:/dev/./urandom", "-Dfile.encoding=UTF-8", "-XX:+HeapDumpOnOutOfMemoryError", "-jar", "/app/oh-server.jar" ]

# 使用 sh -c 支持环境变量展开，exec 确保 java 替换为 PID 1
ENTRYPOINT ["sh", "-c", "exec java -server -Xms1024M -Xmx1024M -Djava.security.egd=file:/dev/./urandom -Dfile.encoding=UTF-8 -XX:+HeapDumpOnOutOfMemoryError -Dlicense.machine.code=${LICENSE_MACHINE_CODE} ${JAVA_OPTS} -jar /app/oh-server.jar"]