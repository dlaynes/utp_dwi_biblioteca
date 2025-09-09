package com.dwigs.biblioteca.service;

import com.dwigs.biblioteca.model.Libro;
import com.dwigs.biblioteca.repository.LibroRepository;
import org.springframework.stereotype.Service;

@Service
public class LibroService extends InMemoryService<Libro> {

    public LibroService(){
        this.repository = new LibroRepository();
    }

    @Override
    public void validarCreacion(Libro obj) {
        if(obj.getTitulo() == null){
            throw new IllegalArgumentException("Se debe incluir un título");
        }
    }

    @Override
    public void validarEdicion(Libro obj) {
        if(obj.getTitulo() == null){
            throw new IllegalArgumentException("Se debe incluir un título");
        }
    }
}
