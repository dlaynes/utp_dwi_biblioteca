package com.dwigs.biblioteca.repository;

import com.dwigs.biblioteca.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findOneById(Long autorId);

    List<Autor> findAll();
}
