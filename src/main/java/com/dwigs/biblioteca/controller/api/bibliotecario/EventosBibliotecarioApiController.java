package com.dwigs.biblioteca.controller.api.bibliotecario;

import com.dwigs.biblioteca.dto.request.ActualizarEventoDTO;
import com.dwigs.biblioteca.model.Evento;
import com.dwigs.biblioteca.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/api/bibliotecario/eventos")
public class EventosBibliotecarioApiController {
    private EventoRepository repository;

    @Autowired
    public EventosBibliotecarioApiController(
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

    @PostMapping()
    public ResponseEntity<Evento> crear(@RequestBody ActualizarEventoDTO crearDTO){

        Evento obj = new Evento();
        obj.setTipoEvento(crearDTO.getTipoEvento());
        obj.setFechaEvento(crearDTO.getFechaEvento());
        obj.setTitulo(crearDTO.getTitulo());
        obj.setFechaRegistro(LocalDateTime.now());

        Evento nuevo = repository.save(obj);
        return ResponseEntity.created(URI.create("/api/publico/eventos/"+ obj.getId())).body(nuevo);
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<Evento> reemplazar(@PathVariable long id, @RequestBody ActualizarEventoDTO editarDTO){

        Evento obj = repository.findOneById(id).orElseThrow();

        obj.setTipoEvento(editarDTO.getTipoEvento());
        obj.setFechaEvento(editarDTO.getFechaEvento());
        obj.setTitulo(editarDTO.getTitulo());

        return ResponseEntity.ok(repository.save(obj));
    }

    @DeleteMapping(value="/{id}", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> eliminar(@PathVariable long id){
        repository.deleteById(id);
        return ResponseEntity.noContent().build(); //ResponseEntity.notFound().build();
    }


}
