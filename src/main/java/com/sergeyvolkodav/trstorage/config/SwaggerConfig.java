package com.sergeyvolkodav.trstorage.config;

import com.sergeyvolkodav.trstorage.rest.TransactionalController;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@ComponentScan(basePackageClasses = {TransactionalController.class})
public class SwaggerConfig {

}
