package com.xincan.transaction;

import org.apache.shardingsphere.shardingjdbc.spring.boot.SpringBootConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        SpringBootConfiguration.class
})
public class XincanTransactionRbacApplication {

    public static void main(String[] args) {
        SpringApplication.run(XincanTransactionRbacApplication.class, args);
    }

}
