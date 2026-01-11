package com.biblioteca.loan_service.dto;

import java.time.LocalDateTime;

public record HistorialPrestamoDto(
        Long id,
        Long prestamoId,
        String accion,
        LocalDateTime fechaAccion,
        String observaciones,
        Long usuarioId,
        String usuarioNombre,    // ← Nuevo
        String libroTitulo,      // ← Nuevo (del préstamo)
        String codigoEjemplar    // ← Nuevo (del préstamo)
) {
}
