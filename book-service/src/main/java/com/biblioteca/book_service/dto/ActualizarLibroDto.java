package com.biblioteca.book_service.dto;

public record ActualizarLibroDto(
        String titulo,
        String editorial,
        Integer anoPublicacion,
        String descripcion,
        Boolean disponible
) {
}
