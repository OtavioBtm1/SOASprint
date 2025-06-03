package com.fiap.checkpoint1.controller;

import com.fiap.checkpoint1.model.Registro;
import com.fiap.checkpoint1.service.RegistroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registros")
public class RegistroController {

    @Autowired
    private RegistroService service;

    @PostMapping
    public ResponseEntity<Registro> criar(@RequestBody Registro registro) {
        Registro salvo = service.salvar(registro);
        return ResponseEntity.ok(salvo);
    }

    @GetMapping
    public ResponseEntity<List<Registro>> listarTodos() {
        List<Registro> lista = service.listarTodos();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Registro> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Registro> atualizar(@PathVariable Long id, @RequestBody Registro novoRegistro) {
        return service.buscarPorId(id)
                .map(registroExistente -> {
                    registroExistente.setData(novoRegistro.getData());
                    registroExistente.setHorario(novoRegistro.getHorario());
                    registroExistente.setCidade(novoRegistro.getCidade());
                    registroExistente.setEstado(novoRegistro.getEstado());
                    Registro atualizado = service.salvar(registroExistente);
                    return ResponseEntity.ok(atualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (service.buscarPorId(id).isPresent()) {
            service.deletar(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/cidade/{cidade}")
    public ResponseEntity<List<Registro>> buscarPorCidade(@PathVariable String cidade) {
        List<Registro> registros = service.buscarPorCidade(cidade);
        return ResponseEntity.ok(registros);
    }
}
