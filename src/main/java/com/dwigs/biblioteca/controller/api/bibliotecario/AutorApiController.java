package com.dwigs.biblioteca.controller.api.bibliotecario;

import com.dwigs.biblioteca.dto.request.ActualizarAutorDTO;
import com.dwigs.biblioteca.model.Autor;
import com.dwigs.biblioteca.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/api/bibliotecario/autores")
public class AutorApiController {
    private AutorRepository repository;

    @Autowired
    public AutorApiController(
            AutorRepository repository
    ){
        this.repository = repository;
    }

    @GetMapping()
    public List<Autor> listar(){
        return repository.findAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Autor> consultar(@PathVariable long id){

        Optional<Autor> obj = repository.findOneById(id);
        return obj.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyAuthority('ROLE_BIBLIOTECARIO', 'ROLE_ADMIN')")
    @PostMapping()
    public ResponseEntity<Autor> crear(@RequestBody ActualizarAutorDTO crearDTO){

        Autor obj = new Autor();

        obj.setNombres(crearDTO.getNombres());
        obj.setApellidos(crearDTO.getApellidos());
        obj.setNacionalidad(crearDTO.getNacionalidad());
        obj.setComentarios(crearDTO.getComentarios());
        obj.setFechaRegistro(LocalDateTime.now());
        Autor nuevo = repository.save(obj);
        return ResponseEntity.created(URI.create("/api/bibliotecario/autores/"+ nuevo.getId())).body(nuevo);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_BIBLIOTECARIO', 'ROLE_ADMIN')")
    @PutMapping(value="/{id}")
    public ResponseEntity<Autor> reemplazar(@PathVariable long id, @RequestBody ActualizarAutorDTO editarDTO){

        Autor obj = repository.findOneById(id).orElseThrow();

        obj.setNombres(editarDTO.getNombres());
        obj.setApellidos(editarDTO.getApellidos());
        obj.setNacionalidad(editarDTO.getNacionalidad());
        obj.setComentarios(editarDTO.getComentarios());

        return ResponseEntity.ok(repository.save(obj));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_BIBLIOTECARIO', 'ROLE_ADMIN')")
    @DeleteMapping(value="/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable long id){
        repository.deleteById(id);
        return ResponseEntity.noContent().build(); //ResponseEntity.notFound().build();
    }

}
