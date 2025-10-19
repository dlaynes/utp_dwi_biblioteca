package com.dwigs.biblioteca.service;

import com.dwigs.biblioteca.model.Autor;
import com.dwigs.biblioteca.model.Libro;
import com.dwigs.biblioteca.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutorService {

    private final AutorRepository repository;

    @Autowired
    public AutorService(AutorRepository repository){
        this.repository = repository;
    }

    public List<Autor> listar(){
        return repository.findAll();
    }

    public Long contarAutores() { return repository.count(); }

    public Optional<Autor> consultar(Long id) { return repository.findOneById(id); }
}
