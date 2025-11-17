package com.dwigs.biblioteca.controller.api.publico;

import com.dwigs.biblioteca.model.InventarioLibro;
import com.dwigs.biblioteca.repository.InventarioLibroRepository;
import com.dwigs.biblioteca.repository.LibroRepository;
import com.dwigs.biblioteca.repository.LocalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/de-libro/{libroId}")
    public List<InventarioLibro> listarDeLibro(@PathVariable Long libroId){
        return inventarioLibroRepository.findByLibroId(libroId);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<InventarioLibro> consultar(@PathVariable long id){

        Optional<InventarioLibro> obj = inventarioLibroRepository.findOneById(id);
        return obj.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
