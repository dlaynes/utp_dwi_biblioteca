package com.dwigs.biblioteca.repository;

import com.dwigs.biblioteca.model.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocalRepository extends JpaRepository<Local, Long> {
    Optional<Local> findOneById(Long localId);

    List<Local> findAll();
}
