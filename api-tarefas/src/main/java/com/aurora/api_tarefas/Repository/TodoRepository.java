package com.aurora.api_tarefas.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.aurora.api_tarefas.Entity.Todo;
import com.aurora.api_tarefas.Entity.Categoria;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByCategoria(Categoria categoria);
}