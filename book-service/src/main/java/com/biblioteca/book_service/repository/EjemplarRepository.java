package com.biblioteca.book_service.repository;

import com.biblioteca.book_service.model.Ejemplar;
import com.biblioteca.book_service.model.EstadoEjemplar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EjemplarRepository extends JpaRepository<Ejemplar,Long> {
    // Buscar ejemplares por libro
    List<Ejemplar> findByLibroId(Long libroId);

    // Buscar ejemplares por estado
    List<Ejemplar> findByEstado(EstadoEjemplar estado);

    // Buscar ejemplar por código de barras
    Optional<Ejemplar> findByCodigoBarras(String codigoBarras);

    // Buscar ejemplares disponibles de un libro
    @Query("SELECT e FROM Ejemplar e WHERE e.libro.id = :libroId AND e.estado = 'DISPONIBLE'")
    List<Ejemplar> findEjemplaresDisponiblesByLibroId(@Param("libroId") Long libroId);

    // Contar ejemplares por estado y libro
    @Query("SELECT COUNT(e) FROM Ejemplar e WHERE e.libro.id = :libroId AND e.estado = :estado")
    long countByLibroIdAndEstado(@Param("libroId") Long libroId, @Param("estado") EstadoEjemplar estado);

    // Buscar ejemplares por ubicación
    List<Ejemplar> findByUbicacionContainingIgnoreCase(String ubicacion);

    // Verificar si existe código de barras
    boolean existsByCodigoBarras(String codigoBarras);
}
