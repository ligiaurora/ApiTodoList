package com.aurora.api_tarefas.Entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "todos")

public class Todo {
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome da tarefa é obrigatório.")
    private String nome;
    private String descricao;
    private boolean realizado;
    private boolean prioridade;

    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    @CreationTimestamp
    private LocalDateTime dataCriacao;

    @NotBlank(message = "O prazo é obrigatório")
    @FutureOrPresent(message = "O prazo deve ser uma data futura ou hoje.")
    private LocalDateTime prazo;

    private LocalDateTime dataConclusao;

    public Todo() {}

    public Todo(Long id, String nome, String descricao, boolean realizado, boolean prioridade, LocalDateTime prazo, Categoria categoria, LocalDateTime dataCriacao,LocalDateTime dataConclusao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.realizado = realizado;
        this.prioridade = prioridade;
        this.prazo = prazo;
        this.categoria = categoria;
        this.dataCriacao = dataCriacao;
        this.dataConclusao = dataConclusao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isRealizado() {
        return realizado;
    }

    public void setRealizado(boolean realizado) {
        this.realizado = realizado;
    }

    public boolean isPrioridade() {
        return prioridade;
    }

    public void setPrioridade(boolean prioridade) {
        this.prioridade = prioridade;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }   

    public LocalDateTime getPrazo() {
        return prazo;
    }

    public void setPrazo(LocalDateTime prazo) {
        this.prazo = prazo;
    }

    public LocalDateTime getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(LocalDateTime dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    @Override
    public String toString() {
        return "Todo [id=" + id + ", nome=" + nome + ", descricao=" + descricao + ", realizado=" + realizado
                + ", prioridade=" + prioridade + ", categoria=" + categoria + ", dataCriacao=" + dataCriacao
                + ", prazo=" + prazo + ", dataConclusao=" + dataConclusao + "]";
    }  
}
