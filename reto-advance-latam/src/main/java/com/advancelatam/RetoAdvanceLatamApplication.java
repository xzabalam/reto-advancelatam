package com.advancelatam;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@OpenAPIDefinition(info = @Info(title = "API para el control de autos y la configuración de pico y placa", version =
        "1.0",
        description = "Operaciones relacionadas con pico y placa."))
public class RetoAdvanceLatamApplication {

    public static void main(String[] args) {
        SpringApplication.run(RetoAdvanceLatamApplication.class, args);
    }

}
