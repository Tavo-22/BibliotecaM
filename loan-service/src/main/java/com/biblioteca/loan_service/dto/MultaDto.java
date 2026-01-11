package com.biblioteca.loan_service.dto;

import com.biblioteca.loan_service.model.EstadoMulta;

import java.math.BigDecimal;
import java.time.LocalDate;

public record MultaDto(
        Long id,
        Long usuarioId,
        String usuarioNombre,    // ← Nuevo
        Long prestamoId,
        String libroTitulo,      // ← Nuevo (del préstamo)
        BigDecimal monto,
        LocalDate fechaMulta,
        LocalDate fechaPago,
        EstadoMulta estado,
        String descripcion
) {
}
