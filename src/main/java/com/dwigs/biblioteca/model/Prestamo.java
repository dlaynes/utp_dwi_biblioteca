package com.dwigs.biblioteca.model;

import com.dwigs.biblioteca.model.converter.EstadoPrestamoAttributeConverter;
import com.dwigs.biblioteca.model.converter.LugarPrestamoAtributeConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "prestamo")
public class Prestamo {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", columnDefinition = "BIGINT")
    private long id;

    @Column(name="estado_prestamo", length = 16)
    @Convert(converter = EstadoPrestamoAttributeConverter.class)
    private EstadoPrestamo estadoPrestamo;

    @Column(name="lugar_prestamo", length = 16)
    @Convert(converter = LugarPrestamoAtributeConverter.class)
    private LugarPrestamo lugarPrestamo;

    @CreationTimestamp
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fechaRegistro;

    @ManyToOne()
    @JoinColumn(name="inventario_id", nullable = false)
    private InventarioLibro inventarioLibro;

    // Persona que solicita el libro
    @ManyToOne()
    @JoinColumn(name="cliente_id", nullable = false)
    private Usuario cliente;

    // Persona que entrega el libro
    @ManyToOne()
    @JoinColumn(name="entregado_por_id")
    private Usuario entregadoPor;

    // Persona que recupera el libro
    @ManyToOne()
    @JoinColumn(name="recepcionado_por_id")
    private Usuario recepcionadoPor;

    @Column(name = "fecha_reserva")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fechaReserva;

    @Column(name = "fecha_prestamo")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fechaPrestamo;

    @Column(name = "fecha_esperada_retorno")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fechaEsperadaRetorno;

    @Column(name = "fecha_retorno")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fechaRetorno;

    @Column(name = "observaciones_entrega", columnDefinition="TEXT")
    private String observacionesEntrega;

    @Column(name = "observaciones_retorno", columnDefinition="TEXT")
    private String observacionesRetorno;

    @Column(name="advertencia")
    private boolean advertencia = false;

}
