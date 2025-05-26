package com.fiap.checkpoint1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fiap.checkpoint1.model.Pedido;
import com.fiap.checkpoint1.repository.PedidoRepository;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> buscarPorId(Long id) {
        return pedidoRepository.findById(id);
    }

    public Pedido salvar(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public Pedido atualizar(Long id, Pedido pedidoAtualizado) {
        return pedidoRepository.findById(id).map(pedido -> {
            pedido.setNomeCliente(pedidoAtualizado.getNomeCliente());
            pedido.setValorTodo(pedidoAtualizado.getValorTodo());
            return pedidoRepository.save(pedido);
        }).orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }

    public void deletar(Long id) {
        pedidoRepository.deleteById(id);
    }
}
