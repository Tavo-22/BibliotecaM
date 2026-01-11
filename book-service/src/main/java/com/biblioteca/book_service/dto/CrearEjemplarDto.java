package com.biblioteca.book_service.dto;

import com.biblioteca.book_service.model.EstadoEjemplar;
import jakarta.validation.constraints.NotBlank;

public record CrearEjemplarDto(
        @NotBlank String codigoBarras,
        EstadoEjemplar estado,
        String ubicacion,
        @NotBlank Long libroId
) {
}
