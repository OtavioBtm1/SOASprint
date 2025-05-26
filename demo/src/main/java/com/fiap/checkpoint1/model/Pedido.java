package com.fiap.checkpoint1.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomeCliente;

    @Column(nullable = false)
    private LocalDate dataPedido = LocalDate.now();

    @Column(nullable = false)
    private double valorTodo;

    public Pedido() {
    }

    public Pedido(String nomeCliente, double valorTodo) {
        this.nomeCliente = nomeCliente;
        this.valorTodo = valorTodo;
    }

    public Long getId() {
        return id;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        if (nomeCliente == null || nomeCliente.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é Obrigatoriooo");
        }
        this.nomeCliente = nomeCliente;
    }

    public LocalDate getDataPedido() {
        return dataPedido;
    }

    public double getValorTodo() {
        return valorTodo;
    }

    public void setValorTodo(double valorTodo) {
        if (valorTodo < 0) {
            throw new IllegalArgumentException("O valor que voce colocou não pode ser negativo");
        }
        this.valorTodo = valorTodo;
    }
}