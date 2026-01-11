package com.biblioteca.book_service.repository;

import com.biblioteca.book_service.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria,Long> {
    // Buscar categoría por nombre exacto
    Optional<Categoria> findByNombre(String nombre);

    // Buscar categorías por nombre (contiene)
    List<Categoria> findByNombreContainingIgnoreCase(String nombre);

    // Verificar si existe categoría por nombre
    boolean existsByNombre(String nombre);

    // Buscar categorías con descripción que contenga texto
    @Query("SELECT c FROM Categoria c WHERE LOWER(c.descripcion) LIKE LOWER(CONCAT('%', :texto, '%'))")
    List<Categoria> findByDescripcionContaining(@Param("texto") String texto);

    // Buscar categorías ordenadas por nombre
    List<Categoria> findAllByOrderByNombreAsc();
}
