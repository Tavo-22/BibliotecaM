package com.biblioteca.loan_service.dto;

import com.biblioteca.loan_service.model.EstadoPrestamo;

import java.time.LocalDate;

public record PrestamoDto(
        Long id,
        Long usuarioId,
        String usuarioNombre,    // ← Nuevo
        Long ejemplarId,
        String libroTitulo,      // ← Nuevo
        String codigoEjemplar,   // ← Nuevo
        LocalDate fechaPrestamo,
        LocalDate fechaDevolucionPrevista,
        LocalDate fechaDevolucionReal,
        EstadoPrestamo estado
) {
}
