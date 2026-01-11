package com.biblioteca.user_service.dto;

public record DireccionDto(
        Long id,
        String ciudad,
        String codigoPostal,
        String direccion,
        Long usuarioId  // Solo el ID del usuario para evitar recursión
) {
}
