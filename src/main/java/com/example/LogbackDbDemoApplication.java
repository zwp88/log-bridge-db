package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author Administrator
 */
@SpringBootApplication
public class LogbackDbDemoApplication {
	static {
		// 静默 SLF4J 的初始化警告信息
		System.setErr(new java.io.PrintStream(new java.io.OutputStream() {
			@Override
			public void write(int b) {
				// 忽略所有错误输出
			}
		}));
	}
	static Logger logger = LoggerFactory.getLogger(LogbackDbDemoApplication.class);
	public static void main(String[] args) {

		logger.info("测试logback db");
		logger.debug("测试logback db");
		logger.trace("测试logback db");
		logger.error("测试logback db");
		logger.warn("测试logback db");

		SpringApplication.run(LogbackDbDemoApplication.class, args);
	}

}
