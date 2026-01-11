package com.biblioteca.loan_service.dto;

public record LibroDto(
        Long id,
        String titulo,
        String isbn,
        String editorial,
        Integer anoPublicacion,
        String descripcion
) {
}
