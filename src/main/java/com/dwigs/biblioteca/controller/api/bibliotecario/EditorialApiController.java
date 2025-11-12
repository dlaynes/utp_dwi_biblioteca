package com.dwigs.biblioteca.controller.api.bibliotecario;

import com.dwigs.biblioteca.dto.request.ActualizarEditorialDTO;
import com.dwigs.biblioteca.model.Editorial;
import com.dwigs.biblioteca.repository.EditorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/api/bibliotecario/editoriales")
public class EditorialApiController {
    private EditorialRepository repository;

    @Autowired
    public EditorialApiController(
            EditorialRepository repository
    ){
        this.repository = repository;
    }

    @GetMapping()
    public List<Editorial> listar(){
        return repository.findAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Editorial> consultar(@PathVariable long id){

        Optional<Editorial> obj = repository.findOneById(id);
        return obj.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyAuthority('ROLE_BIBLIOTECARIO', 'ROLE_ADMIN')")
    @PostMapping()
    public ResponseEntity<Editorial> crear(@RequestBody ActualizarEditorialDTO crearDTO){

        Editorial obj = new Editorial();

        obj.setNombre(crearDTO.getNombre());
        obj.setCiudad(crearDTO.getCiudad());
        obj.setPais(crearDTO.getPais());
        obj.setFechaRegistro(LocalDateTime.now());
        Editorial nuevo = repository.save(obj);
        return ResponseEntity.created(URI.create("/api/bibliotecario/editoriales/"+ nuevo.getId())).body(nuevo);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_BIBLIOTECARIO', 'ROLE_ADMIN')")
    @PutMapping(value="/{id}")
    public ResponseEntity<Editorial> reemplazar(@PathVariable long id, @RequestBody ActualizarEditorialDTO editarDTO){

        Editorial obj = repository.findOneById(id).orElseThrow();

        obj.setNombre(editarDTO.getNombre());
        obj.setCiudad(editarDTO.getCiudad());
        obj.setPais(editarDTO.getPais());

        return ResponseEntity.ok(repository.save(obj));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_BIBLIOTECARIO', 'ROLE_ADMIN')")
    @DeleteMapping(value="/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable long id){
        repository.deleteById(id);
        return ResponseEntity.noContent().build(); //ResponseEntity.notFound().build();
    }


}
