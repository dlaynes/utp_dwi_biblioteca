package com.dwigs.biblioteca.service;

import com.dwigs.biblioteca.model.EstadoPrestamo;
import com.dwigs.biblioteca.model.InventarioLibro;
import com.dwigs.biblioteca.repository.InventarioLibroRepository;
import com.dwigs.biblioteca.repository.PrestamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventarioLibroService {

    PrestamoRepository prestamoRepository;
    InventarioLibroRepository inventarioLibroRepository;

    @Autowired
    public InventarioLibroService(
            PrestamoRepository prestamoRepository,
            InventarioLibroRepository inventarioLibroRepository
    ){
        this.prestamoRepository = prestamoRepository;
        this.inventarioLibroRepository = inventarioLibroRepository;
    }

    public void agregarEntregado(Long inventarioLibroId){
        InventarioLibro inventarioLibro = inventarioLibroRepository.findOneById(inventarioLibroId).orElseThrow();
        Long prestados = prestamoRepository.countByInventarioLibroAndEstadoPrestamo(inventarioLibro, EstadoPrestamo.prestado);

        inventarioLibro.setDisponibles(inventarioLibro.getDisponibles() + 1);
        inventarioLibro.setPrestados(prestados);
        inventarioLibroRepository.save(inventarioLibro);
    }

    public void agregarPerdido(Long inventarioLibroId){
        InventarioLibro inventarioLibro = inventarioLibroRepository.findOneById(inventarioLibroId).orElseThrow();
        Long perdidos = prestamoRepository.countByInventarioLibroAndEstadoPrestamo(inventarioLibro, EstadoPrestamo.perdido);
        Long prestados = prestamoRepository.countByInventarioLibroAndEstadoPrestamo(inventarioLibro, EstadoPrestamo.prestado);

        inventarioLibro.setPerdidos(perdidos);
        inventarioLibro.setPrestados(prestados);
        inventarioLibroRepository.save(inventarioLibro);
    }

    public void agregarPrestado(Long inventarioLibroId){
        InventarioLibro inventarioLibro = inventarioLibroRepository.findOneById(inventarioLibroId).orElseThrow();
        Long prestados = prestamoRepository.countByInventarioLibroAndEstadoPrestamo(inventarioLibro, EstadoPrestamo.prestado);

        inventarioLibro.setDisponibles(inventarioLibro.getDisponibles() -1);
        inventarioLibro.setPrestados(prestados);
        inventarioLibroRepository.save(inventarioLibro);
    }

}
