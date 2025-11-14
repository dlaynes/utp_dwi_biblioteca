package com.dwigs.biblioteca.model;

import com.dwigs.biblioteca.model.converter.TipoEventoAttributeConverter;
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
public class Evento {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", columnDefinition = "BIGINT")
    private long id;

    @CreationTimestamp
    @Column(name="fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fechaRegistro;

    @Column(name="titulo", nullable = false)
    private String titulo;

    @Column(name="fecha_evento")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fechaEvento;

    @Column(name="tipo_evento", columnDefinition ="CHAR",  length = 1)
    @Convert(converter = TipoEventoAttributeConverter.class)
    private TipoEvento tipoEvento;

    public Evento(String titulo, LocalDateTime fechaRegistro, LocalDateTime fechaEvento, TipoEvento tipoEvento){
        this.titulo = titulo;
        this.fechaRegistro = fechaRegistro;
        this.fechaEvento = fechaEvento;
        this.tipoEvento = tipoEvento;
    }

}
