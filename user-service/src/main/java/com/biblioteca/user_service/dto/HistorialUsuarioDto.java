package com.biblioteca.user_service.dto;

import java.time.LocalDateTime;

public record HistorialUsuarioDto(
        Long id,
        String accion,
        String descripcion,
        LocalDateTime fechaAccion,
        Long usuarioId
) {
}
