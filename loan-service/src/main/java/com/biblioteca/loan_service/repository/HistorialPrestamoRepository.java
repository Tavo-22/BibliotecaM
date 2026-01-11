package com.biblioteca.loan_service.repository;

import com.biblioteca.loan_service.model.HistorialPrestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HistorialPrestamoRepository extends JpaRepository<HistorialPrestamo,Long> {
    // Buscar historial por préstamo
    List<HistorialPrestamo> findByPrestamoId(Long prestamoId);

    // Buscar historial por usuario que realizó la acción
    List<HistorialPrestamo> findByUsuarioId(Long usuarioId);

    // Buscar historial por tipo de acción
    List<HistorialPrestamo> findByAccion(String accion);

    // Buscar historial por rango de fechas
    @Query("SELECT h FROM HistorialPrestamo h WHERE h.fechaAccion BETWEEN :inicio AND :fin")
    List<HistorialPrestamo> findByFechaAccionBetween(
            @Param("inicio") LocalDateTime inicio,
            @Param("fin") LocalDateTime fin
    );

    // Buscar últimas N acciones de un préstamo
    @Query("SELECT h FROM HistorialPrestamo h WHERE h.prestamoId = :prestamoId ORDER BY h.fechaAccion DESC")
    List<HistorialPrestamo> findTopNByPrestamoIdOrderByFechaAccionDesc(@Param("prestamoId") Long prestamoId);

    // Buscar historial completo de un préstamo ordenado por fecha
    @Query("SELECT h FROM HistorialPrestamo h WHERE h.prestamoId = :prestamoId ORDER BY h.fechaAccion ASC")
    List<HistorialPrestamo> findHistorialCompletoByPrestamoId(@Param("prestamoId") Long prestamoId);

    // Contar acciones por tipo
    @Query("SELECT COUNT(h) FROM HistorialPrestamo h WHERE h.accion = :accion")
    long countByAccion(@Param("accion") String accion);

    // Buscar acciones recientes (últimos 30 días)
    @Query("SELECT h FROM HistorialPrestamo h WHERE h.fechaAccion >= :fechaInicio")
    List<HistorialPrestamo> findAccionesRecientes(@Param("fechaInicio") LocalDateTime fechaInicio);
}
