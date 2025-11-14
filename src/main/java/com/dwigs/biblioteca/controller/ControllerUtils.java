package com.dwigs.biblioteca.controller;

import com.dwigs.biblioteca.security.JwtUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class ControllerUtils {

    @ModelAttribute("requestURI")
    String getRequestURI(HttpServletRequest request) {
        return request.getRequestURI();
    }

    @ModelAttribute("nombreUsuario")
    String getNombreUsuario(@AuthenticationPrincipal JwtUserDetails userDetails) {
        if (userDetails == null) {
            return "Visitante";
        }
        // TODO, obtener nombre y apellido del perfil
        return "Autenticado";
    }

}
