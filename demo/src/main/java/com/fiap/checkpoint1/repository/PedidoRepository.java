package com.fiap.checkpoint1.repository;

import com.fiap.checkpoint1.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}