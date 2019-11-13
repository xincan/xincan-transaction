package com.xincan.transaction.order;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import org.apache.shardingsphere.shardingjdbc.spring.boot.SpringBootConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 使用自定义的多个datasource,避免springboot自动将druidDataSource作为默认的datasource
 */
@EnableTransactionManagement
@SpringBootApplication(exclude = {
        SpringBootConfiguration.class,
        DataSourceAutoConfiguration.class})
public class XincanTransactionOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(XincanTransactionOrderApplication.class, args);
    }

}
