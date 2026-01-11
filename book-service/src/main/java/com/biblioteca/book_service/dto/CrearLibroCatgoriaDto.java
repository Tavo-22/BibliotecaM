package com.biblioteca.book_service.dto;

import jakarta.validation.constraints.NotNull;

public record CrearLibroCatgoriaDto(
        @NotNull Long libroId,
        @NotNull Long categoriaId
) {
}
