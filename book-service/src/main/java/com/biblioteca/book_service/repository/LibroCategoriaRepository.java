package com.biblioteca.book_service.repository;

import com.biblioteca.book_service.model.LibroCategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroCategoriaRepository extends JpaRepository<LibroCategoria,Long> {
    // Buscar relaciones por libro
    List<LibroCategoria> findByLibroId(Long libroId);

    // Buscar relaciones por categoría
    List<LibroCategoria> findByCategoriaId(Long categoriaId);

    // Verificar si existe relación libro-categoría
    boolean existsByLibroIdAndCategoriaId(Long libroId, Long categoriaId);

    // Buscar categorías de un libro específico
    @Query("SELECT lc.categoria.id FROM LibroCategoria lc WHERE lc.libro.id = :libroId")
    List<Long> findCategoriaIdsByLibroId(@Param("libroId") Long libroId);

    // Buscar libros de una categoría específica
    @Query("SELECT lc.libro.id FROM LibroCategoria lc WHERE lc.categoria.id = :categoriaId")
    List<Long> findLibroIdsByCategoriaId(@Param("categoriaId") Long categoriaId);

    // Eliminar relación específica
    void deleteByLibroIdAndCategoriaId(Long libroId, Long categoriaId);
}
