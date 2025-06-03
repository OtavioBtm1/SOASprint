package com.fiap.checkpoint1.service;

import com.fiap.checkpoint1.model.Registro;
import com.fiap.checkpoint1.repository.RegistroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegistroService {

    @Autowired
    private RegistroRepository repository;

    public Registro salvar(Registro registro) {
        return repository.save(registro);
    }

    public List<Registro> listarTodos() {
        return repository.findAll();
    }

    public Optional<Registro> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

    public List<Registro> buscarPorCidade(String cidade) {
        return repository.findByCidade(cidade);
    }
}
