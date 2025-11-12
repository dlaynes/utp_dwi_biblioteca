package com.dwigs.biblioteca.controller.api.publico;

import com.dwigs.biblioteca.dto.request.ActualizarCategoriaDTO;
import com.dwigs.biblioteca.model.Categoria;
import com.dwigs.biblioteca.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/api/publico/categorias")
public class CategoriasApiController {

    private CategoriaRepository categoriaRepository;

    @Autowired
    public CategoriasApiController(
            CategoriaRepository categoriaRepository
    ){
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping()
    public List<Categoria> listar(){
        return categoriaRepository.findAll();
    }

    @GetMapping(value = "/slug/{slug}")
    public ResponseEntity<Categoria> consultarPorSlug(@PathVariable String slug){

        Optional<Categoria> obj = categoriaRepository.findOneBySlug(slug);
        return obj.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Categoria> consultar(@PathVariable long id){

        Optional<Categoria> obj = categoriaRepository.findOneById(id);
        return obj.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyAuthority('ROLE_BIBLIOTECARIO', 'ROLE_ADMIN')")
    @PostMapping()
    public ResponseEntity<Categoria> crear(@RequestBody ActualizarCategoriaDTO crearDTO){

        // TODO: mejores mensajes de error
        // Ya existe una categoría con esa etiqueta
        if(categoriaRepository.existsBySlug(crearDTO.getSlug())){
            return ResponseEntity.unprocessableEntity().build();
        }

        Categoria categoria = new Categoria();

        // La categoría padre no existe
        Long categoriaPadreId = crearDTO.getCategoriaPadre_id();
        if(categoriaPadreId != null){
            Optional<Categoria> categoriaPadre = categoriaRepository.findOneById(categoriaPadreId);
            if(categoriaPadre.isEmpty()){
                return ResponseEntity.unprocessableEntity().build();
            }
            categoria.setCategoriaPadre(categoriaPadre.get());
        }
        categoria.setNombre(crearDTO.getNombre());
        categoria.setSlug(crearDTO.getSlug());

        Categoria nuevo = categoriaRepository.save(categoria);
        return ResponseEntity.created(URI.create("/api/publico/categorias/"+ categoria.getId())).body(nuevo);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_BIBLIOTECARIO', 'ROLE_ADMIN')")
    @PutMapping(value="/{id}")
    public ResponseEntity<Categoria> reemplazar(@PathVariable long id, @RequestBody ActualizarCategoriaDTO editarDTO){

        Optional<Categoria> categoriaOptional = categoriaRepository.findOneById(id);
        if(categoriaOptional.isEmpty()){
            return ResponseEntity.unprocessableEntity().build();
        }
        Categoria categoria = categoriaOptional.get();

        // TODO: mejores mensajes de error
        // Ya existe una categoría con esa etiqueta
        String slug = editarDTO.getSlug();
        if(!categoria.getSlug().equals(slug) && categoriaRepository.existsBySlug(slug)){
            return ResponseEntity.unprocessableEntity().build();
        }
        // La categoría padre no existe
        Long categoriaPadreId = editarDTO.getCategoriaPadre_id();
        if(categoriaPadreId != null){
            Optional<Categoria> categoriaPadre = categoriaRepository.findOneById(categoriaPadreId);
            if(categoriaPadre.isEmpty()){
                return ResponseEntity.unprocessableEntity().build();
            }
            // Evitamos referencias cíclicas de primer nivel
            // TODO: evitar referencias ciclicas en todo el arbol
            if(categoria.getId() == categoriaPadre.get().getCategoriaPadre().getId()){
                return ResponseEntity.unprocessableEntity().build();
            }
            categoria.setCategoriaPadre(categoriaPadre.get());
        }
        categoria.setNombre(editarDTO.getNombre());
        categoria.setSlug(editarDTO.getSlug());

        // TODO: categorias

        return ResponseEntity.ok(categoriaRepository.save(categoria));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_BIBLIOTECARIO', 'ROLE_ADMIN')")
    @DeleteMapping(value="/{id}", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> eliminar(@PathVariable long id){
        categoriaRepository.deleteById(id);
        return ResponseEntity.noContent().build(); //ResponseEntity.notFound().build();
    }

}
