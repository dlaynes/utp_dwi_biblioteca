package com.dwigs.biblioteca.controller.api;

import com.dwigs.biblioteca.dto.request.CrearLibroDTO;
import com.dwigs.biblioteca.model.*;
import com.dwigs.biblioteca.service.AutorService;
import com.dwigs.biblioteca.service.EditorialService;
import com.dwigs.biblioteca.service.IdiomaService;
import com.dwigs.biblioteca.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/api/libros")
public class LibrosApiController {

    @Autowired
    private LibroService libroService;

    @Autowired
    private AutorService autorService;

    @Autowired
    private EditorialService editorialService;

    @Autowired
    private IdiomaService idiomaService;

    private void inicializar(){
        Autor autor = new Autor();
        autor.setId(1);
        autor.setNombres("Ricardo");
        autor.setApellidos("Palma");
        autor.setNacionalidad("Peruano");
        autor.setComentarios("Autor famoso de los siglos XIX - XX");
        autor.setFechaRegistro(LocalDateTime.now());
        autorService.reemplazar(1, autor);

        Editorial editorial = new Editorial();
        editorial.setId(1);
        editorial.setNombre("Navarrete");
        editorial.setPais("Perú");
        editorial.setCiudad("Lima");
        editorial.setFechaRegistro(LocalDateTime.now());
        editorialService.reemplazar(1, editorial);

        Idioma idioma = new Idioma();
        idioma.setNombre("Español");
        idioma.setCodigo("ES");
        idioma.setFechaRegistro(LocalDateTime.now());
        idiomaService.reemplazar(1, idioma);

        Libro libro = new Libro();
        libro.setTitulo("La Limeña");
        libro.setIbsm("em-1");
        libro.setNacionalidad("Peruana");
        libro.setId(1);
        libro.setIdioma(idioma);
        libro.setEditorial(editorial);
        libro.setAutor(autor);
        libro.setPaginas(500);
        libro.setGeneroLiterario(GeneroLiterario.narrativo);
        libro.setPublicadoEn(LocalDateTime.of(1922,1,1,0,0,0));
        libro.setId(1);
        libroService.reemplazar(1, libro);
    }

    @GetMapping
    public ResponseEntity<List<Libro>> listar(){
        inicializar();

        return ResponseEntity.ok(libroService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Libro> consultar(@PathVariable long id){
        inicializar();

        Optional<Libro> libro = libroService.consultar(id);
        return libro.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Libro> crear(@RequestBody CrearLibroDTO crearLibroDTO){
        inicializar();

        Libro libro = crearLibroDTO.obtenerLibro();

        libro.setAutor(autorService.consultar(crearLibroDTO.getAutorId()).orElseThrow());
        libro.setIdioma(idiomaService.consultar(crearLibroDTO.getIdiomaId()).orElseThrow());
        libro.setEditorial(editorialService.consultar(crearLibroDTO.getEditorialId()).orElseThrow());
        Libro libroCreado = libroService.crear(libro);
        return ResponseEntity.created(URI.create("/api/libros/"+ libro.getId())).body(libroCreado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Libro> reemplazar(@PathVariable long id, @RequestBody CrearLibroDTO editarLibroDTO){
        inicializar();

        Libro libro = editarLibroDTO.obtenerLibro();
        libro.setAutor(autorService.consultar(editarLibroDTO.getAutorId()).orElseThrow());
        libro.setIdioma(idiomaService.consultar(editarLibroDTO.getIdiomaId()).orElseThrow());
        libro.setEditorial(editorialService.consultar(editarLibroDTO.getEditorialId()).orElseThrow());
        return ResponseEntity.ok(libroService.reemplazar(id, libro));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable long id){
        inicializar();
        return libroService.eliminar(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
