package com.biblioteca.loan_service.dto;

import com.biblioteca.loan_service.model.EstadoMulta;

import java.time.LocalDate;

public record ActualizarMultaDto(
        LocalDate fechaPago,
        EstadoMulta estado,
        String descripcion
) {
}
