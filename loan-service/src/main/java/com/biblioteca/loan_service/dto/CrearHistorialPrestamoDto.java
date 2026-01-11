package com.biblioteca.loan_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CrearHistorialPrestamoDto(
        @NotNull Long prestamoId,
        @NotBlank String accion,
        String observaciones,
        Long usuarioId
) {
}
