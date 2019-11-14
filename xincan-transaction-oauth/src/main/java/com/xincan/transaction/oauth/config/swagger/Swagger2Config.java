package com.xincan.transaction.oauth.config.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.*;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spi.service.contexts.SecurityContextBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;

/**
 * Copyright (C), 2018, 北京同创永益科技发展有限公司
 * FileName: ResultCode
 * Author:   JiangXincan
 * Date:     2018-12-19 16:30:00
 * @Description: xincan-swagger配置
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Value("http://127.0.0.1:${server.port}/oauth")
    private String AUTH_SERVER;

    @Value("${swagger.oauth.clientId}")
    private String CLIENT_ID;

    @Value("${swagger.oauth.clientSecret}")
    private String CLIENT_SECRET;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.xincan.transaction.oauth.server"))
                .paths(PathSelectors.any())
                .build()
                // 配置 swagger oauth认证
                .securitySchemes(Arrays.asList(securityScheme()))
                .securityContexts(Arrays.asList(securityContext()));
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("xincan","http://localhost:8000/doc.html","alittlexincan@163.com");
        return new ApiInfoBuilder()
                .title("xincan-swagger-ui RESTful APIs")
                .description("xincan-swagger-ui")
                .termsOfServiceUrl("http://localhost:8000/")
                .contact(contact)
                .version("1.0")
                .build();
    }

    /**
     * 配置swagger的oauth2 authorization_code认证, 需要在数据库表中创建相对应的数据
     *
     * sql语句1:
     *
     * INSERT INTO `oauth_client_details`(`client_id`, `resource_ids`, `client_secret`, `scope`,
     * `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`,
     * `refresh_token_validity`, `additional_information`, `autoapprove`)
     * VALUES (
     * -- 该字段为 CLIENT_ID 自定义的所对应值
     * 'swagger',
     * -- 该字段为配置文件中 spring.application.name 所对应值(可为NULL)
     * 'xincan-transaction-oauth',
     * -- 该字段为经过 (new BCryptPasswordEncoder()).encode 方法加密后的 CLIENT_SECRET 值
     * '$2a$10$aX7r1RP2LQzhFZgmXvyHUuN34t9q43YZcmLZIdi7t6fAlpDQJS.SS',
     * -- 该字段为scope值
     * 'all',
     * -- 该字段中需要包含 password
     * 'client_credentials,refresh_token,password,authorization_code',
     * -- 该字段为swagger跳转默认值
     * 'http://127.0.0.1:8070/webjars/springfox-swagger-ui/oauth2-redirect.html',
     * -- 该字段为角色类型
     * 'ROLE_ADMIN',
     * 6000, 6000, NULL, NULL);
     *
     * sql语句2:
     *
     * INSERT INTO `xincan-oauth`.`user`(`id`, `password`, `username`)
     * VALUES
     * (
     * -- 该字段为id
     * 220599983231991808,
     * -- 该字段为经过 (new BCryptPasswordEncoder()).encode 方法加密后的 CLIENT_SECRET 值,需要与sql语句1中对应字段相同
     * '$2a$10$aX7r1RP2LQzhFZgmXvyHUuN34t9q43YZcmLZIdi7t6fAlpDQJS.SS',
     * -- 该字段为 CLIENT_ID 自定义的所对应值
     * 'swagger');
     *
     */

    @Bean
    public SecurityConfiguration security() {
        return SecurityConfigurationBuilder.builder()
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
                .build();
    }

    private AuthorizationScope[] scopes() {
        AuthorizationScope[] scopes = {
                new AuthorizationScope("server", "仅设置了名为server的scope")
        };
        return scopes;
    }

    private SecurityScheme securityScheme() {
        // 配置 swagger oauth password认证
        List<AuthorizationScope> authorizationScopeList = new ArrayList<>(Arrays.asList(scopes()));
        List<GrantType> grantTypes = new ArrayList<>();
        GrantType passwordCredentialsGrant = new ResourceOwnerPasswordCredentialsGrant(AUTH_SERVER+"/token");
        grantTypes.add(passwordCredentialsGrant);

        return new OAuth("spring_oauth", authorizationScopeList, grantTypes);
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(
                        Arrays.asList(new SecurityReference("spring_oauth", scopes()))
                )
                .forPaths(PathSelectors.any())
                .build();
    }


}
