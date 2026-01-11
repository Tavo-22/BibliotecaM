package com.biblioteca.book_service.dto;

import java.time.LocalDateTime;

public record LibroDto(
        Long id,
        String titulo,
        String isbn,
        String editorial,
        Integer anoPublicacion,
        String descripcion,
        LocalDateTime fechaCreacion
) {
}
