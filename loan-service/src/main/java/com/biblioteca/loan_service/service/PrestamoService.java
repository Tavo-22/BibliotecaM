package com.biblioteca.loan_service.service;

import com.biblioteca.loan_service.client.BookServiceClient;
import com.biblioteca.loan_service.client.UserServiceClient;
import com.biblioteca.loan_service.dto.*;
import com.biblioteca.loan_service.model.EstadoPrestamo;
import com.biblioteca.loan_service.model.Prestamo;
import com.biblioteca.loan_service.repository.PrestamoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private BookServiceClient bookServiceClient;

    // ==================== CONVERSIONES ====================

    // convertir entity -> dto
    private PrestamoDto convertirADTO(Prestamo prestamo) {
        try {
            // 🆕 OBTENER DATOS DE OTROS SERVICIOS
            UsuarioDto usuario = userServiceClient.obtenerUsuario(prestamo.getUsuarioId());

            EjemplarDto ejemplar = bookServiceClient.obtenerEjemplar(prestamo.getEjemplarId());

            LibroDto libro = bookServiceClient.obtenerLibro(ejemplar.libroId());
            return new PrestamoDto(
                    prestamo.getId(),
                    prestamo.getUsuarioId(),
                    usuario.nombre(), // usuarioNombre - se llenará después
                    prestamo.getEjemplarId(),
                    libro.titulo(), // libroTitulo - se llenará después
                    ejemplar.codigoBarras(), // codigoEjemplar - se llenará después
                    prestamo.getFechaPrestamo(),
                    prestamo.getFechaDevolucionPrevista(),
                    prestamo.getFechaDevolucionReal(),
                    prestamo.getEstado()
            );
        }catch (Exception e){
            // 🛡️ Manejar errores si un servicio no está disponible
            return new PrestamoDto(
                    prestamo.getId(),
                    prestamo.getUsuarioId(),
                    "No disponible",      // Valor por defecto
                    prestamo.getEjemplarId(),
                    "No disponible",      // Valor por defecto
                    "No disponible",      // Valor por defecto
                    prestamo.getFechaPrestamo(),
                    prestamo.getFechaDevolucionPrevista(),
                    prestamo.getFechaDevolucionReal(),
                    prestamo.getEstado()
            );
        }
    }

    //convertir dto -> entity
    private Prestamo convertirAEntidad(CrearPrestamoDto dto) {
        Prestamo prestamo = new Prestamo();
        prestamo.setUsuarioId(dto.usuarioId());
        prestamo.setEjemplarId(dto.ejemplarId());
        prestamo.setFechaPrestamo(dto.fechaPrestamo());
        prestamo.setFechaDevolucionPrevista(dto.fechaDevolucionPrevista());
        prestamo.setEstado(EstadoPrestamo.ACTIVO); // Por defecto
        return prestamo;
    }

    // ==================== CRUD BÁSICO ====================
    //obtener todos los prestamos
    public List<PrestamoDto> obtenerTodos() {
        return prestamoRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    //obtener prestamos por id
    public PrestamoDto obtenerPorId(Long id) {
        Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado con ID: " + id));
        return convertirADTO(prestamo);
    }

    //crear prestamo
    public PrestamoDto crear(CrearPrestamoDto crearPrestamoDto) {
        // Validar que el usuario no tenga préstamos activos en exceso
        long prestamosActivos = prestamoRepository.countPrestamosActivosByUsuarioId(crearPrestamoDto.usuarioId());
        if (prestamosActivos >= 3) { // Límite de 3 préstamos activos
            throw new RuntimeException("El usuario ya tiene el máximo de préstamos activos permitidos");
        }

        Prestamo prestamo = convertirAEntidad(crearPrestamoDto);
        Prestamo prestamoGuardado = prestamoRepository.save(prestamo);

        // TODO: Aquí iría el registro automático en historial

        return convertirADTO(prestamoGuardado);
    }

    //actualizar prestamos
    public PrestamoDto actualizar(Long id, ActualizarPrestamoDto actualizarPrestamoDto) {
        Prestamo prestamoExistente = prestamoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado con ID: " + id));

        // Actualizar solo los campos proporcionados
        if (actualizarPrestamoDto.fechaDevolucionReal() != null) {
            prestamoExistente.setFechaDevolucionReal(actualizarPrestamoDto.fechaDevolucionReal());
        }
        if (actualizarPrestamoDto.estado() != null) {
            prestamoExistente.setEstado(actualizarPrestamoDto.estado());
        }

        Prestamo prestamoActualizado = prestamoRepository.save(prestamoExistente);

        // TODO: Aquí iría el registro automático en historial

        return convertirADTO(prestamoActualizado);
    }

    //eliminar prestamo
    public void eliminar(Long id) {
        if (!prestamoRepository.existsById(id)) {
            throw new RuntimeException("Préstamo no encontrado con ID: " + id);
        }
        prestamoRepository.deleteById(id);
    }

    // ==================== CONSULTAS ESPECÍFICAS ====================

    //obtener prestamos por usuario
    public List<PrestamoDto> obtenerPorUsuario(Long usuarioId) {
        return prestamoRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    //obtener prestamos por ejemplar
    public List<PrestamoDto> obtenerPorEjemplar(Long ejemplarId) {
        return prestamoRepository.findByEjemplarId(ejemplarId)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    //obtener prestamos por estado
    public List<PrestamoDto> obtenerPorEstado(EstadoPrestamo estado) {
        return prestamoRepository.findByEstado(estado)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    //obtener prestamos activos por usuario
    public List<PrestamoDto> obtenerPrestamosActivosPorUsuario(Long usuarioId) {
        return prestamoRepository.findPrestamosActivosByUsuarioId(usuarioId)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    //obtener prestamos vacios
    public List<PrestamoDto> obtenerPrestamosVencidos() {
        return prestamoRepository.findPrestamosVencidos(LocalDate.now())
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    //contar prestamos activos por usuario
    public long contarPrestamosActivosPorUsuario(Long usuarioId) {
        return prestamoRepository.countPrestamosActivosByUsuarioId(usuarioId);
    }

    //obtener prestamos por rango de fechas
    public List<PrestamoDto> obtenerPorRangoFechas(LocalDate inicio, LocalDate fin) {
        return prestamoRepository.findByFechaPrestamoBetween(inicio, fin)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
}
