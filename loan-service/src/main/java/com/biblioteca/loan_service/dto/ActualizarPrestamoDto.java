package com.biblioteca.loan_service.dto;

import com.biblioteca.loan_service.model.EstadoPrestamo;

import java.time.LocalDate;

public record ActualizarPrestamoDto(
        LocalDate fechaDevolucionReal,
        EstadoPrestamo estado  // ← Cambiado de String a Enum
) {
}
