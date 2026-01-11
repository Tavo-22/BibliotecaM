package com.biblioteca.loan_service.repository;

import com.biblioteca.loan_service.model.EstadoMulta;
import com.biblioteca.loan_service.model.Multa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MultaRepository extends JpaRepository<Multa,Long> {
    // Buscar multas por usuario
    List<Multa> findByUsuarioId(Long usuarioId);

    // Buscar multas por préstamo
    List<Multa> findByPrestamoId(Long prestamoId);

    // Buscar multas por estado
    List<Multa> findByEstado(EstadoMulta estado);

    // Buscar multas pendientes de un usuario
    @Query("SELECT m FROM Multa m WHERE m.usuarioId = :usuarioId AND m.estado = 'PENDIENTE'")
    List<Multa> findMultasPendientesByUsuarioId(@Param("usuarioId") Long usuarioId);

    // Buscar multas por rango de fechas
    @Query("SELECT m FROM Multa m WHERE m.fechaMulta BETWEEN :inicio AND :fin")
    List<Multa> findByFechaMultaBetween(
            @Param("inicio") LocalDate inicio,
            @Param("fin") LocalDate fin
    );

    // Calcular total de multas pendientes por usuario
    @Query("SELECT COALESCE(SUM(m.monto), 0) FROM Multa m WHERE m.usuarioId = :usuarioId AND m.estado = 'PENDIENTE'")
    Double calcularTotalMultasPendientesByUsuarioId(@Param("usuarioId") Long usuarioId);

    // Contar multas pendientes por usuario
    @Query("SELECT COUNT(m) FROM Multa m WHERE m.usuarioId = :usuarioId AND m.estado = 'PENDIENTE'")
    long countMultasPendientesByUsuarioId(@Param("usuarioId") Long usuarioId);

    // Buscar multas pagadas en un período
    @Query("SELECT m FROM Multa m WHERE m.estado = 'PAGADA' AND m.fechaPago BETWEEN :inicio AND :fin")
    List<Multa> findMultasPagadasEnPeriodo(
            @Param("inicio") LocalDate inicio,
            @Param("fin") LocalDate fin);
}
