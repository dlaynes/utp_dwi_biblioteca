package com.dwigs.biblioteca.model;

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
@Table(name = "idioma")
public class Idioma {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", columnDefinition = "BIGINT")
    private long id;

    @CreationTimestamp
    @Column(name="fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fechaRegistro;

    @Column(name="nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name="codigo", nullable = false, length = 10)
    private String codigo;

    public Idioma( LocalDateTime dateTime, String nombre, String codigo){
        this.fechaRegistro = dateTime;
        this.nombre = nombre;
        this.codigo = codigo;
    }

}
