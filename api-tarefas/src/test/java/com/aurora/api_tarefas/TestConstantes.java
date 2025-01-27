package com.aurora.api_tarefas;

import com.aurora.api_tarefas.DTO.TodoDTO;
import com.aurora.api_tarefas.Entity.*;

import java.time.LocalDateTime;
import java.util.*;

public class TestConstantes {

    public static final List<TodoDTO> TODOS = new ArrayList<>() {
        {
            add(new TodoDTO(
                1L,
                "Tarefa Teste I",
                "Descrição da tarefa teste I",
                false,
                true,
                LocalDateTime.now().plusDays(1), 
                Categoria.OUTROS, 
                LocalDateTime.now(), 
                null 
            ));
            add(new TodoDTO(
                2L,
                "Tarefa Teste II",
                "Descrição da tarefa teste II",
                true,
                false,
                LocalDateTime.now().plusDays(5), 
                Categoria.LAZER, 
                LocalDateTime.now().minusDays(2), 
                LocalDateTime.now() 
            ));

            add(new TodoDTO(
                3L,
                "Tarefa Teste III",
                "Descrição da tarefa teste III",
                true,
                false,
                LocalDateTime.now().plusDays(5), 
                Categoria.TRABALHO, 
                LocalDateTime.now().minusDays(2), 
                LocalDateTime.now() 
            ));
        }
    };

    
    public static final TodoDTO TODO = TODOS.get(0);
}
