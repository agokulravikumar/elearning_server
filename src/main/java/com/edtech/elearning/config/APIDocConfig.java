package com.edtech.elearning.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@SecurityScheme(name = "elearning_auth", scheme = "basic", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class APIDocConfig {

    @Value("${spring.application.version}")
    private String version;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .servers(servers())
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("E-Learning API")
                .description("RESTFul API documentation for E-Learning Platform")
                .version(version);
    }

    private List<Server> servers() {
        var localServer = new Server();
        localServer.setUrl("http://localhost:6161/api");
        localServer.setDescription("Local Server");

        List<Server> servers = new ArrayList<>();
        servers.add(localServer);

        return servers;
    }

}
