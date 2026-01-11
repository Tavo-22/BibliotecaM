package com.biblioteca.loan_service.dto;

public record EjemplarDto(
        Long id,
        String codigoBarras,
        String estado,
        String ubicacion,
        Long libroId
) {
}
