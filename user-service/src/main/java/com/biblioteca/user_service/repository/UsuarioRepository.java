package com.biblioteca.user_service.repository;

import com.biblioteca.user_service.model.TipoMembresia;
import com.biblioteca.user_service.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    // Consulta derivada - busca por email
    Optional<Usuario> findByEmail(String email);

    // Consulta derivada - busca usuarios activos
    List<Usuario> findByActivoTrue();

    // Consulta derivada - busca por tipo de membresía
    List<Usuario> findByTipoMembresia(TipoMembresia tipoMembresia);

    // Consulta JPQL personalizada - busca usuarios con email que contenga un texto
    @Query("SELECT u FROM Usuario u WHERE u.email LIKE %:dominio%")
    List<Usuario> findByEmailContainingDomain(@Param("dominio") String dominio);

    // Consulta JPQL - cuenta usuarios activos
    @Query("SELECT COUNT(u) FROM Usuario u WHERE u.activo = true")
    long countActiveUsers();
}
