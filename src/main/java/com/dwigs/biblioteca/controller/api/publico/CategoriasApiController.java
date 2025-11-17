package com.dwigs.biblioteca.controller.api.publico;

import com.dwigs.biblioteca.model.Categoria;
import com.dwigs.biblioteca.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
