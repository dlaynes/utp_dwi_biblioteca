package com.dwigs.biblioteca.repository;

import com.dwigs.biblioteca.model.Editorial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EditorialRepository extends JpaRepository<Editorial, Long> {
    Optional<Editorial> findOneById(Long editorialId);
}
