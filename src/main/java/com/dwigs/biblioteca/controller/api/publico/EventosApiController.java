package com.dwigs.biblioteca.controller.api.publico;

import com.dwigs.biblioteca.model.Evento;
import com.dwigs.biblioteca.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/api/publico/eventos")
public class EventosApiController {

    private EventoRepository repository;

    @Autowired
    public EventosApiController(
            EventoRepository repository
    ){
        this.repository = repository;
    }

    @GetMapping()
    public List<Evento> listar(){
        return repository.findAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Evento> consultar(@PathVariable long id){

        Optional<Evento> obj = repository.findOneById(id);
        return obj.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
