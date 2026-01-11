package com.biblioteca.loan_service.service;

import com.biblioteca.loan_service.dto.ActualizarMultaDto;
import com.biblioteca.loan_service.dto.CrearMultaDto;
import com.biblioteca.loan_service.dto.MultaDto;
import com.biblioteca.loan_service.model.EstadoMulta;
import com.biblioteca.loan_service.model.Multa;
import com.biblioteca.loan_service.repository.MultaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MultaService {

    @Autowired
    private MultaRepository multaRepository;

    // ==================== CONVERSIONES ====================

    private MultaDto convertirADTO(Multa multa) {
        return new MultaDto(
                multa.getId(),
                multa.getUsuarioId(),
                null, // usuarioNombre - se llenará después
                multa.getPrestamoId(),
                null, // libroTitulo - se llenará después
                multa.getMonto(),
                multa.getFechaMulta(),
                multa.getFechaPago(),
                multa.getEstado(),
                multa.getDescripcion()
        );
    }

    private Multa convertirAEntidad(CrearMultaDto dto) {
        Multa multa = new Multa();
        multa.setUsuarioId(dto.usuarioId());
        multa.setPrestamoId(dto.prestamoId());
        multa.setMonto(dto.monto());
        multa.setFechaMulta(dto.fechaMulta());
        multa.setEstado(EstadoMulta.PENDIENTE); // Por defecto
        multa.setDescripcion(dto.descripcion());
        return multa;
    }

    // ==================== CRUD BÁSICO ====================

    public List<MultaDto> obtenerTodas() {
        return multaRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public MultaDto obtenerPorId(Long id) {
        Multa multa = multaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Multa no encontrada con ID: " + id));
        return convertirADTO(multa);
    }

    public MultaDto crear(CrearMultaDto crearMultaDto) {
        Multa multa = convertirAEntidad(crearMultaDto);
        Multa multaGuardada = multaRepository.save(multa);
        return convertirADTO(multaGuardada);
    }

    public MultaDto actualizar(Long id, ActualizarMultaDto actualizarMultaDto) {
        Multa multaExistente = multaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Multa no encontrada con ID: " + id));

        // Actualizar solo los campos proporcionados
        if (actualizarMultaDto.fechaPago() != null) {
            multaExistente.setFechaPago(actualizarMultaDto.fechaPago());
        }
        if (actualizarMultaDto.estado() != null) {
            multaExistente.setEstado(actualizarMultaDto.estado());
        }
        if (actualizarMultaDto.descripcion() != null) {
            multaExistente.setDescripcion(actualizarMultaDto.descripcion());
        }

        Multa multaActualizada = multaRepository.save(multaExistente);
        return convertirADTO(multaActualizada);
    }

    public void eliminar(Long id) {
        if (!multaRepository.existsById(id)) {
            throw new RuntimeException("Multa no encontrada con ID: " + id);
        }
        multaRepository.deleteById(id);
    }

    // ==================== CONSULTAS ESPECÍFICAS ====================

    public List<MultaDto> obtenerPorUsuario(Long usuarioId) {
        return multaRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public List<MultaDto> obtenerPorPrestamo(Long prestamoId) {
        return multaRepository.findByPrestamoId(prestamoId)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public List<MultaDto> obtenerPorEstado(EstadoMulta estado) {
        return multaRepository.findByEstado(estado)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public List<MultaDto> obtenerMultasPendientesPorUsuario(Long usuarioId) {
        return multaRepository.findMultasPendientesByUsuarioId(usuarioId)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public List<MultaDto> obtenerPorRangoFechas(LocalDate inicio, LocalDate fin) {
        return multaRepository.findByFechaMultaBetween(inicio, fin)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public List<MultaDto> obtenerMultasPagadasEnPeriodo(LocalDate inicio, LocalDate fin) {
        return multaRepository.findMultasPagadasEnPeriodo(inicio, fin)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // ==================== MÉTODOS DE NEGOCIO ====================

    public BigDecimal calcularTotalMultasPendientesPorUsuario(Long usuarioId) {
        Double total = multaRepository.calcularTotalMultasPendientesByUsuarioId(usuarioId);
        return BigDecimal.valueOf(total != null ? total : 0.0);
    }

    public long contarMultasPendientesPorUsuario(Long usuarioId) {
        return multaRepository.countMultasPendientesByUsuarioId(usuarioId);
    }

    public void pagarMulta(Long multaId) {
        Multa multa = multaRepository.findById(multaId)
                .orElseThrow(() -> new RuntimeException("Multa no encontrada con ID: " + multaId));

        multa.setEstado(EstadoMulta.PAGADA);
        multa.setFechaPago(LocalDate.now());
        multaRepository.save(multa);
    }

    // Método para generar multa automática por préstamo vencido
    public void generarMultaPorVencimiento(Long prestamoId, Long usuarioId, BigDecimal monto, String descripcion) {
        CrearMultaDto multaDto = new CrearMultaDto(
                usuarioId,
                prestamoId,
                monto,
                LocalDate.now(),
                descripcion
        );
        crear(multaDto);
    }
}
