/**
 * MIT License

	Copyright (c) 2024 王小费 whx5710@qq.com

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
package com.finn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.web.server.autoconfigure.ServerProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

/**
 * 应用初始化器
 * 负责应用启动后的初始化操作
 */
@Component
public class ApplicationInitializer implements ApplicationRunner {
    private final Logger log = LoggerFactory.getLogger(ApplicationInitializer.class);
    private final ServerProperties serverProperties;
    
    public ApplicationInitializer(ServerProperties serverProperties){
        this.serverProperties = serverProperties;
    }
    
    @Override
    public void run(ApplicationArguments args) throws Exception {
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        Integer port = serverProperties.getPort();
        String contextPath = serverProperties.getServlet().getContextPath();
        String baseUrl = String.format("%s://%s:%s%s", "http", hostAddress, port, contextPath);
        log.info("-----------------------------------------------");
        log.info("后台服务启动成功.");
        log.info("API 地址：{}", baseUrl);
        log.info("-----------------------------------------------");
    }
    
    public static void initialize(ConfigurableApplicationContext applicationContext) {
        ApplicationInitializer initializer = applicationContext.getBean(ApplicationInitializer.class);
        try {
            initializer.run(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}