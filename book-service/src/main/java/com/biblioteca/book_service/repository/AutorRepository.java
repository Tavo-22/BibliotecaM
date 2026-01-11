package com.biblioteca.book_service.repository;

import com.biblioteca.book_service.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor,Long> {
    // Buscar autor por nombre exacto
    Optional<Autor> findByNombre(String nombre);

    // Buscar autores por nombre (contiene)
    List<Autor> findByNombreContainingIgnoreCase(String nombre);

    // Buscar autores por nacionalidad
    List<Autor> findByNacionalidad(String nacionalidad);


    // Buscar autores con biografía que contenga texto
    @Query("SELECT a FROM Autor a WHERE LOWER(a.biografia) LIKE LOWER(CONCAT('%', :texto, '%'))")
    List<Autor> findByBiografiaContaining(@Param("texto") String texto);

    // Contar autores por nacionalidad
    @Query("SELECT COUNT(a) FROM Autor a WHERE a.nacionalidad = :nacionalidad")
    long countByNacionalidad(@Param("nacionalidad") String nacionalidad);

    // Buscar autores ordenados por nombre
    List<Autor> findAllByOrderByNombreAsc();
}
