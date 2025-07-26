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
	//TODO:
	//Histórico de preços:
	//Endpoint para listar os históricos de preço de um produto (ex: GET /produtos/{id}/historico)

	//Relatórios (relatorio):
	//Endpoint para gerar relatório (POST /usuarios/{id}/relatorios?tipo=semanal ou mensal)
	//Endpoint para listar relatórios disponíveis
	//Endpoint para download do relatório (GET /relatorios/{id}/download)

	//Processo para pegar preço:
	//Busque os produtos ativos (query no banco produto onde ativo = true)
	//Para cada produto, acesse a URL e pegue o preço atual
	//Salve o preço coletado na tabela historico_preco
	//Verifique se o preço coletado é menor ou igual ao preco_desejado
	//Caso sim, crie uma notificação e envie um email

	//Agendamento periódico:
	//@Scheduled para executar uma tarefa periodicamente (ex: a cada 1 hora, 1 dia, 1 Semana)
}
