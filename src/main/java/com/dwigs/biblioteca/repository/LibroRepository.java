package com.dwigs.biblioteca.repository;

import com.dwigs.biblioteca.model.Libro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    Optional<Libro> findOneById(Long libroId);

    List<Libro> findAll();

    Page<Libro> findByTituloContaining(String nombre, Pageable pageable);

    Page<Libro> findAll(Pageable pageable);

    @NativeQuery("SELECT l.* FROM libro l" +
            " LEFT JOIN categoria_libro AS cl ON cl.libro_id=l.id" +
            " LEFT JOIN categoria c ON c.id=cl.categoria_id " +
            " WHERE c.slug=:categoriaSlug")
    List<Libro> findByCategoriaSlug(String categoriaSlug);
}