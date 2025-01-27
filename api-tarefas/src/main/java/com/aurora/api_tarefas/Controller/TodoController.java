package com.aurora.api_tarefas.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.time.LocalDateTime;

import com.aurora.api_tarefas.DTO.*;
import com.aurora.api_tarefas.Entity.*;
import com.aurora.api_tarefas.Service.TodoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/todos")
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    public ResponseEntity<List<TodoDTO>> criar(@Valid @RequestBody TodoDTO todoDTO) {
        Todo todo = new Todo();
        todo.setNome(todoDTO.nome());
        todo.setDescricao(todoDTO.descricao());
        todo.setRealizado(todoDTO.realizado());
        todo.setPrioridade(todoDTO.prioridade());
        todo.setPrazo(todoDTO.prazo() != null ? todoDTO.prazo() : LocalDateTime.now().plusDays(1));
    
        todo.setCategoria(todoDTO.categoria());
        todo.setDataCriacao(todoDTO.dataCriacao());
    
    
        List<TodoDTO> todos = todoService.criar(todo).stream().map(TodoDTO::new).toList();
        return ResponseEntity.ok(todos);
    }
    

    @GetMapping
    public ResponseEntity<List<TodoDTO>> ler() {
        List<TodoDTO> todos = todoService.ler().stream().map(TodoDTO::new).toList();
        return ResponseEntity.ok(todos);
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<TodoDTO>> lerPorCategoria(@PathVariable Categoria categoria) {
        List<TodoDTO> todos = todoService.lerPorCategoria(categoria).stream().map(TodoDTO::new).toList();
        return ResponseEntity.ok(todos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<List<TodoDTO>> atualizar(
    @PathVariable Long id, 
    @Valid @RequestBody TodoDTO todoDTO) {
        Todo todo = new Todo();
        todo.setId(id);
        todo.setNome(todoDTO.nome());
        todo.setDescricao(todoDTO.descricao());
        todo.setRealizado(todoDTO.realizado());
        todo.setPrioridade(todoDTO.prioridade());
        todo.setPrazo(todoDTO.prazo());
        todo.setDataCriacao(todoDTO.dataCriacao());
        todo.setCategoria(todoDTO.categoria());
        

    List<TodoDTO> todos = todoService.atualizar(todo).stream().map(TodoDTO::new).toList();
    return ResponseEntity.ok(todos);
    }

    @PatchMapping("/{id}/realizar")
    public ResponseEntity<List<TodoDTO>> marcarComoRealizada(@PathVariable Long id) {
        List<TodoDTO> todos = todoService.marcarComoRealizada(id)
            .stream()
            .map(TodoDTO::new)
            .toList();
        return ResponseEntity.ok(todos);
    }

    @PatchMapping("/{id}/desfazer")
    public ResponseEntity<List<TodoDTO>> desmarcarComoRealizada(@PathVariable Long id) {
        List<TodoDTO> todos = todoService.desmarcarComoRealizada(id)
            .stream()
            .map(TodoDTO::new)
            .toList();
        return ResponseEntity.ok(todos);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<List<TodoDTO>> apagar(@PathVariable Long id) {
        List<TodoDTO> todos = todoService.apagar(id).stream().map(TodoDTO::new).toList();
        return ResponseEntity.ok(todos);
    }
}