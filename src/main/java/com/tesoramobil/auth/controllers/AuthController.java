package com.tesoramobil.auth.controllers;

import com.tesoramobil.auth.models.TokenDto;
import com.tesoramobil.auth.models.UserLoginDto;
import com.tesoramobil.auth.services.AuthService;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/auth") // Ruta base del controlador
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Endpoint para iniciar sesión y generar un token JWT.
     *
     * @param user DTO que contiene username y password.
     * @return TokenDto con el accessToken generado.
     */
    @PostMapping(path = "/login")
    public ResponseEntity<TokenDto> jwtCreate(@RequestBody UserLoginDto user) {
        return ResponseEntity.ok(this.authService.login(user));
    }

    /**
     * Endpoint para validar un token JWT recibido en el header.
     *
     * @param accessToken El token JWT enviado en el header.
     * @return TokenDto si el token es válido.
     */
    @PostMapping(path = "/jwt")
    public ResponseEntity<TokenDto> jwtValidate(@RequestHeader String accessToken) {
        return ResponseEntity.ok(
            this.authService.validateToken(
                TokenDto.builder().accessToken(accessToken).build()
            )
        );
    }
}
