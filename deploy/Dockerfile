# 构建镜像，执行命令：【docker build -t oh-iris:1.0.0 .】
# 启动容器，执行命令： docker run --name oh-iris -d -p 8080:8080 oh-iris:1.0.0

FROM eclipse-temurin:17-jre
MAINTAINER 王小费
# 将jar文件添加到容器中
ADD oh-server.jar /app/oh-server.jar
# 设置时区
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo 'Asia/Shanghai' >/etc/timezone

ENTRYPOINT ["java", "-server", "-Xms1024M", "-Xmx1024M", "-Djava.security.egd=file:/dev/./urandom", "-Dfile.encoding=UTF-8", "-XX:+HeapDumpOnOutOfMemoryError", "-jar", "/app/oh-server.jar" ]


