package com.dwigs.biblioteca.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class ControllerUtils {

    @ModelAttribute("requestURI")
    String getRequestURI(HttpServletRequest request) {
        return request.getRequestURI();
    }

    @ModelAttribute("nombreUsuario")
    String getNombreUsuario( @AuthenticationPrincipal User usuario) {
        if (usuario == null) {
            return "Visitante";
        }
        // TODO, obtener nombre y apellido del perfil
        return "Autenticado";
    }

}
