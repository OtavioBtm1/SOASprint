package com.fiap.checkpoint1.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Registro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate data;
    private LocalTime horario;
    private String cidade;
    private String estado;

    // Construtor vazio
    public Registro() {
    }

    // Construtor completo
    public Registro(LocalDate data, LocalTime horario, String cidade, String estado) {
        this.data = data;
        this.horario = horario;
        this.cidade = cidade;
        this.estado = estado;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Apagao{" +
                "id=" + id +
                ", data=" + data +
                ", horario=" + horario +
                ", cidade='" + cidade + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}
