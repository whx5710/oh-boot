/**
 * MIT License

	Copyright (c) 2023 王小费 whx5710@qq.com

	Permission is hereby granted, free of charge, to any person obtaining a copy
	of this software and associated documentation files (the "Software"), to deal
	in the Software without restriction, including without limitation the rights
	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
	copies of the Software, and to permit persons to whom the Software is
	furnished to do so, subject to the following conditions:

	The above copyright notice and this permission notice shall be included in all
	copies or substantial portions of the Software.

	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
	SOFTWARE.
 */
package com.iris;

import cn.hutool.core.util.URLUtil;
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
 * 可设置到其他模块中，注意引入模块
 */
@SpringBootApplication
public class ServerApplication extends SpringBootServletInitializer implements ApplicationRunner {
	private final Logger log = LoggerFactory.getLogger(ServerApplication.class);

	private final ServerProperties serverProperties;

	public ServerApplication(ServerProperties serverProperties){
		this.serverProperties = serverProperties;
	}

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
		log.info("-----------------------------------------------");
		log.info("后台服务启动成功.");
		log.info("API 地址：{}", baseUrl);
		log.info("-----------------------------------------------");
	}
}