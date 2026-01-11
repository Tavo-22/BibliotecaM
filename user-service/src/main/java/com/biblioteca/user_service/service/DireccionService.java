package com.biblioteca.user_service.service;

import com.biblioteca.user_service.dto.CrearDireccionDto;
import com.biblioteca.user_service.dto.DireccionDto;
import com.biblioteca.user_service.model.Direccion;
import com.biblioteca.user_service.model.Usuario;
import com.biblioteca.user_service.repository.DireccionRepository;
import com.biblioteca.user_service.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DireccionService {

    @Autowired
    private DireccionRepository direccionRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    //Conversion Entity->Dto
    private DireccionDto convertirDto(Direccion direccion){
        return new DireccionDto(
                direccion.getId(),
                direccion.getCiudad(),
                direccion.getCodigoPostal(),
                direccion.getDireccion(),
                direccion.getUser().getId()
        );
    }

    //Crear Dto->Entity
    private Direccion convertirEntidad(CrearDireccionDto crearDireccionDto){
        Direccion direccion = new Direccion();
        direccion.setCiudad(crearDireccionDto.ciudad());
        direccion.setCiudad(crearDireccionDto.codigoPostal());
        direccion.setDireccion(crearDireccionDto.direccion());

        // Buscar y asignar el usuario
        Usuario usuario = usuarioRepository.findById(crearDireccionDto.usuarioId())
                .orElseThrow(()-> new RuntimeException("Usuario no encontrado con el ID"+crearDireccionDto.usuarioId()));

        direccion.setUser(usuario);

        return direccion;
    }

    //Listar todas las direcciones
    public List<DireccionDto> obtenerTodas(){
        return direccionRepository.findAll()
                .stream()
                .map(this::convertirDto)
                .collect(Collectors.toList());
    }

    //Obtener Direccion por id
    public DireccionDto obtenerPorId(Long id){
        Direccion direccion = direccionRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Direccion no encontrada con el ID "+id));

        return convertirDto(direccion);
    }

    //Obtener direccion por id del usuario
    public List<DireccionDto> obtenerPorUsuario(Long usuarioId){
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(()->new RuntimeException("Usuario no encontrado con ID "+usuarioId));

        return direccionRepository.findByUsuario(usuario)
                .stream()
                .map(this::convertirDto)
                .collect(Collectors.toList());
    }

    //Obtener direccion por ciudad
    public List<DireccionDto> obtenerPorCiudad(String ciudad){

        return direccionRepository.findByCiudad(ciudad)
                .stream()
                .map(this::convertirDto)
                .collect(Collectors.toList());

    }

    public List<DireccionDto> obtenerPorCodigoPostal(String codigoPostal){
        return direccionRepository.findByCodigoPostal(codigoPostal)
                .stream()
                .map(this::convertirDto)
                .collect(Collectors.toList());
    }

    //Crear Direccion
    public DireccionDto crear(CrearDireccionDto crearDireccionDto){
        Direccion direccion = convertirEntidad(crearDireccionDto);
        Direccion direccionGuardada = direccionRepository.save(direccion);

        return convertirDto(direccionGuardada);
    }

    //actulizar Direccion
    public DireccionDto actualizar(Long id, CrearDireccionDto actualizarDireccionDto){
        Direccion direccionExistente = direccionRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Direccion no encontrada con el ID "+id));

        //Actualizar campos
        direccionExistente.setCiudad(actualizarDireccionDto.ciudad());
        direccionExistente.setCodigoPostal(actualizarDireccionDto.codigoPostal());
        direccionExistente.setDireccion(actualizarDireccionDto.direccion());

        Direccion direccionActualizada = direccionRepository.save(direccionExistente);

        return convertirDto(direccionActualizada);
    }

    //Eliminar Direccion
    public void eliminar(Long id){
        if(!direccionRepository.existsById(id)){
            throw new RuntimeException("Direccion no enocntrada con el ID "+id);
        }
        direccionRepository.deleteById(id);
    }
}
