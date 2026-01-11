package com.biblioteca.user_service.dto;

import com.biblioteca.user_service.model.TipoMembresia;

public record UsuarioDto(
        Long id,
        String nombre,
        String email,
        TipoMembresia tipoMembresia,
        boolean activo
) {
}
