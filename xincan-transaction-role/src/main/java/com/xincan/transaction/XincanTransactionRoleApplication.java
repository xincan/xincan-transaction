package com.xincan.transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class
})
public class XincanTransactionRoleApplication {

    public static void main(String[] args) {
        SpringApplication.run(XincanTransactionRoleApplication.class, args);
    }

}
