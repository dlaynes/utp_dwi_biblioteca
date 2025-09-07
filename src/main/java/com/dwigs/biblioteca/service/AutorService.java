package com.dwigs.biblioteca.service;

import com.dwigs.biblioteca.model.Autor;
import com.dwigs.biblioteca.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorService extends InMemoryService<Autor> {

    @Autowired
    AutorRepository repository;

    @Override
    public void validarCreacion(Autor obj) {
        if(obj.getNombres().isEmpty()){
            throw new IllegalArgumentException("Debe indicar un nombre");
        }
    }

    @Override
    public void validarEdicion(Autor obj) {
        if(obj.getNombres().isEmpty()){
            throw new IllegalArgumentException("Debe indicar un nombre");
        }
    }
}
