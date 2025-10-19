package com.dwigs.biblioteca.service;

import com.dwigs.biblioteca.model.Libro;
import com.dwigs.biblioteca.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibroService {

    private final LibroRepository repository;

    @Autowired
    public LibroService(LibroRepository repository){
        this.repository = repository;
    }

    public List<Libro> listar(){
        return repository.findAll();
    }

    public Long contarLibros() { return repository.count(); }

    public Libro crear(Libro libro) { return repository.save(libro); }

    public Optional<Libro> consultar(Long id) { return repository.findOneById(id); }

    public Libro actualizar(long id, Libro libro) { return repository.save(libro); }

    public boolean borrar(long id) {
        repository.deleteById(id);
        return true;
    }

}
