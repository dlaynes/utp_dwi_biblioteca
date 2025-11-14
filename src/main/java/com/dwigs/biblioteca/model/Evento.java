package com.dwigs.biblioteca.model;

import com.dwigs.biblioteca.model.converter.TipoEventoAttributeConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
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

    @Column(name="nombre", nullable = false)
    private String titulo;

    @Column(name="fecha_eventoo")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fechaEvento;

    @Column(name="tipo_evento", length = 16)
    @Convert(converter = TipoEventoAttributeConverter.class)
    private TipoEvento tipoEvento;

}
