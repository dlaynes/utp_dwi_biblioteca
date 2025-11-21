package com.dwigs.biblioteca.controller.api.publico;

import com.dwigs.biblioteca.dto.response.LibroResponseDTO;
import com.dwigs.biblioteca.model.Libro;
import com.dwigs.biblioteca.repository.LibroRepository;
import com.dwigs.biblioteca.service.AutorService;
import com.dwigs.biblioteca.service.EditorialService;
import com.dwigs.biblioteca.service.IdiomaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @GetMapping("/busqueda")
    public ResponseEntity<Map<String, Object>> buscar(
        @RequestParam(required = false) String titulo,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ){
        Pageable paging = PageRequest.of(page, size);
        Page<Libro> resultadosPaginados;
        if (titulo == null)
            resultadosPaginados = libroRepository.findAll(paging);
        else
            resultadosPaginados = libroRepository.findByTituloContaining(titulo, paging);

        Map<String, Object> response = new HashMap<>();
        response.put("results", resultadosPaginados.getContent());
        response.put("currentPage", resultadosPaginados.getNumber());
        response.put("totalItems", resultadosPaginados.getTotalElements());
        response.put("totalPages", resultadosPaginados.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/categoria/{slug}")
    public List<LibroResponseDTO> porCategoria(@PathVariable String slug){
        return convertirADTO(libroRepository.findByCategoriaSlug(slug));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<LibroResponseDTO> consultar(@PathVariable long id){

        Libro libro = libroRepository.findOneById(id).orElseThrow();
        LibroResponseDTO libroResponseDTO = LibroResponseDTO.convertirDesdeLibro(libro);

        return ResponseEntity.ok(libroResponseDTO);
    }


}
