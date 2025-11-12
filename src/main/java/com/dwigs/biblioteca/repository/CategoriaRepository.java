package com.dwigs.biblioteca.repository;

import com.dwigs.biblioteca.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    Optional<Categoria> findOneById(Long categoriaId);

    List<Categoria> findAll();

    boolean existsBySlug(String categoriaSlug);

    Optional<Categoria> findOneBySlug(String categoriaSlug);
}
