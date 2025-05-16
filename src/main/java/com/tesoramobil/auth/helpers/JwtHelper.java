package com.tesoramobil.auth.helpers;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.tesoramobil.auth.services.MessageService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtHelper {

	@Autowired
	private MessageService messageService;

	@Value("${jwt.secret}")
	private String jwtSecret;

	/**
	 * Genera un token JWT firmado con la clave secreta y con fecha de expiración.
	 *
	 * @param username Nombre de usuario (subject) que se incluirá en el token.
	 * @return Token JWT en formato String.
	 */
	public String createToken(String username) {

		final var now = new Date();
		final var expirationDate = new Date(now.getTime() + (3600 * 1000)); // 1 hora

		return Jwts
				.builder()
				.setSubject(username) // El valor que se usará como "identidad" del usuario
				.setIssuedAt(new Date(System.currentTimeMillis())) // Fecha de emisión
				.setExpiration(expirationDate) // Fecha de expiración
				.signWith(this.getSecretKey()) // Firma el token
				.compact(); // Genera el token final
	}

	// Modificamos JwtHelper para agregar los roles en el JWT
	public String createTokenWithClaims(String username, String role, Long userId) {
		final var now = new Date();
		final var expirationDate = new Date(now.getTime() + (3600 * 1000)); // 1 hora

		return Jwts.builder()
				.setSubject(username)
	            .claim("roles", role)     // 👤 rol
	            .claim("id", userId)      // 🆔 ID del usuario como claim
				.setIssuedAt(now)
				.setExpiration(expirationDate)
				.signWith(this.getSecretKey())
				.compact();
	}

	/**
	 * Verifica si un token JWT es válido, es decir, si no ha expirado.
	 *
	 * @param token Token JWT a validar.
	 * @return true si el token aún no ha expirado, false si ya no es válido.
	 */
	public boolean validateToken(String token) {
		try {
			final var expirationDate = this.getExpirationDate(token);
			return expirationDate.after(new Date());
		} catch (Exception e) {
			throw new ResponseStatusException(
					HttpStatus.UNAUTHORIZED,
					messageService.get("token.exception.message"));
		}
	}

	/**
	 * Verifica si un token ya ha expirado.
	 *
	 * @param token Token JWT.
	 * @return true si el token ya expiró, false en caso contrario.
	 */
	public boolean isTokenExpired(String token) {
		final Date expiration = this.getExpirationDate(token);
		return expiration.before(new Date());
	}

	/**
	 * Obtiene la fecha de expiración de un token JWT.
	 *
	 * @param token Token JWT.
	 * @return Fecha de expiración (Date) contenida en el token.
	 */
	private Date getExpirationDate(String token) {
		return this.getClaimsFromToken(token, Claims::getExpiration);
	}

	/**
	 * Extrae un dato específico (claim) desde un token JWT utilizando una función
	 * resolutora.
	 *
	 * @param token    El token JWT firmado.
	 * @param resolver Una función que extrae el valor deseado del objeto Claims.
	 * @param <T>      El tipo del dato que quieres obtener (String, Date, etc).
	 * @return El dato extraído del token JWT.
	 */
	private <T> T getClaimsFromToken(String token, Function<Claims, T> resolver) {
		return resolver.apply(this.signToken(token));
	}

	/**
	 * Firma un token JWT con la clave secreta y retorna los claims incluidos.
	 *
	 * @param token Token JWT a validar y firmar.
	 * @return Claims extraídos del token.
	 */
	private Claims signToken(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSecretKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	/**
	 * Genera la clave secreta HMAC para firmar/verificar tokens JWT.
	 *
	 * @return SecretKey usada para la firma HMAC.
	 */
	private SecretKey getSecretKey() {
		return Keys.hmacShaKeyFor(this.jwtSecret.getBytes(StandardCharsets.UTF_8));
	}

}
