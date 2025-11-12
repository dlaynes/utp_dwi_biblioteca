package com.dwigs.biblioteca.service;

import com.dwigs.biblioteca.model.EstadoPrestamo;
import com.dwigs.biblioteca.model.Prestamo;
import com.dwigs.biblioteca.repository.PrestamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrestamoService {

    private final PrestamoRepository repository;

    @Autowired
    public PrestamoService(PrestamoRepository repository){
        this.repository = repository;
    }

    public List<Prestamo> listar(){
        return repository.findAll();
    }

    public List<Prestamo> listarDeCliente(Long clienteId){
        return repository.findByClienteId(clienteId);
    }

    public Long contar() { return repository.count(); }

    public Optional<Prestamo> damePrestamo(Long id){
        return this.repository.findOneById(id);
    }

    public Optional<Prestamo> damePrestamoDeUsuario(Long id, Long usuarioId){
        return this.repository.findOneByIdAndClienteId(id, usuarioId);
    }

    public Optional<Prestamo> damePrestamoDeUsuarioConEstado(Long id, Long usuarioId, EstadoPrestamo estadoPrestamo){
        return this.repository.findOneByIdAndClienteIdAndEstadoPrestamo(id, usuarioId, estadoPrestamo);
    }

    public Optional<Prestamo> encontrarPorEstado(Long id, EstadoPrestamo estadoPrestamo){
        return this.repository.findOneByIdAndEstadoPrestamo(id, estadoPrestamo);
    }

    public Prestamo crear(Prestamo obj) { return repository.save(obj); }

    public Prestamo actualizar(long id, Prestamo obj) { return repository.save(obj); }

}
