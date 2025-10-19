package com.dwigs.biblioteca.repository;

import com.dwigs.biblioteca.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    Optional<Prestamo> findOneById(Long libroId);
}
