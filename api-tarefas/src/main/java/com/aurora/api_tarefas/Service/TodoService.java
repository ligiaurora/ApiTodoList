package com.aurora.api_tarefas.Service;

import java.time.LocalDateTime;
import java.util.List;

import com.aurora.api_tarefas.Entity.Todo;
import com.aurora.api_tarefas.Entity.Categoria;
import com.aurora.api_tarefas.Repository.TodoRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class TodoService {
    private TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Transactional
    public List<Todo> criar(Todo todo) {
    // Validação manual dos campos obrigatórios
        if (todo.getNome() == null || todo.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da tarefa é obrigatório.");
        }
    
        if (todo.getDescricao() == null || todo.getDescricao().trim().isEmpty()) {
            throw new IllegalArgumentException("A descrição da tarefa é obrigatória.");
        }

        if (todo.getPrazo() == null) {
            todo.setPrazo(LocalDateTime.now().plusDays(1));
        }

        VerificaConclusao(todo, todo);
        todoRepository.save(todo);
        return ler();
    }
    

    public List <Todo> ler(){
        Sort ordenacao = Sort.by("prioridade").descending().and(Sort.by("nome").ascending()).and(Sort.by("descricao").ascending()); 
            return todoRepository.findAll(ordenacao);
    }

    public List<Todo> lerPorCategoria(Categoria categoria) {
        return todoRepository.findAll().stream().filter(todo -> categoria.equals(todo.getCategoria())).toList();
    }

    /*@Transactional
    public List<Todo> atualizar(Long id, Todo todoAtualizado) {
        Todo todo = todoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada"));
        
        if (todoAtualizado.getNome() != null) {
            todo.setNome(todoAtualizado.getNome());
        }
        if (todoAtualizado.getDescricao() != null) {
            todo.setDescricao(todoAtualizado.getDescricao());
        }
        if (todoAtualizado.getPrazo() != null) {
            todo.setPrazo(todoAtualizado.getPrazo());
        }
        if (todoAtualizado.getCategoria() != null) {
            Categoria categoria = categoriaRepository.findById(todoAtualizado.getCategoria().getId())
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));
            todo.setCategoria(categoria);
        }
        
        todo.setPrioridade(todoAtualizado.isPrioridade());
        
        todoRepository.save(todo);
        return ler();
    }*/

    @Transactional
    public List<Todo> atualizar(Todo todo) {
        Todo todoExistente = todoRepository.findById(todo.getId())
            .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada"));

        System.out.println(todoExistente.toString());
        todoExistente.setDataCriacao(todo.getDataCriacao());

        todoExistente.setNome(todo.getNome());
        todoExistente.setDescricao(todo.getDescricao());
        todoExistente.setPrioridade(todo.isPrioridade());
        todoExistente.setRealizado(todo.isRealizado());
        todoExistente.setPrazo(todo.getPrazo());
        todoExistente.setCategoria(todo.getCategoria());

        VerificaConclusao(todo, todoExistente);
        
        todoRepository.save(todoExistente);
        return ler();
    }

    private void VerificaConclusao(Todo todo, Todo todoExistente) {
        if (todo.isRealizado())
            todoExistente.setDataConclusao(LocalDateTime.now());
    }



    @Transactional
    public List<Todo> marcarComoRealizada(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada ou não existe"));
        
        todo.setRealizado(true);
        todo.setDataConclusao(LocalDateTime.now());
        
        todoRepository.save(todo);
        return ler();
    }

    @Transactional
    public List<Todo> desmarcarComoRealizada(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada ou não existe"));
        
        todo.setRealizado(false);
        todo.setDataConclusao(null);
        
        todoRepository.save(todo);
        return ler();
    }

    @Transactional
    public List<Todo> apagar(Long id) {
        if (!todoRepository.existsById(id)) {
            throw new EntityNotFoundException("Tarefa não encontrada");
        }
        todoRepository.deleteById(id);
        return ler();
    }
    
    public boolean isAtrasada(Todo todo) {
        return !todo.isRealizado() && todo.getPrazo().isBefore(LocalDateTime.now());
    }
}
