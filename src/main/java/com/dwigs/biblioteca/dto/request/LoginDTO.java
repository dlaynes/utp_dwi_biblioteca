package com.dwigs.biblioteca.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {

    @NotBlank(message = "La contraseña no puede estar en blanco")
    private String password;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email indicado no es válido")
    @Size(max=150, message = "El email no puede tener más de 150 caracteres")
    private String email;

    private boolean remember;

}
