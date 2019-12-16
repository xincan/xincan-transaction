package com.xincan.transaction.oauth.config.swagger;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import com.google.common.collect.Lists;
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
@EnableSwaggerBootstrapUI
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.xincan.transaction.oauth.server"))
                .paths(PathSelectors.any())
                .build()
                // 配置 swagger oauth认证
                .securityContexts(Lists.newArrayList(securityContext()))
                .securitySchemes(Lists.<SecurityScheme>newArrayList(apiKey()))
                ;
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
     * 配置token类型为Bearer
     * @return
     */
    private ApiKey apiKey() {
        return new ApiKey("BearerToken", "Authorization", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("/.*"))
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(new SecurityReference("BearerToken", authorizationScopes));
    }


}
