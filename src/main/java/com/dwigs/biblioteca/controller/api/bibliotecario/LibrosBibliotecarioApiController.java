package com.dwigs.biblioteca.controller.api.bibliotecario;

import com.dwigs.biblioteca.dto.request.ActualizarLibroDTO;
import com.dwigs.biblioteca.model.Categoria;
import com.dwigs.biblioteca.model.Libro;
import com.dwigs.biblioteca.repository.CategoriaRepository;
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
import java.util.Set;

@RestController()
@RequestMapping("/api/bibliotecario/libros")
public class LibrosBibliotecarioApiController {

    private LibroRepository libroRepository;

    private AutorService autorService;

    private EditorialService editorialService;

    private IdiomaService idiomaService;

    private CategoriaRepository categoriaRepository;

    @Autowired
    public LibrosBibliotecarioApiController(
            LibroRepository libroRepository,
            AutorService autorService,
            EditorialService editorialService,
            IdiomaService idiomaService,
            CategoriaRepository categoriaRepository
    ){
        this.libroRepository = libroRepository;
        this.autorService = autorService;
        this.editorialService = editorialService;
        this.idiomaService = idiomaService;
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping()
    public List<Libro> listar(){
        return libroRepository.findAll();
    }

    @GetMapping("/categoria/{id}")
    public List<Libro> porCategoria(@PathVariable String slug){
        return libroRepository.findByCategoriaSlug(slug);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Libro> consultar(@PathVariable long id){

        Optional<Libro> libro = libroRepository.findOneById(id);
        return libro.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Libro> crear(@RequestBody ActualizarLibroDTO crearLibroDTO){

        Libro libro = crearLibroDTO.obtenerLibro();

        libro.setAutor(autorService.consultar(crearLibroDTO.getAutorId()).orElseThrow());
        libro.setIdioma(idiomaService.consultar(crearLibroDTO.getIdiomaId()).orElseThrow());
        libro.setEditorial(editorialService.consultar(crearLibroDTO.getEditorialId()).orElseThrow());
        libro.setDisponibles(crearLibroDTO.getDisponibles());
        libro.setPrestados(0L);
        libro.setPerdidos(0L);
        libro.setReservados(0L);

        Set<Long> categoriaIds = crearLibroDTO.getCategoriaIds();
        libro.setCategorias(categoriaRepository.findByIdIn(categoriaIds));

        Libro libroCreado = libroRepository.save(libro);
        return ResponseEntity.created(URI.create("/api/publico/libros/"+ libro.getId())).body(libroCreado);
    }

    @PutMapping(value="/{id}", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Libro> reemplazar(@PathVariable long id, @RequestBody ActualizarLibroDTO editarLibroDTO){

        Libro libroAnt = libroRepository.findOneById(id).orElseThrow();

        Long nuevoDisponibles = editarLibroDTO.getDisponibles();
        // TODO: mensaje de error, los libros disponibles no pueden ser menores a los libros prestados
        Long prestados = libroAnt.getPrestados();
        if(prestados != null && nuevoDisponibles < libroAnt.getPrestados()){
            return ResponseEntity.unprocessableEntity().build();
        }
        libroAnt.setDisponibles(nuevoDisponibles);
        libroAnt.setGeneroLiterario(editarLibroDTO.getGeneroLiterario());
        libroAnt.setIbsm(editarLibroDTO.getIbsm());
        libroAnt.setTitulo(editarLibroDTO.getTitulo());
        libroAnt.setNacionalidad(editarLibroDTO.getNacionalidad());
        libroAnt.setImagen(editarLibroDTO.getImagen());
        libroAnt.setPublicadoEn(editarLibroDTO.getPublicadoEn());
        libroAnt.setPaginas(editarLibroDTO.getPaginas());
        libroAnt.setAutor(autorService.consultar(editarLibroDTO.getAutorId()).orElseThrow());
        libroAnt.setIdioma(idiomaService.consultar(editarLibroDTO.getIdiomaId()).orElseThrow());
        libroAnt.setEditorial(editorialService.consultar(editarLibroDTO.getEditorialId()).orElseThrow());

        Set<Long> categoriaIds = editarLibroDTO.getCategoriaIds();
        Set<Categoria> categorias = categoriaRepository.findByIdIn(categoriaIds);
        libroAnt.setCategorias(categorias);

        return ResponseEntity.ok(libroRepository.save(libroAnt));
    }

    @DeleteMapping(value="/{id}", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> eliminar(@PathVariable long id){
        libroRepository.deleteById(id);
        return ResponseEntity.noContent().build(); //ResponseEntity.notFound().build();
    }
}
