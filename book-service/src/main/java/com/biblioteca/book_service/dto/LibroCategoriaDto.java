package com.biblioteca.book_service.dto;

public record LibroCategoriaDto(
        Long id,
        Long libroId,
        Long categoriaId,
        String libroTitulo,    // Opcional: para mostrar info
        String categoriaNombre // Opcional: para mostrar info
) {
}
