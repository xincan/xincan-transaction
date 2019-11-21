package com.xincan.transaction.order;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import org.apache.shardingsphere.shardingjdbc.spring.boot.SpringBootConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 使用sharding-jdbc的datasource,避免springboot自动将druidDataSource作为默认的datasource
 */
@EnableOAuth2Client
@EnableTransactionManagement
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        SpringBootConfiguration.class
})
public class XincanTransactionOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(XincanTransactionOrderApplication.class, args);
    }

}
