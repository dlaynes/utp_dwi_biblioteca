package com.dwigs.biblioteca.repository;

import com.dwigs.biblioteca.model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    Optional<Comentario> findOneById(Long editorialId);

    List<Comentario> findAll();
}