package com.dwigs.biblioteca.controller;

import com.dwigs.biblioteca.dto.request.LoginRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/auth")
@Controller
public class AuthController {

    @GetMapping("/login")
    public String loginPage(Model model, @RequestParam(required = false) String register, @RequestParam(required = false) String error, @RequestParam(required = false) String logout) {
        if(register != null){
            model.addAttribute("successText", "Su cuenta ha sido creada satisfactoriamente. Ya puede ingresar al sistema");
        }
        if(error != null){
            model.addAttribute("loginError", "Error");
        }
        if(logout != null){
            model.addAttribute("logoutText", "Sesión cerrada");
        }
        model.addAttribute("login", new LoginRequest());
        return "auth/login";
    }

}
