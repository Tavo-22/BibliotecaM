package com.biblioteca.loan_service.repository;

import com.biblioteca.loan_service.model.EstadoPrestamo;
import com.biblioteca.loan_service.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo,Long> {
    // Buscar préstamos por usuario
    List<Prestamo> findByUsuarioId(Long usuarioId);

    // Buscar préstamos por ejemplar
    List<Prestamo> findByEjemplarId(Long ejemplarId);

    // Buscar préstamos por estado
    List<Prestamo> findByEstado(EstadoPrestamo estado);

    // Buscar préstamos activos de un usuario
    @Query("SELECT p FROM Prestamo p WHERE p.usuarioId = :usuarioId AND p.estado = 'ACTIVO'")
    List<Prestamo> findPrestamosActivosByUsuarioId(@Param("usuarioId") Long usuarioId);

    // Buscar préstamos vencidos
    @Query("SELECT p FROM Prestamo p WHERE p.fechaDevolucionPrevista < :fechaActual AND p.estado = 'ACTIVO'")
    List<Prestamo> findPrestamosVencidos(@Param("fechaActual") LocalDate fechaActual);

    // Contar préstamos activos por usuario
    @Query("SELECT COUNT(p) FROM Prestamo p WHERE p.usuarioId = :usuarioId AND p.estado = 'ACTIVO'")
    long countPrestamosActivosByUsuarioId(@Param("usuarioId") Long usuarioId);

    // Buscar préstamos por rango de fechas
    @Query("SELECT p FROM Prestamo p WHERE p.fechaPrestamo BETWEEN :inicio AND :fin")
    List<Prestamo> findByFechaPrestamoBetween(
            @Param("inicio") LocalDate inicio,
            @Param("fin") LocalDate fin
    );
}
