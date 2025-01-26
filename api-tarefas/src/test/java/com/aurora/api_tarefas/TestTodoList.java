package com.aurora.api_tarefas;


import java.time.LocalDateTime;

import org.glassfish.jaxb.core.v2.TODO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.aurora.api_tarefas.Entity.Todo;
import com.aurora.api_tarefas.Entity.Categoria;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql("/remove.sql")
class TestTodoList{
	@Autowired
	private WebTestClient webTestClient;

	@Test
	void testCriarTodoSucesso(){
		var todo = new Todo(
			null,
			"Tarefa de Teste I",
			"Descrição de Teste da Tarefa",
			false,
			true,
			LocalDateTime.now().plusDays(1),
			Categoria.ESTUDOS,
			null
		);

		webTestClient
            .post()
            .uri("/todos")
            .bodyValue(todo)
            .exchange()
            .expectStatus().isCreated()
            .expectBody()
            .jsonPath("$.id").isNotEmpty() //O Id da tarefa é autoincremento no Banco
            .jsonPath("$.nome").isEqualTo(todo.getNome())
            .jsonPath("$.descricao").isEqualTo(todo.getDescricao())
            .jsonPath("$.realizado").isEqualTo(todo.isRealizado())
            .jsonPath("$.prioridade").isEqualTo(todo.isPrioridade())
            .jsonPath("$.categoria").isEqualTo(todo.getCategoria().name())
            .jsonPath("$.prazo").isEqualTo(todo.getPrazo().toString())
            .jsonPath("$.dataConclusao").doesNotExist();
	}


	@Test
	void TestCriarSemSucesso(){
		var todo = new Todo(
			null,
			"",
			"",
			false,
			true,
			null,
			null,
			null
		);

		webTestClient
		.post()
		.uri("/todos")
		.bodyValue(todo)
		.exchange()
		.expectStatus().isBadRequest();
	}

	












}