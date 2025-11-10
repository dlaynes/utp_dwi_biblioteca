package com.dwigs.biblioteca.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class IndexController {

    @GetMapping("/sumar")
    public String sumar(@RequestParam long op1, @RequestParam long op2){
        return String.valueOf(op1 + op2);
    }

    @GetMapping("/debug/token")
    public String debugToken() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("Authentication: " + auth);
        System.out.println("Authorities: " + auth.getAuthorities());
        System.out.println("Principal: " + auth.getPrincipal());
        return "Check console for token details";
    }

}
