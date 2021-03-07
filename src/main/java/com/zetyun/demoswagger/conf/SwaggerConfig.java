package com.zetyun.demoswagger.conf;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

import static com.google.common.collect.Lists.newArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
//                .apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.zetyun.demoswagger.controller"))
                .paths(Predicates.or(
                        PathSelectors.ant("/user/add"),
                        PathSelectors.ant("/user/find/*"))
                )
                .build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, newArrayList(
                        new ResponseMessageBuilder()
                                .code(500)
                                .message("服务器发生异常")
                                .responseModel(new ModelRef("Error"))
                                .build(),
                        new ResponseMessageBuilder()
                                .code(403)
                                .message("资源不可用")
                                .build()
                ));
    }

    private ApiInfo apiInfo(){
        return new ApiInfo(
                "兴业准实时数据集成项目文档",
                "九章云极官网：https://www.datacanvas.com/，欢迎大家访问！",
                "API 1.0",
                "Terms of service",
                new Contact("rt", "https://www.datacanvas.com/", "rt@zetyun.com"),
                "Flink",
                "http://flink.apache.org/",
                Collections.emptyList()
        );
    }
}
