package com.lkc.www.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class knife4jConfig {
    @Bean
    public GroupedOpenApi adminApi(){
        return GroupedOpenApi.builder()
                .group("admin-api")//分组名称
                .pathsToMatch("/admin/**")//接口请求规则
                .build();
    }

    //自定义接口信息
    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("尚品甄选API接口文档")
                        .version("1.0")
                        .description("尚品甄选API接口文档")
                        .contact(new Contact().name("liukaicun")));//作者
    }
}
