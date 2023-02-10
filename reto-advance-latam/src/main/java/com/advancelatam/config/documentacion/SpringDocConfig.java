package com.advancelatam.config.documentacion;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {
    private static final String GRUPO = "controladores";
    private static final String[] PATHS = {
            "/api/**"
    };

    @Bean
    public GroupedOpenApi getDocket() {
        return GroupedOpenApi
                .builder()
                .packagesToScan("com.advancelatam.web.controllers")
                .group(GRUPO)
                .pathsToMatch(PATHS)
                .build();
    }
}
