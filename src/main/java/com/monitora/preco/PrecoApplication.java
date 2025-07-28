package com.monitora.preco;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PrecoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrecoApplication.class, args);
	}

	//Agendamento periódico:
	//@Scheduled para executar uma tarefa periodicamente (ex: a cada 1 hora, 1 dia, 1 Semana)

	// TODO: MELHORIAS FUTURAS (IDEIAS)
		// Filtro de datas na dashboard individual
		// Suporte para múltiplos usuários (admin vê todos, comum vê os próprios)

}
