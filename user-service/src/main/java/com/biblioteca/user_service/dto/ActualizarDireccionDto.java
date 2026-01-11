package com.biblioteca.user_service.dto;

public record ActualizarDireccionDto(
        String ciudad,
        String codigoPostal,
        String direccion
) {
}
