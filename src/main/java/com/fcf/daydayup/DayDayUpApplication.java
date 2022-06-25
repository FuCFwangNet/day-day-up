package com.fcf.daydayup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan("com.fcf.daydayup.service.dao")
@SpringBootApplication
public class DayDayUpApplication {

	public static void main(String[] args) {
		SpringApplication.run(DayDayUpApplication.class, args);
	}

}
