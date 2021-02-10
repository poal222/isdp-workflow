package org.idsp.smartengine;

import org.hswebframework.web.crud.annotation.EnableEasyormRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@MapperScan(basePackages = "com.alibaba.smart.framework.engine.persister.database")
@EnableEasyormRepository("org.idsp.smartengine.entity")
public class SmartengineApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartengineApplication.class, args);
	}

}
