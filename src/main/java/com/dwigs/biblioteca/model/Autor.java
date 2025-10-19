package com.dwigs.biblioteca.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name="autor")
@Getter
@Setter
@NoArgsConstructor
public class Autor {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", columnDefinition = "BIGINT")
    private long id;

    @CreationTimestamp
    @Column(name="fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fechaRegistro;

    @Column(name="nombres", length = 150, nullable = false)
    private String nombres;

    @Column(name="apellidos", length = 150, nullable = false)
    private String apellidos;

    @Column(name="nacionalidad", length = 30, nullable = false)
    private String nacionalidad;

    @Column(name = "comentarios", columnDefinition="TEXT")
    private String comentarios;

}
