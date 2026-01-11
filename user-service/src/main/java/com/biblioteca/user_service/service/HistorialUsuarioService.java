package com.biblioteca.user_service.service;

import com.biblioteca.user_service.dto.CrearHistorialUsuarioDto;
import com.biblioteca.user_service.dto.HistorialUsuarioDto;
import com.biblioteca.user_service.model.HistorialUsuario;
import com.biblioteca.user_service.model.Usuario;
import com.biblioteca.user_service.repository.HistorialUsuarioRepository;
import com.biblioteca.user_service.repository.UsuarioRepository;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistorialUsuarioService {

    @Autowired
    private HistorialUsuarioRepository historialUsuarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    //converision entity -> dto
    private HistorialUsuarioDto convertirDto(HistorialUsuario historialUsuario){
        return new HistorialUsuarioDto(
                historialUsuario.getId(),
                historialUsuario.getAccion(),
                historialUsuario.getDescripcion(),
                historialUsuario.getFechaAccion(),
                historialUsuario.getUser().getId()
        );
    }

    //conversion crearDto -> Entity
    private HistorialUsuario convertirEntidad(CrearHistorialUsuarioDto crearHistorialUsuarioDto){
        HistorialUsuario historialUsuario = new HistorialUsuario();
        historialUsuario.setAccion(crearHistorialUsuarioDto.accion());
        historialUsuario.setDescripcion(crearHistorialUsuarioDto.descripcion());
        historialUsuario.setFechaAccion(LocalDateTime.now());

        //Buscar y asignar el usuario
        Usuario usuario = usuarioRepository.findById(crearHistorialUsuarioDto.usuarioId())
                .orElseThrow(()-> new RuntimeException("Usuario no encontrador con ID "+crearHistorialUsuarioDto.usuarioId()));

        historialUsuario.setUser(usuario);

        return historialUsuario;
    }

    //Obtener todo el historial
    public List<HistorialUsuarioDto> obtenerTodoElHistorial(){
        return historialUsuarioRepository.findAll()
                .stream()
                .map(this::convertirDto)
                .collect(Collectors.toList());
    }

    //Obtener historial por usuario
    public List<HistorialUsuarioDto> obtenerHistorialPorUsuario(Long usuarioId){
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(()->new RuntimeException("Usuario no encontrado con ID "+usuarioId));

        return historialUsuarioRepository.findByUsuario(usuario)
                .stream()
                .map(this::convertirDto)
                .collect(Collectors.toList());
    }

    //Obtener historial por accion
    public List<HistorialUsuarioDto> obtenerHistorialPorAccion(String accion){
        return historialUsuarioRepository.findByAccion(accion)
                .stream()
                .map(this::convertirDto)
                .collect(Collectors.toList());
    }

    //Crear registro de historial
    public HistorialUsuarioDto crearRegistroHistorial(CrearHistorialUsuarioDto crearHistorialUsuarioDto){

        HistorialUsuario historialUsuario = convertirEntidad(crearHistorialUsuarioDto);
        HistorialUsuario historialGuardado = historialUsuarioRepository.save(historialUsuario);
        return convertirDto(historialGuardado);
    }

    //  MÉTODOS ESPECÍFICOS PARA ACCIONES COMUNES
    public void registrarCreacionUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + usuarioId));

        CrearHistorialUsuarioDto historialDto = new CrearHistorialUsuarioDto(
                "REGISTRO",
                "Usuario creado exitosamente",
                usuario.getId()
        );
        crearRegistroHistorial(historialDto);
    }

    public void registrarActualizacionPerfil(Long usuarioId, String cambios) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + usuarioId));

        CrearHistorialUsuarioDto historialDto = new CrearHistorialUsuarioDto(
                "ACTUALIZACION",
                "Perfil actualizado: " + cambios,
                usuario.getId()
        );
        crearRegistroHistorial(historialDto);
    }

    public void registrarCambioMembresia(Long usuarioId, String nuevaMembresia) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + usuarioId));

        CrearHistorialUsuarioDto historialDto = new CrearHistorialUsuarioDto(
                "CAMBIO_MEMBRESIA",
                "Membresía cambiada a: " + nuevaMembresia,
                usuario.getId()
        );
        crearRegistroHistorial(historialDto);
    }


}
