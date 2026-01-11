package com.biblioteca.loan_service.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CrearMultaDto(
        @NotNull Long usuarioId,
        Long prestamoId,        // ← Puede ser null si es multa directa
        @NotNull @Positive BigDecimal monto,
        @NotNull @FutureOrPresent LocalDate fechaMulta,
        String descripcion
) {
}
