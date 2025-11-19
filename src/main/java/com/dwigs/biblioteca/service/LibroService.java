package com.dwigs.biblioteca.service;

import com.dwigs.biblioteca.model.EstadoPrestamo;
import com.dwigs.biblioteca.model.Libro;
import com.dwigs.biblioteca.repository.LibroRepository;
import com.dwigs.biblioteca.repository.PrestamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibroService {

    private final PrestamoRepository prestamoRepository;

    private final LibroRepository repository;

    @Autowired
    public LibroService(LibroRepository repository, PrestamoRepository prestamoRepository){
        this.repository = repository;
        this.prestamoRepository = prestamoRepository;
    }

    public List<Libro> listar(){
        return repository.findAll();
    }

    public Long contarLibros() { return repository.count(); }

    public Libro crear(Libro libro) { return repository.save(libro); }

    public Optional<Libro> consultar(Long id) { return repository.findOneById(id); }

    public Libro actualizar(long id, Libro libro) { return repository.save(libro); }

    public boolean borrar(long id) {
        repository.deleteById(id);
        return true;
    }

    // O usar joins
    public void agregarReserva(Long inventarioLibroId){
        Libro libro = repository.findOneById(inventarioLibroId).orElseThrow();
        Long reservados = prestamoRepository.countByLibroAndEstadoPrestamo(libro, EstadoPrestamo.reservado);
        libro.setReservados(reservados);
        repository.save(libro);
    }

    public void agregarEntregado(Long inventarioLibroId){
        Libro libro = repository.findOneById(inventarioLibroId).orElseThrow();
        Long prestados = prestamoRepository.countByLibroAndEstadoPrestamo(libro, EstadoPrestamo.prestado);

        libro.setDisponibles(libro.getDisponibles() + 1);
        libro.setPrestados(prestados);
        repository.save(libro);
    }

    public void agregarPerdido(Long inventarioLibroId){
        Libro libro = repository.findOneById(inventarioLibroId).orElseThrow();
        Long perdidos = prestamoRepository.countByLibroAndEstadoPrestamo(libro, EstadoPrestamo.perdido);
        Long prestados = prestamoRepository.countByLibroAndEstadoPrestamo(libro, EstadoPrestamo.prestado);

        libro.setPerdidos(perdidos);
        libro.setPrestados(prestados);
        repository.save(libro);
    }

    public void agregarPrestado(Long inventarioLibroId){
        Libro libro = repository.findOneById(inventarioLibroId).orElseThrow();
        Long prestados = prestamoRepository.countByLibroAndEstadoPrestamo(libro, EstadoPrestamo.prestado);

        libro.setDisponibles(libro.getDisponibles() -1);
        libro.setPrestados(prestados);
        repository.save(libro);
    }


}
