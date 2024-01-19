package com.solinfbroker.apiautenticacao;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
		title = "API de Autenticação - SolinfBroker",
		version = "1.2",
		description = "Api responsável por criar e autenticar os usuários"))
public class ApiAutenticacaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiAutenticacaoApplication.class, args);
	}

}
