package vn.com.tdtu.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI(@Value("${springdoc.version:1.0}") String appVersion) {
        return new OpenAPI()
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        "BearerToken",
                                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("Bearer")
                                )
                                .addSecuritySchemes(
                                        "BasicToken",
                                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("Basic")
                                )
                )
                .info(
                        new Info()
                                .title("Project 1 apis")
                                .version(appVersion)
                                .license(
                                        new License().name("Apache 2.0").url("http://springdoc.org")
                                )
                );
    }

}
