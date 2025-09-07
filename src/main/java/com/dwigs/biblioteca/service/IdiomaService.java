package com.dwigs.biblioteca.service;

import com.dwigs.biblioteca.model.Idioma;
import com.dwigs.biblioteca.repository.IdiomaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IdiomaService extends InMemoryService<Idioma> {

    @Autowired
    IdiomaRepository repository;

    @Override
    public void validarCreacion(Idioma obj) {
        if(obj.getNombre().isEmpty()){
            throw new IllegalArgumentException("Debe indicar un nombre");
        }
    }

    @Override
    public void validarEdicion(Idioma obj) {
        if(obj.getNombre().isEmpty()){
            throw new IllegalArgumentException("Debe indicar un nombre");
        }
    }
}
