package com.biblioteca.user_service.dto;

import com.biblioteca.user_service.model.TipoMembresia;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CrearUsuarioDto(
        @NotBlank String nombre,
        @NotBlank String apellido,
        @NotBlank @Email String email,
        String telefono,
        TipoMembresia tipoMembresia
) {
}
