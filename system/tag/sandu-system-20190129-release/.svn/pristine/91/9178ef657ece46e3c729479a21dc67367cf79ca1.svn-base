package com.sandu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * CopyRight (c) 2018 Sandu Technology Inc.
 * <p>
 * sandu-project
 *
 * @author sandu (yocome@gmail.com)
 * @datetime 2018/4/26 15:37
 */
@Configuration
@Profile(value = {"local", "dev", "test", "ci"})
@EnableSwagger2
public class SwaggerConfig {

    /**
     * 设置SwaggerUI
     *
     * @return Docket Bean
     */
    @Bean
    public Docket createRestApi() {

        //添加支持Token认证
        ParameterBuilder parameter = new ParameterBuilder();
        List<Parameter> parameters = new ArrayList<>();
        parameter.name(HttpHeaders.AUTHORIZATION).description("授权token")
                .modelRef(new
                        ModelRef("string")).parameterType("header").required(false).build();
        parameters.add(parameter.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(parameters);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("三度空间 Demo RESTFull API")
                .description("更多内容，请访问: http://www.sanduspace.cn")
                .termsOfServiceUrl("http://www.sanduspace.cn")
                .version("1.0")
                .build();
    }
}
