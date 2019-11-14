package com.xincan.transaction.oauth.config.security;

import com.xincan.transaction.oauth.server.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
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

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder());
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        System.out.println("--------web security------------------------------------------------------");
//
//        http.requestMatchers()
//                .anyRequest()
//                .and().authorizeRequests()
//                //.antMatchers("/admin/**").authenticated()
//                .antMatchers("/oauth/**").permitAll()
////                .antMatchers("/admin/**").hasRole("ADMIN")///admin/**的URL都需要有超级管理员角色，如果使用.hasAuthority()方法来配置，需要在参数中加上ROLE_,如下.hasAuthority("ROLE_超级管理员")
//                .anyRequest().authenticated()//其他的路径都是登录后即可访问
//                .and().csrf().disable();
//
//    }

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
