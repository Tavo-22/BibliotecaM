package com.biblioteca.book_service.dto;

import com.biblioteca.book_service.model.EstadoEjemplar;

public record ActualizarEjemplarDto(
        EstadoEjemplar estado,
        String ubicacion
) {
}
