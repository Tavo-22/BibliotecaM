package com.biblioteca.user_service.repository;

import com.biblioteca.user_service.model.Direccion;
import com.biblioteca.user_service.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DireccionRepository extends JpaRepository<Direccion,Long> {
    // Buscar todas las direcciones de un usuario
    List<Direccion> findByUsuario(Usuario usuario);

    // Buscar direcciones por ciudad
    List<Direccion> findByCiudad(String ciudad);

    // Buscar direcciones por usuario ID (alternativa)
    @Query("SELECT d FROM Direccion d WHERE d.usuario.id = :userId")
    List<Direccion> findByUsuarioId(@Param("userId") Long userId);

    // Contar direcciones por ciudad
    @Query("SELECT COUNT(d) FROM Direccion d WHERE d.ciudad = :ciudad")
    long countByCiudad(@Param("ciudad") String ciudad);

    // Buscar direcciones por código postal
    List<Direccion> findByCodigoPostal(String codigoPostal);
}
