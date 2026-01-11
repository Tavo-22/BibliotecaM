package com.biblioteca.loan_service.dto;

import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;

public record CrearPrestamoDto(
        @NotNull Long usuarioId,
        @NotNull Long ejemplarId,
        @NotNull LocalDate fechaPrestamo,
        @NotNull LocalDate fechaDevolucionPrevista
) {
}
