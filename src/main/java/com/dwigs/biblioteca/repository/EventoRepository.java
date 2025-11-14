package com.dwigs.biblioteca.repository;

import com.dwigs.biblioteca.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface EventoRepository extends JpaRepository<Evento, Long> {
    Optional<Evento> findOneById(Long libroId);

    List<Evento> findAll();
}


