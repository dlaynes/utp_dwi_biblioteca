package com.dwigs.biblioteca.repository;

import com.dwigs.biblioteca.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByNombre(String rol);

    List<Rol> findAll();

    Set<Rol> findByIdIn(Set<Long> rolIds);
}
