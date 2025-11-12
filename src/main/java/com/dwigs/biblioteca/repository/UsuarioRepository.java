package com.dwigs.biblioteca.repository;

import com.dwigs.biblioteca.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Boolean existsByEmail(String email);

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findOneById(Long usuarioId);

    boolean existsById(Long usuarioId);

    List<Usuario> findAll();
}

