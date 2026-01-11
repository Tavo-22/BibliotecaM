package com.biblioteca.book_service.dto;

import jakarta.validation.constraints.NotNull;

public record CrearLibroAutorDto(
        @NotNull Long libroId,
        @NotNull Long autorId
) {
}
