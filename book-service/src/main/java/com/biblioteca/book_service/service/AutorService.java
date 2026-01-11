package com.biblioteca.book_service.service;

import com.biblioteca.book_service.dto.ActualizarAutorDto;
import com.biblioteca.book_service.dto.AutorDto;
import com.biblioteca.book_service.dto.CrearAutorDto;
import com.biblioteca.book_service.model.Autor;
import com.biblioteca.book_service.repository.AutorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    //Convertir Entity -> Dto
    private AutorDto convertirDto(Autor autor){
        return new AutorDto(
                autor.getId(),
                autor.getNombre(),
                autor.getNacionalidad(),
                autor.getFechaNacimiento(),
                autor.getBiografia()
        );
    }

    //Convertir Dto -> Entity
    private Autor convertirEntity(CrearAutorDto autorDto){
        Autor autor = new Autor();
        autor.setNombre(autorDto.nombre());
        autor.setNacionalidad(autorDto.nacionalidad());
        autor.setFechaNacimiento(autorDto.fechaNacimiento());
        autor.setBiografia(autorDto.biografia());
        return autor;
    }

    //Obtener todos los autores
    public List<AutorDto> obtenerTodos(){
        return autorRepository.findAll()
                .stream()
                .map(this::convertirDto)
                .collect(Collectors.toList());
    }

    //Obtener autores ordenados por nombres
    public List<AutorDto> obtenerTodosOrdenadosPorNombre(){
        return autorRepository.findAllByOrderByNombreAsc()
                .stream()
                .map(this::convertirDto)
                .collect(Collectors.toList());
    }

    //Obtener autor por id
    public AutorDto obtenerPorId(Long id){
        Autor autor = autorRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Autor no encontrado con ID "+id));
        return convertirDto(autor);
    }

    //Obtener autor por nombre exacto
    public AutorDto obtenerPorNombre(String nombre){
        Autor autor = autorRepository.findByNombre(nombre)
                .orElseThrow(()->new RuntimeException("Autor no encontrado con nombre "+nombre));
        return convertirDto(autor);

    }

    //buscar autores por nombre contenga
    public List<AutorDto> buscarPorNombre(String nombre){
        return autorRepository.findByNombreContainingIgnoreCase(nombre)
                .stream()
                .map(this::convertirDto)
                .collect(Collectors.toList());
    }

    //buscar autores por nacionalidad
    public List<AutorDto> buscarPorNacionalidad(String nacionalidad){
        return autorRepository.findByNacionalidad(nacionalidad)
                .stream()
                .map(this::convertirDto)
                .collect(Collectors.toList());
    }

    //buscar autore por bibliografia
    public List<AutorDto> buscarPorBibliografia(String bibliografia){
        return autorRepository.findByBiografiaContaining(bibliografia)
                .stream()
                .map(this::convertirDto)
                .collect(Collectors.toList());
    }

    //contar autores por nacionlidad
    public long contarPorNacionlidad(String nacionalidad){
        return autorRepository.countByNacionalidad(nacionalidad);
    }

    //Crear nuevo autor
    @Transactional
    public AutorDto crear(CrearAutorDto crearAutorDto) {
        Autor autor = convertirEntity(crearAutorDto);
        Autor autorGuardado = autorRepository.save(autor);
        return convertirDto(autorGuardado);
    }

    //Actualizar autor
    @Transactional
    public AutorDto actualizar(Long id, ActualizarAutorDto actualizarAutorDto){
        Autor autorExistente = autorRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Autor no encontrado con ID "+id));

        //actualizar solo campos proporciodnados
        if (actualizarAutorDto.nombre() != null) {
            autorExistente.setNombre(actualizarAutorDto.nombre());
        }
        if (actualizarAutorDto.nacionalidad() != null) {
            autorExistente.setNacionalidad(actualizarAutorDto.nacionalidad());
        }
        if (actualizarAutorDto.fechaNacimiento() != null) {
            autorExistente.setFechaNacimiento(actualizarAutorDto.fechaNacimiento());
        }
        if (actualizarAutorDto.biografia() != null) {
            autorExistente.setBiografia(actualizarAutorDto.biografia());
        }

        Autor autorActualizado = autorRepository.save(autorExistente);
        return convertirDto(autorActualizado);
    }

    //Eliminar autor
    @Transactional
    public void eliminar(Long id){
        if(!autorRepository.existsById(id)){
            throw new RuntimeException("Autor no encontrado con ID "+id);
        }
        autorRepository.deleteById(id);
    }
}
