package com.iris;

import cn.hutool.core.util.URLUtil;
import com.iris.framework.common.config.properties.ProjectProperties;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.net.InetAddress;

/**
 * 项目启动入口
 * 王小费
 */
@SpringBootApplication
public class ServerApplication extends SpringBootServletInitializer implements ApplicationRunner {
	private final Logger log = LoggerFactory.getLogger(ServerApplication.class);

	@Resource
	private ServerProperties serverProperties;

	@Resource
	private ProjectProperties projectProperties;

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ServerApplication.class);
	}

	/**
	 * 启动后执行
	 * @param args
	 * @throws Exception
	 */
	@Override
	public void run(ApplicationArguments args) throws Exception {
		String hostAddress = InetAddress.getLocalHost().getHostAddress();
		Integer port = serverProperties.getPort();
		String contextPath = serverProperties.getServlet().getContextPath();
		String baseUrl = URLUtil.normalize(String.format("%s:%s%s", hostAddress, port, contextPath));
		log.info("----------------------------------------------");
		log.info("{} 后台服务启动成功.", projectProperties.getName());
		log.info("API 地址：{}", baseUrl);
		log.info("----------------------------------------------");

	}
}