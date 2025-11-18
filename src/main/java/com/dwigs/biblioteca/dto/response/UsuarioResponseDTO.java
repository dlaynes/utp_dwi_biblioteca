package com.dwigs.biblioteca.dto.response;

import com.dwigs.biblioteca.model.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UsuarioResponseDTO {

    private Long id;

    private String nombres;

    private String apellidos;

    private String email;

    private String telefono;

    private String emailPersonal;

    private TipoDocumento tipoDocumento;

    private String numeroDocumento;

    private EstadoCivil estadoCivil;

    private Genero genero;

    private EstadoUsuario estadoUsuario;

    private LocalDateTime ultimoLogin;

    private Set<String> rolKeys = new HashSet<>();

    public static UsuarioResponseDTO deUsuario(Usuario usuario){
        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO();
        usuarioResponseDTO.setId(usuario.getId());
        usuarioResponseDTO.setNombres(usuario.getNombres());
        usuarioResponseDTO.setApellidos(usuario.getApellidos());
        usuarioResponseDTO.setEmail(usuario.getEmail());
        usuarioResponseDTO.setTelefono(usuario.getTelefono());
        usuarioResponseDTO.setGenero(usuario.getGenero());
        usuarioResponseDTO.setEmailPersonal(usuario.getEmailPersonal());
        usuarioResponseDTO.setEstadoUsuario(usuario.getEstadoUsuario());
        usuarioResponseDTO.setTipoDocumento(usuario.getTipoDocumento());
        usuarioResponseDTO.setNumeroDocumento(usuario.getNumeroDocumento());
        usuarioResponseDTO.setEstadoCivil(usuario.getEstadoCivil());
        usuarioResponseDTO.setUltimoLogin(usuario.getUltimoLogin());

        return usuarioResponseDTO;
    }

}
