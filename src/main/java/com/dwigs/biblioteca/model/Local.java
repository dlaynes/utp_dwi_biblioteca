package com.dwigs.biblioteca.model;

import com.dwigs.biblioteca.validator.coordinates.CoordinatesConstraint;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/*
* Para simplificar las cosas, para este proyecto cada local tiene un solo lugar de lectura, y un solo depósito de libros
* */

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="local")
public class Local {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", columnDefinition = "BIGINT")
    private long id;

    @CreationTimestamp
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fechaRegistro;

    @Column(name="nombre", length = 150, nullable = false)
    private String nombre;

    @Column(name = "ubicacion", columnDefinition="TEXT")
    private String ubicacion;

    @Column(name="email", length = 150, nullable = false)
    private String email;

    @Column(name="telefono", length = 100, nullable = false)
    private String telefono;

    @Column(name="ciudad", length = 100, nullable = false)
    private String ciudad;

    @Column(name="pais", length = 100, nullable = false)
    private String pais;

    @CoordinatesConstraint
    @Column(name = "latitud", precision = 11, scale=8)
    private BigDecimal latitud;

    @CoordinatesConstraint
    @Column(name = "longitud", precision = 11, scale=8)
    private BigDecimal longitud;

}
