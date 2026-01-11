package com.biblioteca.book_service.dto;

import com.biblioteca.book_service.model.EstadoEjemplar;

public record EjemplarDto(
        Long id,
        String codigoBarras,
        EstadoEjemplar estado,
        String ubicacion,
        Long libroId,
        String libroTitulo
) {
}
