package com.biblioteca.loan_service.dto;

import com.biblioteca.loan_service.model.EstadoReserva;

public record ActualizarReservaDto(
        EstadoReserva estado
) {
}
