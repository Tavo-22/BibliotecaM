package com.biblioteca.loan_service.service;

import com.biblioteca.loan_service.dto.CrearHistorialPrestamoDto;
import com.biblioteca.loan_service.dto.HistorialPrestamoDto;
import com.biblioteca.loan_service.model.HistorialPrestamo;
import com.biblioteca.loan_service.repository.HistorialPrestamoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class HistorialPrestamoService {
    @Autowired
    private HistorialPrestamoRepository historialPrestamoRepository;

    // ==================== CONVERSIONES ====================

    private HistorialPrestamoDto convertirADTO(HistorialPrestamo historial) {
        return new HistorialPrestamoDto(
                historial.getId(),
                historial.getPrestamoId(),
                historial.getAccion(),
                historial.getFechaAccion(),
                historial.getObservaciones(),
                historial.getUsuarioId(),
                null, // usuarioNombre - se llenará después
                null, // libroTitulo - se llenará después
                null  // codigoEjemplar - se llenará después
        );
    }

    private HistorialPrestamo convertirAEntidad(CrearHistorialPrestamoDto dto) {
        HistorialPrestamo historial = new HistorialPrestamo();
        historial.setPrestamoId(dto.prestamoId());
        historial.setAccion(dto.accion());
        historial.setFechaAccion(LocalDateTime.now());
        historial.setObservaciones(dto.observaciones());
        historial.setUsuarioId(dto.usuarioId());
        return historial;
    }

    // ==================== CRUD BÁSICO ====================

    public List<HistorialPrestamoDto> obtenerTodoElHistorial() {
        return historialPrestamoRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public HistorialPrestamoDto obtenerPorId(Long id) {
        HistorialPrestamo historial = historialPrestamoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro de historial no encontrado con ID: " + id));
        return convertirADTO(historial);
    }

    public HistorialPrestamoDto crearRegistroHistorial(CrearHistorialPrestamoDto crearHistorialDto) {
        HistorialPrestamo historial = convertirAEntidad(crearHistorialDto);
        HistorialPrestamo historialGuardado = historialPrestamoRepository.save(historial);
        return convertirADTO(historialGuardado);
    }

    public void eliminarRegistro(Long id) {
        if (!historialPrestamoRepository.existsById(id)) {
            throw new RuntimeException("Registro de historial no encontrado con ID: " + id);
        }
        historialPrestamoRepository.deleteById(id);
    }

    // ==================== CONSULTAS ESPECÍFICAS ====================

    public List<HistorialPrestamoDto> obtenerPorPrestamo(Long prestamoId) {
        return historialPrestamoRepository.findByPrestamoId(prestamoId)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public List<HistorialPrestamoDto> obtenerPorUsuario(Long usuarioId) {
        return historialPrestamoRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public List<HistorialPrestamoDto> obtenerPorAccion(String accion) {
        return historialPrestamoRepository.findByAccion(accion)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // ==================== MÉTODOS AUTOMÁTICOS (HÍBRIDO) ====================

    public void registrarCreacionPrestamo(Long prestamoId, Long usuarioId) {
        CrearHistorialPrestamoDto historialDto = new CrearHistorialPrestamoDto(
                prestamoId,
                "PRESTAMO_CREADO",
                "Préstamo creado exitosamente",
                usuarioId
        );
        crearRegistroHistorial(historialDto);
    }

    public void registrarDevolucionPrestamo(Long prestamoId, Long usuarioId) {
        CrearHistorialPrestamoDto historialDto = new CrearHistorialPrestamoDto(
                prestamoId,
                "PRESTAMO_DEVUELTO",
                "Préstamo devuelto satisfactoriamente",
                usuarioId
        );
        crearRegistroHistorial(historialDto);
    }

    public void registrarVencimientoPrestamo(Long prestamoId, Long usuarioId) {
        CrearHistorialPrestamoDto historialDto = new CrearHistorialPrestamoDto(
                prestamoId,
                "PRESTAMO_VENCIDO",
                "Préstamo marcado como vencido",
                usuarioId
        );
        crearRegistroHistorial(historialDto);
    }

    public void registrarRenovacionPrestamo(Long prestamoId, Long usuarioId, String detalles) {
        CrearHistorialPrestamoDto historialDto = new CrearHistorialPrestamoDto(
                prestamoId,
                "PRESTAMO_RENOVADO",
                "Préstamo renovado: " + detalles,
                usuarioId
        );
        crearRegistroHistorial(historialDto);
    }
}
