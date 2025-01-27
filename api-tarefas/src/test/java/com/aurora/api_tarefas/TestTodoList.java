package com.aurora.api_tarefas;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.aurora.api_tarefas.Entity.Todo;
import com.aurora.api_tarefas.DTO.TodoDTO;
import com.aurora.api_tarefas.Entity.Categoria;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql("./resources/remove.sql")
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
            LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS),
            null
        );

	webTestClient
            .post()
            .uri("/todos")
            .bodyValue(todo)
            .exchange()
            .expectStatus().isOk()
            .expectBody()
			.jsonPath("$").isArray()
            .jsonPath("$[0].id").isNotEmpty()
            .jsonPath("$[0].nome").isEqualTo(todo.getNome())
            .jsonPath("$[0].descricao").isEqualTo(todo.getDescricao())
            .jsonPath("$[0].realizado").isEqualTo(todo.isRealizado())
            .jsonPath("$[0].prioridade").isEqualTo(todo.isPrioridade())
            .jsonPath("$[0].categoria").isEqualTo(todo.getCategoria().name())
			.jsonPath("$[0].prazo").value(value -> {
				LocalDateTime prazoRetornado = LocalDateTime.parse(value.toString());
				Assertions.assertEquals(
					todo.getPrazo().truncatedTo(ChronoUnit.SECONDS), 
					prazoRetornado.truncatedTo(ChronoUnit.SECONDS)
				);
			})
            .jsonPath("$[0].dataConclusao").doesNotExist();
	}


	@Test
	void TestCriarTodoSemSucesso(){
		 var todoDTO = new TodoDTO(
			null,
			"",
			"",
			false,
			true,
			null,
			null,
			null,
			null
		);

	webTestClient
		.post()
		.uri("/todos")
		.bodyValue(todoDTO)
		.exchange()
		.expectStatus().isBadRequest();
	}

	@Sql("./resources/importar.sql")
    @Test
    void testAtualizarTodoSuccesso() {
        var todoAtualizar = TestConstantes.TODO;
        var AtualizarTodo = new TodoDTO(
            todoAtualizar.id(),
            todoAtualizar.nome() + " Tarefa Teste I Atualizado",
            todoAtualizar.descricao() + " Descrição de Teste Atualizada",
            !todoAtualizar.realizado(),
            todoAtualizar.prioridade(),
            todoAtualizar.prazo().plusDays(2),
            Categoria.TRABALHO,
            todoAtualizar.dataCriacao(),
            null
        );

        webTestClient
            .put()
            .uri("/todos/" + todoAtualizar.id())
            .bodyValue(AtualizarTodo)
            .exchange()
            .expectStatus().isOk()
            .expectBody()
			.jsonPath("$").isArray()
            .jsonPath("[0].id").isEqualTo(todoAtualizar.id())
            .jsonPath("[0].nome").isEqualTo(AtualizarTodo.nome())
            .jsonPath("[0].descricao").isEqualTo(AtualizarTodo.descricao())
            .jsonPath("[0].realizado").isEqualTo(AtualizarTodo.realizado())
            .jsonPath("[0].prioridade").isEqualTo(AtualizarTodo.prioridade());
    }

	@Test
    void testAtualizarTodoSemSucesso() {
        var SemId = 999L;

        var AtualizarTodo = new TodoDTO(
            SemId,
            "Nome Inválido",
            "Descrição Inválida",
            false,
            false,
            null,
            Categoria.OUTROS,
            null,
            null
        );

        webTestClient
            .put()
            .uri("/todos/" + SemId)
            .bodyValue(AtualizarTodo)
            .exchange()
            .expectStatus().isNotFound();
    }
	
	@Sql("./resources/importar.sql")
    @Test
    void testDeletarTodoSuccesso() {
        var todoDeletar = TestConstantes.TODOS.get(0);

        webTestClient
            .delete()
            .uri("/todos/" + todoDeletar.id())
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.length()").isEqualTo(TestConstantes.TODOS.size() - 1);
    }

	@Test
    void testDeletarTodoSemSucesso() {
        var InexistenteID = 999L;

        webTestClient
            .delete()
            .uri("/todos/" + InexistenteID)
            .exchange()
            .expectStatus().isNotFound();
    }

	@Sql("./resources/importar.sql")
    @Test
    void testListarTodos() {
        webTestClient
            .get()
            .uri("/todos")
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.length()").isEqualTo(TestConstantes.TODOS.size());
    }
}