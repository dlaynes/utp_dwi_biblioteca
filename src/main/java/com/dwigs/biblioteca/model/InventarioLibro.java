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
@Table(name="inventario_libro")
public class InventarioLibro {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", columnDefinition = "BIGINT")
    private long id;

    @CreationTimestamp
    @Column(name="fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fechaRegistro;

    @ManyToOne()
    @JoinColumn(name="local_id", nullable = false)
    private Local local;

    @ManyToOne()
    @JoinColumn(name="libro_id", nullable = false)
    private Libro libro;

    @Column(name="disponibles")
    private long disponibles;

    @Column(name="reservados")
    private long reservados;

    @Column(name="prestados")
    private long prestados;

    @Column(name="perdidos")
    private long perdidos;

    private long existentes(){
        return disponibles + prestados;
    }
}
