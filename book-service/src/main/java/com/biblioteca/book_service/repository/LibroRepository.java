package com.biblioteca.book_service.repository;

import com.biblioteca.book_service.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro,Long> {
    // Buscar libro por ISBN
    Optional<Libro> findByIsbn(String isbn);

    // Buscar libros por título (contiene)
    List<Libro> findByTituloContainingIgnoreCase(String titulo);

    // Buscar libros por editorial
    List<Libro> findByEditorial(String editorial);

    // Buscar libros por año de publicación
    List<Libro> findByAnoPublicacion(Integer anoPublicacion);

    // Buscar libros por rango de años
    @Query("SELECT l FROM Libro l WHERE l.anoPublicacion BETWEEN :inicio AND :fin")
    List<Libro> findByAnoPublicacionBetween(
            @Param("inicio") Integer inicio,
            @Param("fin") Integer fin
    );

    // Buscar libros que contengan texto en título o descripción
    @Query("SELECT l FROM Libro l WHERE LOWER(l.titulo) LIKE LOWER(CONCAT('%', :texto, '%')) OR LOWER(l.descripcion) LIKE LOWER(CONCAT('%', :texto, '%'))")
    List<Libro> buscarPorTituloODescripcion(@Param("texto") String texto);

    // Contar libros por editorial
    @Query("SELECT COUNT(l) FROM Libro l WHERE l.editorial = :editorial")
    long countByEditorial(@Param("editorial") String editorial);
}
