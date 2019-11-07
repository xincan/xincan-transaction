package com.xincan.transaction.config;

import feign.Request;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: TODO
 * @className: OpenFeignConfig
 * @date: 2019/11/7 10:43
 * @author: Xincan Jiang
 * @version: 1.0
 */
@Configuration
public class OpenFeignConfig {

//    public static int connectTimeOutMillis = 12000;//超时时间
//
//    public static int readTimeOutMillis = 12000;
//
//    @Bean
//    public Request.Options options() {
//        return new Request.Options(connectTimeOutMillis, readTimeOutMillis);
//    }
//
//
//    //自定义重试次数
//    @Bean
//    public Retryer feignRetryer(){
//        return new Retryer.Default(100, 1000, 3);
//    }

}
