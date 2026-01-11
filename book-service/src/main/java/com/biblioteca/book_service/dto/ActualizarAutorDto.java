package com.biblioteca.book_service.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record ActualizarAutorDto(
        @NotBlank String nombre,
        String nacionalidad,
        LocalDate fechaNacimiento,  // ← Cambiado a LocalDate
        String biografia
) {
}
