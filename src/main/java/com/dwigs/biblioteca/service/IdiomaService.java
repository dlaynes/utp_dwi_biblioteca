package com.dwigs.biblioteca.service;

import com.dwigs.biblioteca.model.Idioma;
import com.dwigs.biblioteca.model.Libro;
import com.dwigs.biblioteca.repository.IdiomaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IdiomaService {

    private final IdiomaRepository repository;

    @Autowired
    public IdiomaService(IdiomaRepository idiomaRepository) {
        this.repository = idiomaRepository;
    }

    public List<Idioma> dameIdiomas(){
        return repository.findAll();
    }

    public Long contarIdiomas() { return repository.count(); }
    public Optional<Idioma> consultar(Long id) { return repository.findOneById(id); }
}
