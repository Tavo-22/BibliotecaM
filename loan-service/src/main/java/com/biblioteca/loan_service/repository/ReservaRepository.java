package com.biblioteca.loan_service.repository;

import com.biblioteca.loan_service.model.EstadoReserva;
import com.biblioteca.loan_service.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva,Long> {
    // Buscar reservas por usuario
    List<Reserva> findByUsuarioId(Long usuarioId);

    // Buscar reservas por libro
    List<Reserva> findByLibroId(Long libroId);

    // Buscar reservas por estado
    List<Reserva> findByEstado(EstadoReserva estado);

    // Buscar reservas activas de un usuario
    @Query("SELECT r FROM Reserva r WHERE r.usuarioId = :usuarioId AND r.estado = 'ACTIVA'")
    List<Reserva> findReservasActivasByUsuarioId(@Param("usuarioId") Long usuarioId);

    // Buscar reservas activas de un libro
    @Query("SELECT r FROM Reserva r WHERE r.libroId = :libroId AND r.estado = 'ACTIVA'")
    List<Reserva> findReservasActivasByLibroId(@Param("libroId") Long libroId);

    // Buscar reservas expiradas
    @Query("SELECT r FROM Reserva r WHERE r.fechaExpiracion < :fechaActual AND r.estado = 'ACTIVA'")
    List<Reserva> findReservasExpiradas(@Param("fechaActual") LocalDate fechaActual);

    // Verificar si usuario tiene reserva activa para un libro
    @Query("SELECT COUNT(r) > 0 FROM Reserva r WHERE r.usuarioId = :usuarioId AND r.libroId = :libroId AND r.estado = 'ACTIVA'")
    boolean existsReservaActivaByUsuarioIdAndLibroId(@Param("usuarioId") Long usuarioId, @Param("libroId") Long libroId);

    // Contar reservas activas por libro
    @Query("SELECT COUNT(r) FROM Reserva r WHERE r.libroId = :libroId AND r.estado = 'ACTIVA'")
    long countReservasActivasByLibroId(@Param("libroId") Long libroId);
}
