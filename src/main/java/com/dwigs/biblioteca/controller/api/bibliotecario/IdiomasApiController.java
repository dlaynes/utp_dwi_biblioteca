package com.dwigs.biblioteca.controller.api.bibliotecario;

import com.dwigs.biblioteca.dto.request.ActualizarIdiomaDTO;
import com.dwigs.biblioteca.model.Idioma;
import com.dwigs.biblioteca.repository.IdiomaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/api/bibliotecario/idiomas")
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

    @PreAuthorize("hasAnyAuthority('ROLE_BIBLIOTECARIO', 'ROLE_ADMIN')")
    @PostMapping()
    public ResponseEntity<Idioma> crear(@RequestBody ActualizarIdiomaDTO crearDTO){

        Idioma obj = new Idioma();

        obj.setNombre(crearDTO.getNombre());
        obj.setCodigo(crearDTO.getCodigo());
        obj.setFechaRegistro(LocalDateTime.now());
        Idioma nuevo = idiomaRepository.save(obj);
        return ResponseEntity.created(URI.create("/api/bibliotecario/idiomas/"+ nuevo.getId())).body(nuevo);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_BIBLIOTECARIO', 'ROLE_ADMIN')")
    @PutMapping(value="/{id}")
    public ResponseEntity<Idioma> reemplazar(@PathVariable long id, @RequestBody ActualizarIdiomaDTO editarDTO){

        Idioma obj = idiomaRepository.findOneById(id).orElseThrow();

        obj.setNombre(editarDTO.getNombre());
        obj.setCodigo(editarDTO.getCodigo());

        return ResponseEntity.ok(idiomaRepository.save(obj));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_BIBLIOTECARIO', 'ROLE_ADMIN')")
    @DeleteMapping(value="/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable long id){
        idiomaRepository.deleteById(id);
        return ResponseEntity.noContent().build(); //ResponseEntity.notFound().build();
    }

}
