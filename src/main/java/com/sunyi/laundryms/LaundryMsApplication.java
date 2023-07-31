package com.sunyi.laundryms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.sunyi.laundryms.domain.mapper")
@SpringBootApplication
public class LaundryMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(LaundryMsApplication.class, args);
    }

}
