package com.dwigs.biblioteca.service;

import com.dwigs.biblioteca.model.HasId;
import com.dwigs.biblioteca.model.Libro;
import com.dwigs.biblioteca.repository.IRepository;
import com.dwigs.biblioteca.repository.InMemoryRepository;

import java.util.List;
import java.util.Optional;

public abstract class InMemoryService<T extends HasId> {
    public InMemoryRepository<T> repository;

    public List<T> listar() {
        return repository.findAll();
    }

    public Optional<T> consultar(long id){
        return repository.findById(id);
    }

    public T crear(T obj){
        validarCreacion(obj);
        return repository.create(obj);
    }

    public T reemplazar(long id, T obj){
        validarEdicion(obj);
        return repository.upsert(id, obj);
    }

    public boolean eliminar(long id){
        return repository.delete(id);
    }

    public abstract void validarCreacion(T obj);

    public abstract void validarEdicion(T obj);
}
