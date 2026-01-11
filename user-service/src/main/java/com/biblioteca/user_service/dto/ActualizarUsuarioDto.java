package com.biblioteca.user_service.dto;

import com.biblioteca.user_service.model.TipoMembresia;

public record ActualizarUsuarioDto(
        String nombre,
        String apellido,
        String telefono,
        TipoMembresia tipoMembresia,
        Boolean activo
) {
}
