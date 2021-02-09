package org.idsp.smartengine;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackages = "com.alibaba.smart.framework.engine.persister.database")
//@ComponentScan(basePackages = {
//		"com.alibaba.smart.framework.engine.persister"
//})
public class SmartengineApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartengineApplication.class, args);
	}

}
