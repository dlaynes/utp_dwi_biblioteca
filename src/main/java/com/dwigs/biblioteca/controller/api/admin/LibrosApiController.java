package com.dwigs.biblioteca.controller.api.admin;

import com.dwigs.biblioteca.dto.request.CrearLibroDTO;
import com.dwigs.biblioteca.model.Libro;
import com.dwigs.biblioteca.repository.LibroRepository;
import com.dwigs.biblioteca.service.AutorService;
import com.dwigs.biblioteca.service.EditorialService;
import com.dwigs.biblioteca.service.IdiomaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/api/admin/libros")
public class LibrosApiController {

    private LibroRepository libroRepository;

    private AutorService autorService;

    private EditorialService editorialService;

    private IdiomaService idiomaService;

    @Autowired
    public LibrosApiController(
        LibroRepository libroRepository,
        AutorService autorService,
        EditorialService editorialService,
        IdiomaService idiomaService
    ){
        this.libroRepository = libroRepository;
        this.autorService = autorService;
        this.editorialService = editorialService;
        this.idiomaService = idiomaService;
    }

    @GetMapping()
    public List<Libro> listar(){
        return libroRepository.findAll();
    }

    @GetMapping(value = "/{id}", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Libro> consultar(@PathVariable long id){

        Optional<Libro> libro = libroRepository.findOneById(id);
        return libro.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Libro> crear(@RequestBody CrearLibroDTO crearLibroDTO){

        Libro libro = crearLibroDTO.obtenerLibro();

        libro.setAutor(autorService.consultar(crearLibroDTO.getAutorId()).orElseThrow());
        libro.setIdioma(idiomaService.consultar(crearLibroDTO.getIdiomaId()).orElseThrow());
        libro.setEditorial(editorialService.consultar(crearLibroDTO.getEditorialId()).orElseThrow());
        Libro libroCreado = libroRepository.save(libro);
        return ResponseEntity.created(URI.create("/api/admin/libros/"+ libro.getId())).body(libroCreado);
    }

    @PutMapping(value="/{id}", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Libro> reemplazar(@PathVariable long id, @RequestBody CrearLibroDTO editarLibroDTO){

        Libro libro = editarLibroDTO.obtenerLibro();
        libro.setAutor(autorService.consultar(editarLibroDTO.getAutorId()).orElseThrow());
        libro.setIdioma(idiomaService.consultar(editarLibroDTO.getIdiomaId()).orElseThrow());
        libro.setEditorial(editorialService.consultar(editarLibroDTO.getEditorialId()).orElseThrow());
        return ResponseEntity.ok(libroRepository.save(libro));
    }

    @DeleteMapping(value="/{id}", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> eliminar(@PathVariable long id){
       libroRepository.deleteById(id);
       return ResponseEntity.noContent().build(); //ResponseEntity.notFound().build();
    }
}
