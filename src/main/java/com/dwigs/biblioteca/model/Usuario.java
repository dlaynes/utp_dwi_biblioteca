package com.dwigs.biblioteca.model;

import com.dwigs.biblioteca.model.converter.EstadoCivilAttributeConverter;
import com.dwigs.biblioteca.model.converter.EstadoUsuarioAttributeConverter;
import com.dwigs.biblioteca.model.converter.GeneroAttributeConverter;
import com.dwigs.biblioteca.model.converter.TipoDocumentoAttributeConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT")
    private long id;

    @Column(name="email", length = 150, nullable = false)
    private String email;

    @JsonIgnore
    @Column(name="password")
    private String password;

    @CreationTimestamp
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fechaRegistro;

    @Column(name = "ultimo_login")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime ultimoLogin;

    @Column(name="estado", columnDefinition ="CHAR", length = 1)
    @Convert(converter = EstadoUsuarioAttributeConverter.class)
    private EstadoUsuario estadoUsuario;

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

    @Column(name="tipo_documento", columnDefinition ="CHAR", length = 1)
    @Convert(converter = TipoDocumentoAttributeConverter.class)
    private TipoDocumento tipoDocumento;

    @Column(name="estado_civil", columnDefinition ="CHAR", length = 1)
    @Convert(converter = EstadoCivilAttributeConverter.class)
    private EstadoCivil estadoCivil;

    @Column(name="genero", columnDefinition ="CHAR", length = 1)
    @Convert(converter = GeneroAttributeConverter.class)
    private Genero genero;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_rol",
            joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id", referencedColumnName = "id"))
    private Set<Rol> roles = new HashSet<>();

}
