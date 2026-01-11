package com.biblioteca.loan_service.service;

import com.biblioteca.loan_service.dto.ActualizarReservaDto;
import com.biblioteca.loan_service.dto.CrearReservaDto;
import com.biblioteca.loan_service.dto.ReservaDto;
import com.biblioteca.loan_service.model.EstadoReserva;
import com.biblioteca.loan_service.model.Reserva;
import com.biblioteca.loan_service.repository.ReservaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    // ==================== CONVERSIONES ====================

    private ReservaDto convertirADTO(Reserva reserva) {
        return new ReservaDto(
                reserva.getId(),
                reserva.getUsuarioId(),
                null, // usuarioNombre - se llenará después
                reserva.getLibroId(),
                null, // libroTitulo - se llenará después
                reserva.getFechaReserva(),
                reserva.getFechaExpiracion(),
                reserva.getEstado()
        );
    }

    private Reserva convertirAEntidad(CrearReservaDto dto) {
        Reserva reserva = new Reserva();
        reserva.setUsuarioId(dto.usuarioId());
        reserva.setLibroId(dto.libroId());
        reserva.setFechaReserva(dto.fechaReserva());
        reserva.setFechaExpiracion(dto.fechaExpiracion());
        reserva.setEstado(EstadoReserva.ACTIVA); // Por defecto
        return reserva;
    }

    // ==================== CRUD BÁSICO ====================

    public List<ReservaDto> obtenerTodas() {
        return reservaRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public ReservaDto obtenerPorId(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada con ID: " + id));
        return convertirADTO(reserva);
    }

    public ReservaDto crear(CrearReservaDto crearReservaDto) {
        // Validar que el usuario no tenga reserva activa para el mismo libro
        boolean tieneReservaActiva = reservaRepository.existsReservaActivaByUsuarioIdAndLibroId(
                crearReservaDto.usuarioId(), crearReservaDto.libroId());

        if (tieneReservaActiva) {
            throw new RuntimeException("El usuario ya tiene una reserva activa para este libro");
        }

        Reserva reserva = convertirAEntidad(crearReservaDto);
        Reserva reservaGuardada = reservaRepository.save(reserva);
        return convertirADTO(reservaGuardada);
    }

    public ReservaDto actualizar(Long id, ActualizarReservaDto actualizarReservaDto) {
        Reserva reservaExistente = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada con ID: " + id));

        if (actualizarReservaDto.estado() != null) {
            reservaExistente.setEstado(actualizarReservaDto.estado());
        }

        Reserva reservaActualizada = reservaRepository.save(reservaExistente);
        return convertirADTO(reservaActualizada);
    }

    public void eliminar(Long id) {
        if (!reservaRepository.existsById(id)) {
            throw new RuntimeException("Reserva no encontrada con ID: " + id);
        }
        reservaRepository.deleteById(id);
    }

    // ==================== CONSULTAS ESPECÍFICAS ====================

    public List<ReservaDto> obtenerPorUsuario(Long usuarioId) {
        return reservaRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public List<ReservaDto> obtenerPorLibro(Long libroId) {
        return reservaRepository.findByLibroId(libroId)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public List<ReservaDto> obtenerPorEstado(EstadoReserva estado) {
        return reservaRepository.findByEstado(estado)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public List<ReservaDto> obtenerReservasActivasPorUsuario(Long usuarioId) {
        return reservaRepository.findReservasActivasByUsuarioId(usuarioId)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public List<ReservaDto> obtenerReservasActivasPorLibro(Long libroId) {
        return reservaRepository.findReservasActivasByLibroId(libroId)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public List<ReservaDto> obtenerReservasExpiradas() {
        return reservaRepository.findReservasExpiradas(LocalDate.now())
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public long contarReservasActivasPorLibro(Long libroId) {
        return reservaRepository.countReservasActivasByLibroId(libroId);
    }

    // ==================== MÉTODOS DE NEGOCIO ====================

    public void expirarReservasAutomaticamente() {
        List<Reserva> reservasExpiradas = reservaRepository.findReservasExpiradas(LocalDate.now());
        for (Reserva reserva : reservasExpiradas) {
            reserva.setEstado(EstadoReserva.EXPIRADA);
            reservaRepository.save(reserva);
        }
    }
}
