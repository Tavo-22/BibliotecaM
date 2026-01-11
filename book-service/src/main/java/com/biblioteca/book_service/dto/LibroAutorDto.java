package com.biblioteca.book_service.dto;

public record LibroAutorDto(
        Long id,
        Long libroId,
        Long autorId,
        String libroTitulo,  // Opcional: para mostrar info
        String autorNombre   // Opcional: para mostrar info
) {
}
