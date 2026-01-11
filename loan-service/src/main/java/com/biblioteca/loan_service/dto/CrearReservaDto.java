package com.biblioteca.loan_service.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;

public record CrearReservaDto(
        @NotNull Long usuarioId,
        @NotNull Long libroId,
        @NotNull @FutureOrPresent LocalDate fechaReserva,  // ← Validación
        @NotNull @Future LocalDate fechaExpiracion         // ← Validación
) {
}
