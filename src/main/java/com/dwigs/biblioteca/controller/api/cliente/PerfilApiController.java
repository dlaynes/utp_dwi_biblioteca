package com.dwigs.biblioteca.controller.api.cliente;

import com.dwigs.biblioteca.model.Usuario;
import com.dwigs.biblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController()
@RequestMapping("/api/publico/perfil")
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
    public ResponseEntity<Usuario> misDatos(@AuthenticationPrincipal User usuarioAuth){
        String email = usuarioAuth.getUsername();
        Optional<Usuario> obj = usuarioRepository.findByEmail(email);

        return obj.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Verificar si el email está siendo usado
}
