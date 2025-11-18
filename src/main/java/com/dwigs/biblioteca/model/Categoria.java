package com.dwigs.biblioteca.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "categoria")
public class Categoria {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", columnDefinition = "BIGINT")
    private long id;

    @CreationTimestamp
    @Column(name="fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fechaRegistro;

    @Column(name="slug", nullable = false, unique = true)
    private String slug;

    @Column(name="nombre", nullable = false)
    private String nombre;

    @Column(name="imagen")
    private String imagen;

    @Column(name="color")
    private String color;

    @Column(name="icono")
    private String icono;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_padre_id")
    private Categoria categoriaPadre;

    @JsonIgnore
    @OneToMany(mappedBy = "categoriaPadre")
    private Set<Categoria> subCategorias = new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "categorias")
    private Set<Libro> libros = new HashSet<>();

    public Categoria(String slug, String nombre, LocalDateTime fechaRegistro, String color, String icono){
        this.slug = slug;
        this.nombre = nombre;
        this.fechaRegistro = fechaRegistro;
        this.color = color;
        this.icono = icono;
    }

}
