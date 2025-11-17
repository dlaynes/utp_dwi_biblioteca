package com.dwigs.biblioteca.controller.api.publico;

import com.dwigs.biblioteca.model.Idioma;
import com.dwigs.biblioteca.repository.IdiomaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/api/publico/idiomas")
public class IdiomasApiController {
    private IdiomaRepository idiomaRepository;

    @Autowired
    public IdiomasApiController(
            IdiomaRepository idiomaRepository
    ){
        this.idiomaRepository = idiomaRepository;
    }

    @GetMapping()
    public List<Idioma> listar(){
        return idiomaRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Idioma> consultar(@PathVariable long id){

        Optional<Idioma> obj = idiomaRepository.findOneById(id);
        return obj.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
