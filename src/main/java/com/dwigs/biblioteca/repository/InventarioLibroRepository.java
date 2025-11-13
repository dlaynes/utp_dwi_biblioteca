package com.dwigs.biblioteca.repository;

import com.dwigs.biblioteca.model.InventarioLibro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventarioLibroRepository extends JpaRepository<InventarioLibro, Long> {
    Optional<InventarioLibro> findOneById(Long libroId);

    List<InventarioLibro> findAll();

    List<InventarioLibro> findByLibroId(Long libroId);

}
