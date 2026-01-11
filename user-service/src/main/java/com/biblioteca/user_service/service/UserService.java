package com.biblioteca.user_service.service;

import com.biblioteca.user_service.dto.ActualizarUsuarioDto;
import com.biblioteca.user_service.dto.CrearUsuarioDto;
import com.biblioteca.user_service.dto.UsuarioDto;
import com.biblioteca.user_service.model.Usuario;
import com.biblioteca.user_service.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // CONVERSIÓN: Entity -> DTO
    private UsuarioDto convertirADTO(Usuario usuario) {
        return new UsuarioDto(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getTipoMembresia(),
                usuario.getActivo()
        );
    }

    // CONVERSIÓN: CreateDTO -> Entity
    private Usuario convertirAEntidad(CrearUsuarioDto crearUsuarioDto) {
        Usuario usuario = new Usuario();
        usuario.setNombre(crearUsuarioDto.nombre());
        usuario.setEmail(crearUsuarioDto.email());
        usuario.setTipoMembresia(crearUsuarioDto.tipoMembresia());
        usuario.setActivo(true);
        return usuario;
    }

    // ✅ OBTENER TODOS LOS USUARIOS
    public List<UsuarioDto> obtenerTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // ✅ OBTENER USUARIO POR ID
    public UsuarioDto obtenerPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        return convertirADTO(usuario);
    }

    // ✅ OBTENER USUARIO POR EMAIL
    public UsuarioDto obtenerPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con email: " + email));
        return convertirADTO(usuario);
    }

    // ✅ BUSCAR USUARIOS POR DOMINIO DE EMAIL
    public List<UsuarioDto> buscarPorDominioEmail(String dominio) {
        return usuarioRepository.findByEmailContainingDomain(dominio)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // ✅ CONTAR USUARIOS ACTIVOS
    public long contarUsuariosActivos() {
        return usuarioRepository.countActiveUsers();
    }

    // ✅ CREAR NUEVO USUARIO
    public UsuarioDto crear(CrearUsuarioDto crearUsuarioDto) {
        // Verificar si el email ya existe
        if (usuarioRepository.findByEmail(crearUsuarioDto.email()).isPresent()) {
            throw new RuntimeException("El email ya está registrado: " + crearUsuarioDto.email());
        }

        Usuario usuario = convertirAEntidad(crearUsuarioDto);
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return convertirADTO(usuarioGuardado);
    }

    // ✅ ACTUALIZAR USUARIO
    public UsuarioDto actualizar(Long id, ActualizarUsuarioDto actualizarUsuarioDto) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        // Actualizar solo los campos proporcionados
        if (actualizarUsuarioDto.nombre() != null) {
            usuarioExistente.setNombre(actualizarUsuarioDto.nombre());
        }
        if (actualizarUsuarioDto.tipoMembresia() != null) {
            usuarioExistente.setTipoMembresia(actualizarUsuarioDto.tipoMembresia());
        }
        if (actualizarUsuarioDto.activo() != null) {
            usuarioExistente.setActivo(actualizarUsuarioDto.activo());
        }

        Usuario usuarioActualizado = usuarioRepository.save(usuarioExistente);
        return convertirADTO(usuarioActualizado);
    }

    // ✅ DESACTIVAR USUARIO (soft delete)
    public void desactivar(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        usuario.setActivo(false);
        usuarioRepository.save(usuario);
    }

    // ✅ ACTIVAR USUARIO
    public void activar(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        usuario.setActivo(true);
        usuarioRepository.save(usuario);
    }

    // ✅ ELIMINACIÓN FÍSICA (opcional)
    public void eliminarPermanentemente(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }
}
