package com.dwigs.biblioteca.controller.api;

import com.dwigs.biblioteca.dto.request.CrearLibroDTO;
import com.dwigs.biblioteca.model.Libro;
import com.dwigs.biblioteca.service.AutorService;
import com.dwigs.biblioteca.service.EditorialService;
import com.dwigs.biblioteca.service.IdiomaService;
import com.dwigs.biblioteca.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController("/api/libros")
public class LibrosApiController {

    @Autowired
    private LibroService libroService;

    @Autowired
    private AutorService autorService;

    @Autowired
    private EditorialService editorialService;

    @Autowired
    private IdiomaService idiomaService;

    @GetMapping
    public ResponseEntity<List<Libro>> listar(){
        return ResponseEntity.ok(libroService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Libro> consultar(@PathVariable long id){
        Optional<Libro> libro = libroService.consultar(id);
        return libro.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Libro> crear(@RequestBody CrearLibroDTO crearLibroDTO){
        Libro libro = crearLibroDTO.obtenerLibro();

        libro.setAutor(autorService.consultar(crearLibroDTO.getAutorId()).orElseThrow());
        libro.setIdioma(idiomaService.consultar(crearLibroDTO.getIdiomaId()).orElseThrow());
        libro.setEditorial(editorialService.consultar(crearLibroDTO.getEditorialId()).orElseThrow());
        Libro libroCreado = libroService.crear(libro);
        return ResponseEntity.created(URI.create("/api/libros/"+ libro.getId())).body(libroCreado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Libro> reemplazar(@PathVariable long id, @RequestBody CrearLibroDTO editarLibroDTO){
        Libro libro = editarLibroDTO.obtenerLibro();
        libro.setAutor(autorService.consultar(editarLibroDTO.getAutorId()).orElseThrow());
        libro.setIdioma(idiomaService.consultar(editarLibroDTO.getIdiomaId()).orElseThrow());
        libro.setEditorial(editorialService.consultar(editarLibroDTO.getEditorialId()).orElseThrow());
        return ResponseEntity.ok(libroService.reemplazar(id, libro));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable long id){
        return libroService.eliminar(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
