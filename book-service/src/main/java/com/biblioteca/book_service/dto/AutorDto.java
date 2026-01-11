package com.biblioteca.book_service.dto;

import java.time.LocalDate;

public record AutorDto(
        Long id,
        String nombre,
        String nacionalidad,
        LocalDate fechaNacimiento,  // ← Cambiado a LocalDate
        String biografia
) {
}
