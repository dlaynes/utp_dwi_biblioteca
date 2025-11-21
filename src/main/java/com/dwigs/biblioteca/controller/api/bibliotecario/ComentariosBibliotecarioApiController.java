package com.dwigs.biblioteca.controller.api.bibliotecario;

import com.dwigs.biblioteca.model.Comentario;
import com.dwigs.biblioteca.repository.ComentarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/api/bibliotecario/comentarios")
public class ComentariosBibliotecarioApiController {
    private ComentarioRepository repository;

    @Autowired
    public ComentariosBibliotecarioApiController(
            ComentarioRepository repository
    ){
        this.repository = repository;
    }

    @PostMapping()
    public ResponseEntity<?> crear(@RequestBody Comentario comentario){
        repository.save(comentario);
        return ResponseEntity.ok(true);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Comentario> consultar(@PathVariable long id){

        Optional<Comentario> obj = repository.findOneById(id);
        return obj.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyAuthority('ROLE_BIBLIOTECARIO', 'ROLE_ADMIN')")
    @GetMapping()
    public List<Comentario> listar(){
        return repository.findAll();
    }
}
