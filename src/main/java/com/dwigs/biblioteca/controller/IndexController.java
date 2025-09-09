package com.dwigs.biblioteca.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class IndexController {

    @GetMapping("/sumar")
    public ResponseEntity<String> sumar(@RequestParam long op1, @RequestParam long op2){
        return ResponseEntity.ok(String.valueOf(op1 + op2));
    }

}
