package com.dwigs.biblioteca.controller.api.publico;

import com.dwigs.biblioteca.model.Libro;
import com.dwigs.biblioteca.repository.LibroRepository;
import com.dwigs.biblioteca.service.AutorService;
import com.dwigs.biblioteca.service.EditorialService;
import com.dwigs.biblioteca.service.IdiomaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/api/publico/libros")
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

    @GetMapping("/categoria/{id}")
    public List<Libro> porCategoria(@PathVariable String slug){
        return libroRepository.findByCategoria_Slug(slug);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Libro> consultar(@PathVariable long id){

        Optional<Libro> libro = libroRepository.findOneById(id);
        return libro.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


}
