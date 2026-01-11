package com.biblioteca.loan_service.controller;

import com.biblioteca.loan_service.dto.ActualizarPrestamoDto;
import com.biblioteca.loan_service.dto.CrearPrestamoDto;
import com.biblioteca.loan_service.dto.PrestamoDto;
import com.biblioteca.loan_service.model.EstadoPrestamo;
import com.biblioteca.loan_service.service.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    // ==================== ENDPOINTS CRUD BÁSICO ====================

    @GetMapping
    public ResponseEntity<List<PrestamoDto>> obtenerTodos() {
        List<PrestamoDto> prestamos = prestamoService.obtenerTodos();
        return ResponseEntity.ok(prestamos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrestamoDto> obtenerPorId(@PathVariable(name = "id") Long id) {
        PrestamoDto prestamo = prestamoService.obtenerPorId(id);
        return ResponseEntity.ok(prestamo);
    }

    @PostMapping
    public ResponseEntity<PrestamoDto> crearPrestamo(@RequestBody CrearPrestamoDto crearPrestamoDto) {
        PrestamoDto prestamoCreado = prestamoService.crear(crearPrestamoDto);
        return new ResponseEntity<>(prestamoCreado, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrestamoDto> actualizarPrestamo(
            @PathVariable(name = "id") Long id,
            @RequestBody ActualizarPrestamoDto actualizarPrestamoDto) {
        PrestamoDto prestamoActualizado = prestamoService.actualizar(id, actualizarPrestamoDto);
        return ResponseEntity.ok(prestamoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPrestamo(@PathVariable(name = "id") Long id) {
        prestamoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // ==================== ENDPOINTS DE CONSULTAS ESPECÍFICAS ====================

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<PrestamoDto>> obtenerPorUsuario(@PathVariable(name = "usuarioId") Long usuarioId) {
        List<PrestamoDto> prestamos = prestamoService.obtenerPorUsuario(usuarioId);
        return ResponseEntity.ok(prestamos);
    }

    @GetMapping("/ejemplar/{ejemplarId}")
    public ResponseEntity<List<PrestamoDto>> obtenerPorEjemplar(@PathVariable(name = "ejemplarId") Long ejemplarId) {
        List<PrestamoDto> prestamos = prestamoService.obtenerPorEjemplar(ejemplarId);
        return ResponseEntity.ok(prestamos);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<PrestamoDto>> obtenerPorEstado(@PathVariable(name = "estado") EstadoPrestamo estado) {
        List<PrestamoDto> prestamos = prestamoService.obtenerPorEstado(estado);
        return ResponseEntity.ok(prestamos);
    }

    @GetMapping("/usuario/{usuarioId}/activos")
    public ResponseEntity<List<PrestamoDto>> obtenerPrestamosActivosPorUsuario(@PathVariable(name = "usuarioId") Long usuarioId) {
        List<PrestamoDto> prestamos = prestamoService.obtenerPrestamosActivosPorUsuario(usuarioId);
        return ResponseEntity.ok(prestamos);
    }

    @GetMapping("/vencidos")
    public ResponseEntity<List<PrestamoDto>> obtenerPrestamosVencidos() {
        List<PrestamoDto> prestamos = prestamoService.obtenerPrestamosVencidos();
        return ResponseEntity.ok(prestamos);
    }

    @GetMapping("/usuario/{usuarioId}/contar-activos")
    public ResponseEntity<Long> contarPrestamosActivosPorUsuario(@PathVariable(name = "usuarioId") Long usuarioId) {
        long cantidad = prestamoService.contarPrestamosActivosPorUsuario(usuarioId);
        return ResponseEntity.ok(cantidad);
    }

    @GetMapping("/rango-fechas")
    public ResponseEntity<List<PrestamoDto>> obtenerPorRangoFechas(
            @RequestParam(name = "inicio") LocalDate inicio,
            @RequestParam(name = "fin") LocalDate fin) {
        List<PrestamoDto> prestamos = prestamoService.obtenerPorRangoFechas(inicio, fin);
        return ResponseEntity.ok(prestamos);
    }

    // ==================== ENDPOINTS DE OPERACIONES DE NEGOCIO ====================

    @PutMapping("/{id}/devolver")
    public ResponseEntity<PrestamoDto> devolverPrestamo(@PathVariable(name = "id") Long id) {
        ActualizarPrestamoDto actualizarDto = new ActualizarPrestamoDto(
                LocalDate.now(), // fechaDevolucionReal
                EstadoPrestamo.DEVUELTO // estado
        );
        PrestamoDto prestamoActualizado = prestamoService.actualizar(id, actualizarDto);
        return ResponseEntity.ok(prestamoActualizado);
    }

    @PutMapping("/{id}/marcar-vencido")
    public ResponseEntity<PrestamoDto> marcarPrestamoComoVencido(@PathVariable(name = "id") Long id) {
        ActualizarPrestamoDto actualizarDto = new ActualizarPrestamoDto(
                null, // fechaDevolucionReal
                EstadoPrestamo.VENCIDO // estado
        );
        PrestamoDto prestamoActualizado = prestamoService.actualizar(id, actualizarDto);
        return ResponseEntity.ok(prestamoActualizado);
    }
}
