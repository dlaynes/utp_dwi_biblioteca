package com.dwigs.biblioteca.controller;

import com.dwigs.biblioteca.model.Usuario;
import com.dwigs.biblioteca.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class ControllerUtils {

    @Autowired
    UsuarioService usuarioService;

    @ModelAttribute("requestURI")
    String getRequestURI(HttpServletRequest request) {
        return request.getRequestURI();
    }

    @ModelAttribute("nombreUsuario")
    String getNombreUsuario( @AuthenticationPrincipal Usuario usuario) {
        if (usuario == null) {
            return "Visitante";
        }
        // TODO, obtener nombre y apellido del perfil
        return usuario.getUsername();
    }

}
