package com.biblioteca.user_service.dto;

import jakarta.validation.constraints.NotBlank;

public record CrearDireccionDto(
        @NotBlank String ciudad,
        @NotBlank String codigoPostal,
        @NotBlank String direccion,
        @NotBlank Long usuarioId
) {
}
