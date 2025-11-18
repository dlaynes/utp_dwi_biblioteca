package com.dwigs.biblioteca.controller.api.publico;

import com.dwigs.biblioteca.dto.response.LibroResponseDTO;
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
import java.util.stream.Collectors;

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

    private List<LibroResponseDTO> convertirADTO(List<Libro> libros){
        return libros.stream().map(libro -> LibroResponseDTO.convertirDesdeLibro(libro)).collect(Collectors.toList());
    }

    @GetMapping()
    public List<LibroResponseDTO> listar(){
        return convertirADTO( libroRepository.findAll());
    }

    @GetMapping("/categoria/{slug}")
    public List<LibroResponseDTO> porCategoria(@PathVariable String slug){
        return convertirADTO(libroRepository.findByCategoria_Slug(slug));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<LibroResponseDTO> consultar(@PathVariable long id){

        Libro libro = libroRepository.findOneById(id).orElseThrow();
        LibroResponseDTO libroResponseDTO = LibroResponseDTO.convertirDesdeLibro(libro);

        return ResponseEntity.ok(libroResponseDTO);
    }


}
