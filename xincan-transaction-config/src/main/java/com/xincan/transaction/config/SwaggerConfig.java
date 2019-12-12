package com.xincan.transaction.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @description: TODO
 * @className: SwaggerConfig
 * @date: 2019/10/31 14:58
 * @author: Xincan Jiang
 * @version: 1.0
 */
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.xincan.transaction.server"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("xincan","http://localhost:8100/doc.html","alittlexincan@163.com");
        return new ApiInfoBuilder()
                .title("xincan-swagger-ui RESTful APIs")
                .description("xincan-swagger-ui")
                .termsOfServiceUrl("http://localhost:8100/")
                .contact(contact)
                .version("1.0")
                .build();
    }
}
