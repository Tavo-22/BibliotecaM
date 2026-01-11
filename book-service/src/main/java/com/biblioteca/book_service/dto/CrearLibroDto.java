package com.biblioteca.book_service.dto;

import jakarta.validation.constraints.NotBlank;

public record CrearLibroDto(
        @NotBlank String titulo,
        @NotBlank String isbn,
        String editorial,
        Integer anoPublicacion,   // ← Año como Integer
        String descripcion
) {
}
