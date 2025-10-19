package com.dwigs.biblioteca.repository;

import com.dwigs.biblioteca.model.Idioma;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IdiomaRepository extends JpaRepository<Idioma, Long> {
    Optional<Idioma> findOneById(Long idiomaId);
}
