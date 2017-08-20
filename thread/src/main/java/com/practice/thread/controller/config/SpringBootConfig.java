package com.practice.thread.controller.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by fgm on 2017/7/23.
 */
@EnableSwagger2
@Configuration
@ImportResource("classpath:applicationContext.xml")
@ComponentScan(value = {"com.practice.thread","com.practice.hack.changelog"})
public class SpringBootConfig  extends WebMvcConfigurerAdapter{

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("/index");
        registry.addViewController("/hello").setViewName("/index");
        registry.addViewController("/index").setViewName("/index");
        registry.addViewController("/sse").setViewName("/sse");
    }

    ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("API")
                .description("spring-boot线程测试API")
                .licenseUrl("https://github.com/fgm0129")
                .version("1.0.0")
                .build();
    }

}
