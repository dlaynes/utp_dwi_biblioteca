package com.dwigs.biblioteca.service;

import com.dwigs.biblioteca.model.Editorial;
import com.dwigs.biblioteca.repository.EditorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditorialService extends InMemoryService<Editorial> {

    @Autowired
    EditorialRepository repository;

    @Override
    public void validarCreacion(Editorial obj) {
        if(obj.getNombre().isEmpty()){
            throw new IllegalArgumentException("Debe indicar un nombre");
        }
    }

    @Override
    public void validarEdicion(Editorial obj) {
        if(obj.getNombre().isEmpty()){
            throw new IllegalArgumentException("Debe indicar un nombre");
        }
    }
}
