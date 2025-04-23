package com.tesoramobil.auth.models;

/**
 * Enumeración que representa los roles disponibles en la aplicación.
 * Define los tipos de acceso que los usuarios pueden tener.
 */
public enum Role {
    /**
     * Rol básico para usuarios estándar.
     * Los usuarios con este rol tienen permisos limitados.
     */
    USER,

    /**
     * Rol para administradores.
     * Los usuarios con este rol tienen acceso completo a las funcionalidades del sistema.
     */
    ADMIN
}
