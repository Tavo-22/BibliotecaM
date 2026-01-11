package com.biblioteca.loan_service.controller;

import com.biblioteca.loan_service.dto.ActualizarReservaDto;
import com.biblioteca.loan_service.dto.CrearReservaDto;
import com.biblioteca.loan_service.dto.ReservaDto;
import com.biblioteca.loan_service.model.EstadoReserva;
import com.biblioteca.loan_service.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    // ==================== ENDPOINTS CRUD BÁSICO ====================

    @GetMapping
    public ResponseEntity<List<ReservaDto>> obtenerTodas() {
        List<ReservaDto> reservas = reservaService.obtenerTodas();
        return ResponseEntity.ok(reservas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaDto> obtenerPorId(@PathVariable(name = "id") Long id) {
        ReservaDto reserva = reservaService.obtenerPorId(id);
        return ResponseEntity.ok(reserva);
    }

    @PostMapping
    public ResponseEntity<ReservaDto> crearReserva(@RequestBody CrearReservaDto crearReservaDto) {
        ReservaDto reservaCreada = reservaService.crear(crearReservaDto);
        return new ResponseEntity<>(reservaCreada, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservaDto> actualizarReserva(
            @PathVariable(name = "id") Long id,
            @RequestBody ActualizarReservaDto actualizarReservaDto) {
        ReservaDto reservaActualizada = reservaService.actualizar(id, actualizarReservaDto);
        return ResponseEntity.ok(reservaActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReserva(@PathVariable(name = "id") Long id) {
        reservaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // ==================== ENDPOINTS DE CONSULTAS ESPECÍFICAS ====================

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ReservaDto>> obtenerPorUsuario(@PathVariable(name = "usuarioId") Long usuarioId) {
        List<ReservaDto> reservas = reservaService.obtenerPorUsuario(usuarioId);
        return ResponseEntity.ok(reservas);
    }

    @GetMapping("/libro/{libroId}")
    public ResponseEntity<List<ReservaDto>> obtenerPorLibro(@PathVariable(name = "libroId") Long libroId) {
        List<ReservaDto> reservas = reservaService.obtenerPorLibro(libroId);
        return ResponseEntity.ok(reservas);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<ReservaDto>> obtenerPorEstado(@PathVariable(name = "estado") EstadoReserva estado) {
        List<ReservaDto> reservas = reservaService.obtenerPorEstado(estado);
        return ResponseEntity.ok(reservas);
    }

    @GetMapping("/usuario/{usuarioId}/activas")
    public ResponseEntity<List<ReservaDto>> obtenerReservasActivasPorUsuario(@PathVariable(name = "usuarioId") Long usuarioId) {
        List<ReservaDto> reservas = reservaService.obtenerReservasActivasPorUsuario(usuarioId);
        return ResponseEntity.ok(reservas);
    }

    @GetMapping("/libro/{libroId}/activas")
    public ResponseEntity<List<ReservaDto>> obtenerReservasActivasPorLibro(@PathVariable(name = "libroId") Long libroId) {
        List<ReservaDto> reservas = reservaService.obtenerReservasActivasPorLibro(libroId);
        return ResponseEntity.ok(reservas);
    }

    @GetMapping("/expiradas")
    public ResponseEntity<List<ReservaDto>> obtenerReservasExpiradas() {
        List<ReservaDto> reservas = reservaService.obtenerReservasExpiradas();
        return ResponseEntity.ok(reservas);
    }

    @GetMapping("/libro/{libroId}/contar-activas")
    public ResponseEntity<Long> contarReservasActivasPorLibro(@PathVariable(name = "libroId") Long libroId) {
        long cantidad = reservaService.contarReservasActivasPorLibro(libroId);
        return ResponseEntity.ok(cantidad);
    }

    // ==================== ENDPOINTS DE OPERACIONES DE NEGOCIO ====================

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<ReservaDto> cancelarReserva(@PathVariable(name = "id") Long id) {
        ActualizarReservaDto actualizarDto = new ActualizarReservaDto(EstadoReserva.CANCELADA);
        ReservaDto reservaActualizada = reservaService.actualizar(id, actualizarDto);
        return ResponseEntity.ok(reservaActualizada);
    }

    @PutMapping("/{id}/completar")
    public ResponseEntity<ReservaDto> completarReserva(@PathVariable(name = "id") Long id) {
        ActualizarReservaDto actualizarDto = new ActualizarReservaDto(EstadoReserva.COMPLETADA);
        ReservaDto reservaActualizada = reservaService.actualizar(id, actualizarDto);
        return ResponseEntity.ok(reservaActualizada);
    }

    @PostMapping("/expiradas/actualizar")
    public ResponseEntity<Void> actualizarReservasExpiradas() {
        reservaService.expirarReservasAutomaticamente();
        return ResponseEntity.ok().build();
    }
}
