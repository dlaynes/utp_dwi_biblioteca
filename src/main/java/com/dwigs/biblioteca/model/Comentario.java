package com.dwigs.biblioteca.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "comentario")
public class Comentario {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", columnDefinition = "BIGINT")
    private long id;

    @CreationTimestamp
    @Column(name="fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fechaRegistro;

    @Column(name="nombre", nullable = false)
    private String nombre;

    @Column(name="correo", length = 150, nullable = false)
    private String correo;

    @Column(name="celular", length = 30, nullable = false)
    private String celular;

    @Column(name = "mensaje", columnDefinition="TEXT")
    private String mensaje;
}
