package com.biblioteca.user_service.dto;

import jakarta.validation.constraints.NotBlank;

public record CrearHistorialUsuarioDto(
        @NotBlank String accion,
        @NotBlank String descripcion,
        @NotBlank Long usuarioId
) {
}
