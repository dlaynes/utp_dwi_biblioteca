package com.dwigs.biblioteca.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.dwigs.biblioteca.model.converter.GeneroAttributeConverter;
import com.dwigs.biblioteca.model.converter.TipoDocumentoAttributeConverter;
import com.dwigs.biblioteca.model.converter.EstadoCivilAttributeConverter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "perfil")
public class Perfil {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", columnDefinition = "BIGINT")
    private long id;

    @OneToOne(cascade = CascadeType.MERGE) // Cascade operations to the Address entity
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;

    @Column(name="nombres", nullable = false)
    private String nombres;

    @Column(name="apellidos", nullable = false)
    private String apellidos;

    @Column(name="telefono", length = 100, nullable = false)
    private String telefono;

    @Column(name="email_personal", length = 150, nullable = false)
    private String emailPersonal;

    @Column(name="numero_documento", length = 20, nullable = false)
    private String numeroDocumento;

    @Column(name="tipo_documento", length = 16)
    @Convert(converter = TipoDocumentoAttributeConverter.class)
    private TipoDocumento tipoDocumento;

    @Column(name="estado_civil", length = 16)
    @Convert(converter = EstadoCivilAttributeConverter.class)
    private EstadoCivil estadoCivil;

    @Column(name="genero", length = 16)
    @Convert(converter = GeneroAttributeConverter.class)
    private Genero genero;

}
