package com.biblioteca.book_service.dto;

import jakarta.validation.constraints.NotBlank;

public record CrearCategoriaDto(
        @NotBlank String nombre,
        String descripcion
) {
}
