package com.dwigs.biblioteca.controller.api.cliente;

import com.dwigs.biblioteca.dto.request.prestamo.AceptarPrestamoDTO;
import com.dwigs.biblioteca.dto.request.prestamo.RecibirPrestamoDTO;
import com.dwigs.biblioteca.dto.request.prestamo.SolicitarPrestamoDTO;
import com.dwigs.biblioteca.dto.response.LibroPublicoResponseDTO;
import com.dwigs.biblioteca.dto.response.ReservaResponseDTO;
import com.dwigs.biblioteca.model.EstadoPrestamo;
import com.dwigs.biblioteca.model.Libro;
import com.dwigs.biblioteca.model.Prestamo;
import com.dwigs.biblioteca.model.Usuario;
import com.dwigs.biblioteca.repository.UsuarioRepository;
import com.dwigs.biblioteca.security.JwtUserDetails;
import com.dwigs.biblioteca.service.LibroService;
import com.dwigs.biblioteca.service.PrestamoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/api/cliente/prestamos")
public class PrestamosApiController {

    private PrestamoService prestamoService;

    // TODO: reemplazar llamadas mediante el servicio
    private UsuarioRepository usuarioRepository;

    private LibroService libroService;

    @Autowired
    public PrestamosApiController(
            PrestamoService prestamoService,
            UsuarioRepository usuarioRepository,
            LibroService libroService
    ){
        this.usuarioRepository = usuarioRepository;
        this.prestamoService = prestamoService;
        this.libroService = libroService;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_BIBLIOTECARIO', 'ROLE_ADMIN')")
    @GetMapping()
    public List<Prestamo> listar(){
        return prestamoService.listar();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_CLIENTE', 'ROLE_BIBLIOTECARIO', 'ROLE_ADMIN')")
    @GetMapping("/mis-solicitudes")
    public List<ReservaResponseDTO> listarMisPrestamos(@AuthenticationPrincipal JwtUserDetails userDetails){
        String email = userDetails.getUsername();
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow();
        return prestamoService.listarDeCliente(usuario.getId()).stream().map(it -> convertirPrestamo(it)).collect(Collectors.toList());
    }

    @GetMapping(value = "/mis-solicitudes/{id}")
    public ResponseEntity<ReservaResponseDTO> consultarMiPrestamo(@AuthenticationPrincipal JwtUserDetails userDetails, @PathVariable long id){
        String email = userDetails.getUsername();
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow();
        Prestamo obj = prestamoService.damePrestamoDeUsuario(id, usuario.getId()).orElseThrow();

        return ResponseEntity.ok(convertirPrestamo(obj));
    }

    @PutMapping(value = "/mis-solicitudes/cancelar/{id}")
    public ResponseEntity<Boolean> cancelarMiReserva(@AuthenticationPrincipal JwtUserDetails userDetails, @PathVariable long id){
        String email = userDetails.getUsername();
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow();
        Prestamo prestamo = prestamoService.damePrestamoDeUsuarioConEstado(id, usuario.getId(), EstadoPrestamo.reservado).orElseThrow();

        prestamo.setEstadoPrestamo(EstadoPrestamo.cancelado);
        prestamoService.actualizar(id, prestamo);
        return ResponseEntity.ok().body(true);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_BIBLIOTECARIO', 'ROLE_ADMIN')")
    @PutMapping(value = "/cancelar/{id}")
    public ResponseEntity<Boolean> cancelarReserva(@PathVariable long id){
        Prestamo prestamo = prestamoService.encontrarPorEstado(id, EstadoPrestamo.reservado).orElseThrow();

        prestamo.setEstadoPrestamo(EstadoPrestamo.cancelado);
        prestamoService.actualizar(id, prestamo);
        return ResponseEntity.ok().body(true);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_BIBLIOTECARIO', 'ROLE_ADMIN')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Prestamo> consultar(@PathVariable long id){

        Optional<Prestamo> obj = prestamoService.damePrestamo(id);
        return obj.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    private ReservaResponseDTO convertirPrestamo(Prestamo prestamo){
        ReservaResponseDTO reservaResponseDTO = new ReservaResponseDTO();
        reservaResponseDTO.setId(prestamo.getId());
        reservaResponseDTO.setFechaReserva(prestamo.getFechaReserva());
        reservaResponseDTO.setLibro(LibroPublicoResponseDTO.convertirDesdeLibro( prestamo.getLibro()));
        reservaResponseDTO.setEstadoPrestamo(prestamo.getEstadoPrestamo());
        reservaResponseDTO.setLugarPrestamo(prestamo.getLugarPrestamo());
        return reservaResponseDTO;
    }

    // Cualquier rol puede reservar un libro
    @PostMapping(value = "/reservar")
    public ResponseEntity<ReservaResponseDTO> reservarLibro(@RequestBody SolicitarPrestamoDTO crearDTO, @AuthenticationPrincipal JwtUserDetails userDetails){
        String email = userDetails.getUsername();
        Usuario cliente = usuarioRepository.findByEmail(email).orElseThrow();
        Libro libro = libroService.consultar(crearDTO.getLibroId()).orElseThrow();

        // TODO: evitar que solicite el mismo libro el usuario actual

        Prestamo prestamo = new Prestamo();
        prestamo.setEstadoPrestamo(EstadoPrestamo.reservado);
        prestamo.setFechaReserva(LocalDateTime.now());
        prestamo.setFechaRegistro(LocalDateTime.now());
        prestamo.setLugarPrestamo(crearDTO.getLugarPrestamo());
        prestamo.setCliente(cliente);
        prestamo.setLibro(libro);
        prestamoService.crear(prestamo);

        return ResponseEntity.created(URI.create("/api/cliente/prestamos/"+ prestamo.getId())).body(convertirPrestamo(prestamo));
    }

    @Transactional
    @PreAuthorize("hasAnyAuthority('ROLE_BIBLIOTECARIO', 'ROLE_ADMIN')")
    @PutMapping(value = "/aceptar/{id}")
    public ResponseEntity<?> aceptarReserva(@RequestBody AceptarPrestamoDTO aceptarPrestamoDTO, @AuthenticationPrincipal JwtUserDetails userDetails, @PathVariable long id){
        String email = userDetails.getUsername();
        Usuario bibliotecario = usuarioRepository.findByEmail(email).orElseThrow();
        Prestamo prestamo = prestamoService.encontrarPorEstado( id, EstadoPrestamo.reservado).orElseThrow();
        Libro libro = prestamo.getLibro();
        if(libro.getDisponibles() < 1){
            return ResponseEntity.badRequest().body("Actualmente no hay libros disponibles en el inventario");
        }

        LocalDateTime hoy = LocalDateTime.now();
        prestamo.setLugarPrestamo(aceptarPrestamoDTO.getLugarPrestamo());
        prestamo.setEntregadoPor(bibliotecario);
        prestamo.setFechaPrestamo(hoy);
        prestamo.setObservacionesEntrega(aceptarPrestamoDTO.getObservacionesEntrega());

        LocalDateTime fechaEsperadaRetorno = aceptarPrestamoDTO.getFechaEsperadaDeRetorno();
        if(fechaEsperadaRetorno == null){
            fechaEsperadaRetorno = hoy.plusDays(30);
        }
        prestamo.setFechaEsperadaRetorno(fechaEsperadaRetorno); // TODO: definir los dias por defecto en otro lugar
        prestamo.setEstadoPrestamo(EstadoPrestamo.prestado);
        prestamoService.actualizar(id, prestamo);

        libroService.agregarPrestado(libro.getId());
        return ResponseEntity.ok().body(prestamo);
    }

    @Transactional
    @PreAuthorize("hasAnyAuthority('ROLE_BIBLIOTECARIO', 'ROLE_ADMIN')")
    @PutMapping(value = "/recibir/{id}")
    public ResponseEntity<Prestamo> recibirLibro(@RequestBody RecibirPrestamoDTO recibirPrestamoDTO, @AuthenticationPrincipal JwtUserDetails userDetails, @PathVariable long id){
        String email = userDetails.getUsername();
        Usuario bibliotecario = usuarioRepository.findByEmail(email).orElseThrow();
        Prestamo prestamo = prestamoService.encontrarPorEstado( id, EstadoPrestamo.prestado).orElseThrow();

        LocalDateTime hoy = LocalDateTime.now();

        prestamo.setRecepcionadoPor(bibliotecario);
        prestamo.setFechaRetorno(hoy);
        prestamo.setObservacionesRetorno(recibirPrestamoDTO.getObservacionesRetorno());
        prestamo.setEstadoPrestamo(EstadoPrestamo.entregado);
        prestamo.setAdvertencia(recibirPrestamoDTO.isAdvertencia());
        prestamoService.actualizar(id, prestamo);

        libroService.agregarEntregado(prestamo.getLibro().getId());
        return ResponseEntity.ok().body(prestamo);
    }

    @Transactional
    @PreAuthorize("hasAnyAuthority('ROLE_BIBLIOTECARIO', 'ROLE_ADMIN')")
    @PutMapping(value = "/marcar-como-perdido/{id}")
    public ResponseEntity<Boolean> prestamoPerdido(@PathVariable long id){
        Prestamo prestamo = prestamoService.encontrarPorEstado(id, EstadoPrestamo.prestado).orElseThrow();

        prestamo.setEstadoPrestamo(EstadoPrestamo.perdido);
        prestamoService.actualizar(id, prestamo);
        libroService.agregarPerdido(prestamo.getLibro().getId());
        return ResponseEntity.ok().body(true);
    }

    // TODO: borrar préstamos??

}
