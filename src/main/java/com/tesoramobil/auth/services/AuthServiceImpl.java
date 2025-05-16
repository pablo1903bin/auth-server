package com.tesoramobil.auth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.tesoramobil.auth.entities.UserEntity;
import com.tesoramobil.auth.helpers.JwtHelper;
import com.tesoramobil.auth.models.TokenDto;
import com.tesoramobil.auth.models.UserLoginDto;
import com.tesoramobil.auth.models.UserLoginResponseDto;
import com.tesoramobil.auth.repositories.UserRepository;

/**
 * Servicio encargado de gestionar la autenticación de usuarios.
 * Implementa las operaciones declaradas en la interfaz AuthService.
 */
@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private MessageService messageService;

    /**
     * Inicia sesión utilizando un objeto UserEntity.
     *
     * @param user El usuario que intenta autenticarse.
     * @return TokenDto con el token JWT si la autenticación es exitosa.
     */
    @Override
    public TokenDto login(UserLoginDto user) {
        final var userFromDB = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                        messageService.get("user.exception.message")));

        this.validPassword(user, userFromDB);

        return TokenDto.builder().accessToken(jwtHelper.createToken(userFromDB.getUsername())).build();

    }

    /**
     * Valida si un token JWT es válido (no ha expirado ni ha sido manipulado).
     *
     * @param token Objeto TokenDto que contiene el token JWT a validar.
     * @return TokenDto si el token es válido.
     * @throws ResponseStatusException si el token es inválido o ha expirado.
     */
    @Override
    public TokenDto validateToken(TokenDto token) {
        try {
            if (this.jwtHelper.validateToken(token.getAccessToken())) {
                return TokenDto.builder()
                        .accessToken(token.getAccessToken())
                        .build();
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                        messageService.get("user.exception.message"));
            }
        } catch (Exception e) {
            // Si cualquier excepción ocurre al validar el token, responde 401
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, messageService.get("user.exception.message"));
        }
    }

    // Modificamos AuthServiceImpl.login para retornar el nuevo DTO y usar el nuevo
    // método
    @Override
    public UserLoginResponseDto loginClaims(UserLoginDto user) {
        final var userFromDB = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                        messageService.get("user.exception.message")));

        System.out.println("[auth -server AuthServiceImpl]  📦  usuario de la DB:  " + userFromDB.toString());

        this.validPassword(user, userFromDB);

        String token = jwtHelper.createTokenWithClaims(userFromDB.getUsername(), userFromDB.getRole().name(), userFromDB.getId());

        return UserLoginResponseDto.builder()
                .idUser(userFromDB.getId())
                .username(userFromDB.getUsername())
                .email(userFromDB.getEmail())
                .role(userFromDB.getRole())
                .tkn(token)
                .build();
    }

    /**
     * Valida que la contraseña ingresada coincida con la almacenada en base de
     * datos.
     *
     * @param userDto    Usuario recibido con la contraseña en texto plano.
     * @param userEntity Usuario encontrado en la base de datos con la contraseña
     *                   encriptada.
     * @throws ResponseStatusException si las contraseñas no coinciden.
     */
    private void validPassword(UserLoginDto userDto, UserEntity userEntity) {
        if (!this.passwordEncoder.matches(userDto.getPassword(), userEntity.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, messageService.get("user.exception.message"));
        }
    }
}
