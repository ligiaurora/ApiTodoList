package com.aurora.api_tarefas.DTO;

import java.time.LocalDateTime;
import com.aurora.api_tarefas.Entity.Categoria;
import com.aurora.api_tarefas.Entity.Todo;

public record TodoDTO(Long id, String nome, String descricao, boolean realizado, boolean prioridade, LocalDateTime prazo, Categoria categoria) {
    public TodoDTO(Todo todolst) {
        this(
            todolst.getId(),
            todolst.getNome(),
            todolst.getDescricao(),
            todolst.isRealizado(),
            todolst.isPrioridade(),
            todolst.getPrazo(),
            todolst.getCategoria()
        );
    }
}