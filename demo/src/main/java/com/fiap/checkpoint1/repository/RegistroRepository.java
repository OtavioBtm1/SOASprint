package com.fiap.checkpoint1.repository;

import com.fiap.checkpoint1.model.Registro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistroRepository extends JpaRepository<Registro, Long> {
    List<Registro> findByCidade(String cidade);
}
