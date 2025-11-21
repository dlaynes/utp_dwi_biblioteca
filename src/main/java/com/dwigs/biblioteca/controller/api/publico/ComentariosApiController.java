package com.dwigs.biblioteca.controller.api.publico;

import com.dwigs.biblioteca.model.Comentario;
import com.dwigs.biblioteca.repository.ComentarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/publico/comentarios")
public class ComentariosApiController {
    private ComentarioRepository repository;

    @Autowired
    public ComentariosApiController(
            ComentarioRepository repository
    ){
        this.repository = repository;
    }

    // TO DO: prevenir que alguien envie demasiados mensajes
    @PostMapping()
    public ResponseEntity<?> crear(@RequestBody Comentario comentario){
        repository.save(comentario);
        return ResponseEntity.ok(true);
    }
}
