package com.dwigs.biblioteca.controller;

import com.dwigs.biblioteca.exception.ParametrosInvalidosException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class ErrorAdviceController {

    @ExceptionHandler(ParametrosInvalidosException.class)
    public ResponseEntity<String> manejarParametrosInvalidos(ParametrosInvalidosException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<String> noExisteRuta(ParametrosInvalidosException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /*
    @ExceptionHandler(Unauthorized.class)
    public ResponseEntity<String> sinAutorizacion(ParametrosInvalidosException ex) {{
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }
    */

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> manejarExcepcionGeneral(Exception ex) {
        ex.printStackTrace();
        return new ResponseEntity<>("Ocurrió un error inesperado", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
