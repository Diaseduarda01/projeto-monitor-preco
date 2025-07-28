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

	// TODO: DASHBOARD INDIVIDUAL POR PRODUTO
	// KPIs:
		// GET /dashboard/produto/{produtoId}/preco-desejado
		// → Retornar preço desejado do produto

		// GET /dashboard/produto/{produtoId}/preco-atual
		// → Retornar o último preço coletado

		// GET /dashboard/produto/{produtoId}/variacao
		// → Retornar variação percentual entre primeiro e último preço monitorado

		// GET /dashboard/produto/{produtoId}/tempo
		// → Dias entre primeira e última coleta (tempo monitorado)

	// TODO: RELATÓRIOS
		// POST /usuarios/{usuarioId}/relatorios?tipo=semanal ou mensal
		// → Gerar relatório PDF ou similar (histórico e resumo)

		// GET /usuarios/{usuarioId}/relatorios
		// → Listar relatórios já gerados pelo usuário

		// GET /relatorios/{relatorioId}/download
		// → Baixar o relatório específico (PDF, CSV, etc)


	// TODO: MELHORIAS FUTURAS (IDEIAS)
		// Filtro de datas na dashboard individual
		// Suporte para múltiplos usuários (admin vê todos, comum vê os próprios)

}
