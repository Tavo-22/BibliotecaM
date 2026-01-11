package com.biblioteca.loan_service.dto;

public record UsuarioDto(
        Long id,
        String nombre,
        String email,
        String tipoMembresia,
        boolean activo
) {
}
