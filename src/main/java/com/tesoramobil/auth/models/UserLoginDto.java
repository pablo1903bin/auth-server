package com.tesoramobil.auth.models;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class UserLoginDto {

    @NotBlank(message = "El nombre de usuario es obligatorio")
    private String username;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
}
