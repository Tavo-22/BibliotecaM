package com.biblioteca.loan_service.controller;

import com.biblioteca.loan_service.dto.ActualizarMultaDto;
import com.biblioteca.loan_service.dto.CrearMultaDto;
import com.biblioteca.loan_service.dto.MultaDto;
import com.biblioteca.loan_service.model.EstadoMulta;
import com.biblioteca.loan_service.service.MultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/multas")
public class MultaController {

    @Autowired
    private MultaService multaService;

    // ==================== ENDPOINTS CRUD BÁSICO ====================

    @GetMapping
    public ResponseEntity<List<MultaDto>> obtenerTodas() {
        List<MultaDto> multas = multaService.obtenerTodas();
        return ResponseEntity.ok(multas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MultaDto> obtenerPorId(@PathVariable(name = "id") Long id) {
        MultaDto multa = multaService.obtenerPorId(id);
        return ResponseEntity.ok(multa);
    }

    @PostMapping
    public ResponseEntity<MultaDto> crearMulta(@RequestBody CrearMultaDto crearMultaDto) {
        MultaDto multaCreada = multaService.crear(crearMultaDto);
        return new ResponseEntity<>(multaCreada, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MultaDto> actualizarMulta(
            @PathVariable(name = "id") Long id,
            @RequestBody ActualizarMultaDto actualizarMultaDto) {
        MultaDto multaActualizada = multaService.actualizar(id, actualizarMultaDto);
        return ResponseEntity.ok(multaActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMulta(@PathVariable(name = "id") Long id) {
        multaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // ==================== ENDPOINTS DE CONSULTAS ESPECÍFICAS ====================

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<MultaDto>> obtenerPorUsuario(@PathVariable(name = "usuarioId") Long usuarioId) {
        List<MultaDto> multas = multaService.obtenerPorUsuario(usuarioId);
        return ResponseEntity.ok(multas);
    }

    @GetMapping("/prestamo/{prestamoId}")
    public ResponseEntity<List<MultaDto>> obtenerPorPrestamo(@PathVariable(name = "prestamoId") Long prestamoId) {
        List<MultaDto> multas = multaService.obtenerPorPrestamo(prestamoId);
        return ResponseEntity.ok(multas);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<MultaDto>> obtenerPorEstado(@PathVariable(name = "estado") EstadoMulta estado) {
        List<MultaDto> multas = multaService.obtenerPorEstado(estado);
        return ResponseEntity.ok(multas);
    }

    @GetMapping("/usuario/{usuarioId}/pendientes")
    public ResponseEntity<List<MultaDto>> obtenerMultasPendientesPorUsuario(@PathVariable(name = "usuarioId") Long usuarioId) {
        List<MultaDto> multas = multaService.obtenerMultasPendientesPorUsuario(usuarioId);
        return ResponseEntity.ok(multas);
    }

    @GetMapping("/rango-fechas")
    public ResponseEntity<List<MultaDto>> obtenerPorRangoFechas(
            @RequestParam(name = "inicio") LocalDate inicio,
            @RequestParam(name = "fin") LocalDate fin) {
        List<MultaDto> multas = multaService.obtenerPorRangoFechas(inicio, fin);
        return ResponseEntity.ok(multas);
    }

    @GetMapping("/pagadas/periodo")
    public ResponseEntity<List<MultaDto>> obtenerMultasPagadasEnPeriodo(
            @RequestParam(name = "inicio") LocalDate inicio,
            @RequestParam(name = "fin") LocalDate fin) {
        List<MultaDto> multas = multaService.obtenerMultasPagadasEnPeriodo(inicio, fin);
        return ResponseEntity.ok(multas);
    }

    // ==================== ENDPOINTS DE OPERACIONES DE NEGOCIO ====================

    @GetMapping("/usuario/{usuarioId}/total-pendientes")
    public ResponseEntity<BigDecimal> calcularTotalMultasPendientesPorUsuario(@PathVariable(name = "usuarioId") Long usuarioId) {
        BigDecimal total = multaService.calcularTotalMultasPendientesPorUsuario(usuarioId);
        return ResponseEntity.ok(total);
    }

    @GetMapping("/usuario/{usuarioId}/contar-pendientes")
    public ResponseEntity<Long> contarMultasPendientesPorUsuario(@PathVariable(name = "usuarioId") Long usuarioId) {
        long cantidad = multaService.contarMultasPendientesPorUsuario(usuarioId);
        return ResponseEntity.ok(cantidad);
    }

    @PutMapping("/{id}/pagar")
    public ResponseEntity<MultaDto> pagarMulta(@PathVariable(name = "id") Long id) {
        multaService.pagarMulta(id);
        MultaDto multaActualizada = multaService.obtenerPorId(id);
        return ResponseEntity.ok(multaActualizada);
    }

    @PostMapping("/generar-por-vencimiento")
    public ResponseEntity<MultaDto> generarMultaPorVencimiento(
            @RequestParam(name = "prestamoId") Long prestamoId,
            @RequestParam(name = "usuarioId") Long usuarioId,
            @RequestParam(name = "monto") BigDecimal monto,
            @RequestParam(name = "descripcion") String descripcion) {
        multaService.generarMultaPorVencimiento(prestamoId, usuarioId, monto, descripcion);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
