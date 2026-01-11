package com.biblioteca.loan_service.controller;

import com.biblioteca.loan_service.dto.CrearHistorialPrestamoDto;
import com.biblioteca.loan_service.dto.HistorialPrestamoDto;
import com.biblioteca.loan_service.service.HistorialPrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historial-prestamos")
public class HistoiralPrestamoService {

    @Autowired
    private HistorialPrestamoService historialPrestamoService;

    // ==================== ENDPOINTS CRUD BÁSICO ====================

    @GetMapping
    public ResponseEntity<List<HistorialPrestamoDto>> obtenerTodoElHistorial() {
        List<HistorialPrestamoDto> historial = historialPrestamoService.obtenerTodoElHistorial();
        return ResponseEntity.ok(historial);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistorialPrestamoDto> obtenerPorId(@PathVariable(name = "id") Long id) {
        HistorialPrestamoDto historial = historialPrestamoService.obtenerPorId(id);
        return ResponseEntity.ok(historial);
    }

    @PostMapping
    public ResponseEntity<HistorialPrestamoDto> crearRegistroHistorial(@RequestBody CrearHistorialPrestamoDto crearHistorialDto) {
        HistorialPrestamoDto historialCreado = historialPrestamoService.crearRegistroHistorial(crearHistorialDto);
        return new ResponseEntity<>(historialCreado, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRegistro(@PathVariable(name = "id") Long id) {
        historialPrestamoService.eliminarRegistro(id);
        return ResponseEntity.noContent().build();
    }

    // ==================== ENDPOINTS DE CONSULTAS ESPECÍFICAS ====================

    @GetMapping("/prestamo/{prestamoId}")
    public ResponseEntity<List<HistorialPrestamoDto>> obtenerPorPrestamo(@PathVariable(name = "prestamoId") Long prestamoId) {
        List<HistorialPrestamoDto> historial = historialPrestamoService.obtenerPorPrestamo(prestamoId);
        return ResponseEntity.ok(historial);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<HistorialPrestamoDto>> obtenerPorUsuario(@PathVariable(name = "usuarioId") Long usuarioId) {
        List<HistorialPrestamoDto> historial = historialPrestamoService.obtenerPorUsuario(usuarioId);
        return ResponseEntity.ok(historial);
    }

    @GetMapping("/accion/{accion}")
    public ResponseEntity<List<HistorialPrestamoDto>> obtenerPorAccion(@PathVariable(name = "accion") String accion) {
        List<HistorialPrestamoDto> historial = historialPrestamoService.obtenerPorAccion(accion);
        return ResponseEntity.ok(historial);
    }

    // ==================== ENDPOINTS AUTOMÁTICOS (HÍBRIDO) ====================

    @PostMapping("/automatico/creacion-prestamo")
    public ResponseEntity<Void> registrarCreacionPrestamo(
            @RequestParam(name = "prestamoId") Long prestamoId,
            @RequestParam(name = "usuarioId") Long usuarioId) {
        historialPrestamoService.registrarCreacionPrestamo(prestamoId, usuarioId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/automatico/devolucion-prestamo")
    public ResponseEntity<Void> registrarDevolucionPrestamo(
            @RequestParam(name = "prestamoId") Long prestamoId,
            @RequestParam(name = "usuarioId") Long usuarioId) {
        historialPrestamoService.registrarDevolucionPrestamo(prestamoId, usuarioId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/automatico/vencimiento-prestamo")
    public ResponseEntity<Void> registrarVencimientoPrestamo(
            @RequestParam(name = "prestamoId") Long prestamoId,
            @RequestParam(name = "usuarioId") Long usuarioId) {
        historialPrestamoService.registrarVencimientoPrestamo(prestamoId, usuarioId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/automatico/renovacion-prestamo")
    public ResponseEntity<Void> registrarRenovacionPrestamo(
            @RequestParam(name = "prestamoId") Long prestamoId,
            @RequestParam(name = "usuarioId") Long usuarioId,
            @RequestParam(name = "detalles") String detalles) {
        historialPrestamoService.registrarRenovacionPrestamo(prestamoId, usuarioId, detalles);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
