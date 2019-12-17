package com.xincan.transaction.oauth.config.security;

import com.xincan.transaction.oauth.server.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Copyright (C), 2018,北京同创永益科技发展有限公司
 * @Package:  com.xincan.transaction.oauth.config.oauth
 * @ClassName: WebSecurityConfig
 * @Author: Xincan
 * @Description: ${description}
 * @Date: 2019/4/18 16:50
 * @Version: 1.0
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 注入UserDetailsService实现对象
     */
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    /**
     * 密码编码解码器
     * @return
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 鉴权管理bean
     * @return
     * @throws Exception
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 使用自定义的UserDetailsService实现和密码
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder());
    }

    /**
     * url拦截配置
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //不拦截 oauth 开放的资源
        http.csrf().disable();
        //使HttpSecurity接收以"/login/","/oauth/"开头请求, 配置HttpSecurity不阻止swagger页面
        http.requestMatchers()
                .antMatchers("/oauth/**", "/login/**", "/logout/**",
                        "/webjars/**", "/swagger-ui.html/**", "/swagger-resources/**", "/v2/api-docs/**")
                .and()
                .authorizeRequests()
                .antMatchers("/oauth/**").authenticated()
                .and().httpBasic();
    }


}
