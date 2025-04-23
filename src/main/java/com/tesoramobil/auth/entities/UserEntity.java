package com.tesoramobil.auth.entities;

import java.time.LocalDateTime;

import com.tesoramobil.auth.models.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;



@Entity
@Table(name = "users")
public class UserEntity {

	private static final long serialVersionUID = 1L;

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(name = "username", nullable = false, length = 100)
	    private String username;

	    @Column(name = "password", nullable = false)
	    private String password;

	    @Column(name = "nombre", nullable = false)
	    private String name;

	    @Column(name = "apellido", nullable = false)
	    private String apellido;

	    @Column(name = "telefono", nullable = false)
	    private String telefono;

	    @Column(name = "email", unique = true, nullable = false)
	    private String email;

	    @Column(name = "fecha_creacion", nullable = false)
	    private LocalDateTime fechaCreacion;

	    @Enumerated(EnumType.STRING)
	    @Column(name = "role", nullable = false)
	    private Role role;

	    @Transient
	    private String token;
	    
	    public UserEntity() {}

		public UserEntity(Long id, String username, String password, String name, String apellido, String telefono,
				String email, LocalDateTime fechaCreacion, Role role, String token) {
			this.id = id;
			this.username = username;
			this.password = password;
			this.name = name;
			this.apellido = apellido;
			this.telefono = telefono;
			this.email = email;
			this.fechaCreacion = fechaCreacion;
			this.role = role;
			this.token = token;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getApellido() {
			return apellido;
		}

		public void setApellido(String apellido) {
			this.apellido = apellido;
		}

		public String getTelefono() {
			return telefono;
		}

		public void setTelefono(String telefono) {
			this.telefono = telefono;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public LocalDateTime getFechaCreacion() {
			return fechaCreacion;
		}

		public void setFechaCreacion(LocalDateTime fechaCreacion) {
			this.fechaCreacion = fechaCreacion;
		}

		public Role getRole() {
			return role;
		}

		public void setRole(Role role) {
			this.role = role;
		}

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}
		

		@Override
		public String toString() {
			return "UserEntity [id=" + id + ", username=" + username + ", password=" + password + ", name=" + name
					+ ", apellido=" + apellido + ", telefono=" + telefono + ", email=" + email + ", fechaCreacion="
					+ fechaCreacion + ", role=" + role + ", token=" + token + "]";
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}
	    
	    

}
