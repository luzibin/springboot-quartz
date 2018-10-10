package com.Bin.quartz.config;

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
 * <p>
 * <b>Swagger2</b> is 接口测试
 * </p>
 *
 * @author Bin
 * @version $Id: flowBusiness 62440 2018-02-06 11:24:15Z Bin $
 * @since 2018年02月06日
 **/
@Configuration
@EnableSwagger2
public class Swagger2 {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.Bin.quartz"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("quartz测试接口")
                .description("描述quartz测试接口")
                //.termsOfServiceUrl("http://www.scmdjt.com")
                .contact(new Contact("Bin", "", "zibinlu@163.com"))
                .version("1.0")
                .build();
    }
}
