package com.xincan.transaction.oauth.config.oauth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * @Copyright (C), 2018,北京同创永益科技发展有限公司
 * @Package:  com.xincan.transaction.oauth.config.oauth
 * @ClassName: ResourceServerConfig
 * @Author: Xincan
 * @Description: ${description}
 * @Date: 2019/5/15 17:06
 * @Version: 1.0
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Value("${spring.application.name}")
    private String RESOURCE_ID;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        System.out.println("--------ResourceServerConfig------------------------------------------------------");
//        super.configure(http);
        // 过滤不需要认证的资源
                http
                .authorizeRequests()
                .antMatchers("/user/login").permitAll();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(RESOURCE_ID).stateless(true);
    }


}
