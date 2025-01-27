package com.aurora.api_tarefas.DTO;

import java.time.LocalDateTime;

import com.aurora.api_tarefas.Entity.Categoria;
import com.aurora.api_tarefas.Entity.Todo;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;

public record TodoDTO(
    Long id, 

    @NotBlank(message = "O nome da tarefa é obrigatório.") 
    String nome,

    @NotBlank(message = "A descrição da tarefa é obrigatória.") 
    String descricao, 
    
    boolean realizado, 
    boolean prioridade, 

    @FutureOrPresent(message = "O prazo deve ser uma data futura ou hoje.") 
    LocalDateTime prazo, 

    Categoria categoria, 
    LocalDateTime dataCriacao,
    LocalDateTime dataConclusao
) {
    public TodoDTO(Todo todolst) {
        this(
            todolst.getId(),
            todolst.getNome(),
            todolst.getDescricao(),
            todolst.isRealizado(),
            todolst.isPrioridade(),
            todolst.getPrazo(),
            todolst.getCategoria(),
            todolst.getDataCriacao(),
            todolst.getDataConclusao()
        );
    }
}
