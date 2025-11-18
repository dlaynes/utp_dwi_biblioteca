package com.dwigs.biblioteca.controller.api.cliente;

import com.dwigs.biblioteca.dto.response.UsuarioResponseDTO;
import com.dwigs.biblioteca.model.Usuario;
import com.dwigs.biblioteca.repository.UsuarioRepository;
import com.dwigs.biblioteca.security.JwtUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/cliente/perfil")
public class PerfilApiController {

    private UsuarioRepository usuarioRepository;

    @Autowired
    public PerfilApiController(
            UsuarioRepository usuarioRepository
    ){
        this.usuarioRepository = usuarioRepository;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_CLIENTE', 'ROLE_BIBLIOTECARIO', 'ROLE_ADMIN')")
    @GetMapping()
    public ResponseEntity<UsuarioResponseDTO> misDatos(@AuthenticationPrincipal(errorOnInvalidType=true) JwtUserDetails details){
        String email = details.getUsername();
        Usuario obj = usuarioRepository.findByEmail(email).orElseThrow();

        UsuarioResponseDTO res = UsuarioResponseDTO.deUsuario(obj);
        return ResponseEntity.ok(res);
    }
}
