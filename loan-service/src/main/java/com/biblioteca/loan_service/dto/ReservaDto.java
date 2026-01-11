package com.biblioteca.loan_service.dto;

import com.biblioteca.loan_service.model.EstadoReserva;

import java.time.LocalDate;

public record ReservaDto(
        Long id,
        Long usuarioId,
        String usuarioNombre,    // ← Nuevo
        Long libroId,
        String libroTitulo,      // ← Nuevo
        LocalDate fechaReserva,
        LocalDate fechaExpiracion,
        EstadoReserva estado
) {
}
