package com.biblioteca.book_service.repository;

import com.biblioteca.book_service.model.LibroAutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroAutorRepository extends JpaRepository<LibroAutor, Long> {

    // Buscar relaciones por libro
    List<LibroAutor> findByLibroId(Long libroId);

    // Buscar relaciones por autor
    List<LibroAutor> findByAutorId(Long autorId);

    // Verificar si existe relación libro-autor
    boolean existsByLibroIdAndAutorId(Long libroId, Long autorId);

    // Buscar autores de un libro específico
    @Query("SELECT la.autor.id FROM LibroAutor la WHERE la.libro.id = :libroId")
    List<Long> findAutorIdsByLibroId(@Param("libroId") Long libroId);

    // Buscar libros de un autor específico
    @Query("SELECT la.libro.id FROM LibroAutor la WHERE la.autor.id = :autorId")
    List<Long> findLibroIdsByAutorId(@Param("autorId") Long autorId);

    // Eliminar relación específica
    void deleteByLibroIdAndAutorId(Long libroId, Long autorId);
}
