package com.xincan.transaction;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@MapperScan("com.xincan.transaction.server.mapper.**.*")
public class XincanTransactionUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(XincanTransactionUserApplication.class, args);
    }

}
