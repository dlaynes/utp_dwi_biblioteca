package com.dwigs.biblioteca.service;

import com.dwigs.biblioteca.model.Editorial;
import com.dwigs.biblioteca.model.Editorial;
import com.dwigs.biblioteca.model.Libro;
import com.dwigs.biblioteca.repository.EditorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EditorialService {

    private final EditorialRepository repository;

    @Autowired
    public EditorialService(EditorialRepository repository){
        this.repository = repository;
    }

    public List<Editorial> dameEditoriales(){
        return repository.findAll();
    }

    public Long contarEditoriales() { return repository.count(); }

    public Optional<Editorial> consultar(Long id) { return repository.findOneById(id); }
}
