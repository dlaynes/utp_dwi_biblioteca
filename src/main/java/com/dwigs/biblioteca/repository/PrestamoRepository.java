package com.dwigs.biblioteca.repository;

import com.dwigs.biblioteca.model.EstadoPrestamo;
import com.dwigs.biblioteca.model.Libro;
import com.dwigs.biblioteca.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    Optional<Prestamo> findOneById(Long libroId);

    Optional<Prestamo> findOneByIdAndClienteId(Long libroId, Long clienteId);

    Optional<Prestamo> findOneByIdAndEstadoPrestamo(Long id, EstadoPrestamo estadoPrestamo);

    Optional<Prestamo> findOneByIdAndClienteIdAndEstadoPrestamo(Long id, Long usuarioId, EstadoPrestamo estadoPrestamo);

    List<Prestamo> findByClienteId(Long clienteId);

    List<Prestamo> findAll();

    Long countByClienteId(Long clienteId);

    Long countByLibroAndEstadoPrestamo(Libro libro, EstadoPrestamo estadoPrestamo);

}
