package com.dwigs.biblioteca.controller.api.publico;

import com.dwigs.biblioteca.dto.request.ActualizarInventarioLibroDTO;
import com.dwigs.biblioteca.model.InventarioLibro;
import com.dwigs.biblioteca.model.Local;
import com.dwigs.biblioteca.repository.InventarioLibroRepository;
import com.dwigs.biblioteca.repository.LibroRepository;
import com.dwigs.biblioteca.repository.LocalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/api/publico/inventarios")
public class InventariosLibroApiController {

    private InventarioLibroRepository inventarioLibroRepository;

    private LibroRepository libroRepository;

    private LocalRepository localRepository;

    @Autowired
    public InventariosLibroApiController(
            InventarioLibroRepository inventarioLibroRepository,
            LibroRepository libroRepository,
            LocalRepository localRepository
    ){
        this.inventarioLibroRepository = inventarioLibroRepository;
        this.libroRepository = libroRepository;
        this.localRepository = localRepository;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_BIBLIOTECARIO', 'ROLE_ADMIN')")
    @GetMapping()
    public List<InventarioLibro> listar(){
        return inventarioLibroRepository.findAll();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_BIBLIOTECARIO', 'ROLE_ADMIN')")
    @GetMapping("/de-libro/{libroId}")
    public List<InventarioLibro> listarDeLibro(@PathVariable Long libroId){
        return inventarioLibroRepository.findByLibroId(libroId);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<InventarioLibro> consultar(@PathVariable long id){

        Optional<InventarioLibro> obj = inventarioLibroRepository.findOneById(id);
        return obj.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyAuthority('ROLE_BIBLIOTECARIO', 'ROLE_ADMIN')")
    @PostMapping()
    public ResponseEntity<InventarioLibro> crear(@RequestBody ActualizarInventarioLibroDTO crearDTO){

        Local local = localRepository.findOneById(1L).orElseThrow();

        InventarioLibro inventarioLibro = new InventarioLibro();
        inventarioLibro.setLibro(libroRepository.findOneById(crearDTO.getLibroId()).orElseThrow());
        inventarioLibro.setDisponibles(crearDTO.getDisponibles());
        inventarioLibro.setFechaRegistro(LocalDateTime.now());
        inventarioLibro.setLocal(local);

        InventarioLibro nuevo = inventarioLibroRepository.save(inventarioLibro);
        return ResponseEntity.created(URI.create("/api/publico/inventarios/"+ inventarioLibro.getId())).body(nuevo);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_BIBLIOTECARIO', 'ROLE_ADMIN')")
    @PutMapping(value="/{id}")
    public ResponseEntity<InventarioLibro> reemplazar(@PathVariable long id, @RequestBody ActualizarInventarioLibroDTO editarDTO){

        InventarioLibro inventarioLibro = inventarioLibroRepository.findOneById(id).orElseThrow();
        Integer nuevoDisponibles = editarDTO.getDisponibles();
        // TODO: mensaje de error, los libros disponibles no pueden ser menores a los libros prestados
        if(nuevoDisponibles < inventarioLibro.getPrestados()){
            return ResponseEntity.unprocessableEntity().build();
        }
        inventarioLibro.setDisponibles(nuevoDisponibles);

        return ResponseEntity.ok(inventarioLibroRepository.save(inventarioLibro));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_BIBLIOTECARIO', 'ROLE_ADMIN')")
    @DeleteMapping(value="/{id}", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> eliminar(@PathVariable long id){
        inventarioLibroRepository.deleteById(id);
        return ResponseEntity.noContent().build(); //ResponseEntity.notFound().build();
    }

}
