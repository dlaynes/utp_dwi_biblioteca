package com.dwigs.biblioteca.model;

import com.dwigs.biblioteca.model.converter.GeneroLiterarioAttributeConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="libro")
public class Libro  {
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

    @ManyToOne()
    @JoinColumn(name="autor_id", nullable = false)
    private Autor autor;

    @Column(name="ibsm", nullable = false)
    private String ibsm;

    @ManyToOne()
    @JoinColumn(name="editorial_id", nullable = false)
    private Editorial editorial;

    @ManyToOne()
    @JoinColumn(name="idioma_id", nullable = false)
    private Idioma idioma;

    @Column(name="imagen")
    private String imagen;

    @Column(name="nacionalidad", nullable = false)
    private String nacionalidad;

    @Column(name = "paginas")
    private int paginas;

    @Column(name="publicado_en")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime publicadoEn;

    @Column(name="genero_literario", columnDefinition ="CHAR", length = 1)
    @Convert(converter = GeneroLiterarioAttributeConverter.class)
    private GeneroLiterario generoLiterario;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "categoria_libro",
            joinColumns = @JoinColumn(name = "libro_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id", referencedColumnName = "id"))
    private Set<Categoria> categorias = new HashSet<>();

}
