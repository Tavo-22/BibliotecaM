package com.biblioteca.user_service.repository;

import com.biblioteca.user_service.model.HistorialUsuario;
import com.biblioteca.user_service.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HistorialUsuarioRepository extends JpaRepository<HistorialUsuario,Long> {
    // Buscar historial de un usuario
    List<HistorialUsuario> findByUsuario(Usuario usuario);

    // Buscar historial por ID de usuario
    @Query("SELECT h FROM HistorialUsuario h WHERE h.usuario.id = :userId")
    List<HistorialUsuario> findByUsuarioId(@Param("userId") Long userId);

    // Buscar por tipo de acción
    List<HistorialUsuario> findByAccion(String accion);

    // Buscar historial en un rango de fechas
    @Query("SELECT h FROM HistorialUsuario h WHERE h.fechaAccion BETWEEN :inicio AND :fin")
    List<HistorialUsuario> findByFechaAccionBetween(
            @Param("inicio") LocalDateTime inicio,
            @Param("fin") LocalDateTime fin
    );

    // Buscar las últimas N acciones de un usuario
    @Query("SELECT h FROM HistorialUsuario h WHERE h.usuario.id = :userId ORDER BY h.fechaAccion DESC")
    List<HistorialUsuario> findTopNByUsuarioIdOrderByFechaAccionDesc(
            @Param("userId") Long userId
    );

    // Contar acciones por tipo
    @Query("SELECT COUNT(h) FROM HistorialUsuario h WHERE h.accion = :accion")
    long countByAccion(@Param("accion") String accion);
}
