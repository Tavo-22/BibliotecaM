package com.biblioteca.book_service.service;

import com.biblioteca.book_service.dto.ActualizarEjemplarDto;
import com.biblioteca.book_service.dto.CrearEjemplarDto;
import com.biblioteca.book_service.dto.EjemplarDto;
import com.biblioteca.book_service.model.Ejemplar;
import com.biblioteca.book_service.model.EstadoEjemplar;
import com.biblioteca.book_service.model.Libro;
import com.biblioteca.book_service.repository.EjemplarRepository;
import com.biblioteca.book_service.repository.LibroRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EjemplarService {

    @Autowired
    private EjemplarRepository ejemplarRepository;

    @Autowired
    private LibroRepository libroRepository;

    //Convertir entity -> dto
    private EjemplarDto convertirDto(Ejemplar ejemplar){
        return new EjemplarDto(
                ejemplar.getId(),
                ejemplar.getCodigoBarras(),
                ejemplar.getEstado(),
                ejemplar.getUbicacion(),
                ejemplar.getLibro().getId(),
                ejemplar.getLibro().getTitulo()
        );
    }

    //convertir dto -> Entity
    private Ejemplar convertirEntity(CrearEjemplarDto crearEjemplarDto){
        Ejemplar ejemplar = new Ejemplar();
        ejemplar.setCodigoBarras(crearEjemplarDto.codigoBarras());
        ejemplar.setEstado(crearEjemplarDto.estado() != null ? crearEjemplarDto.estado() : EstadoEjemplar.DISPONIBLE);
        ejemplar.setUbicacion(crearEjemplarDto.ubicacion());

        // Buscar y asignar el libro
        Libro libro = libroRepository.findById(crearEjemplarDto.libroId())
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con ID: " + crearEjemplarDto.libroId()));
        ejemplar.setLibro(libro);

        return ejemplar;
    }

    //obtener todos los ejemplares
    public List<EjemplarDto> obtenerTodos(){
        return ejemplarRepository.findAll()
                .stream()
                .map(this::convertirDto)
                .collect(Collectors.toList());
    }

    //obtener ejemplar por id
    public EjemplarDto obtenerPorId(Long id){
        Ejemplar ejemplar = ejemplarRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Ejemplar no encontrador con ID "+id));
        return convertirDto(ejemplar);
    }

    //obtener ejemplar por codigo de barras
    public EjemplarDto obtenerPorCodigoBarras(String codigo){
        Ejemplar ejemplar = ejemplarRepository.findByCodigoBarras(codigo)
                .orElseThrow(()->new RuntimeException("Ejemplar no encontrador con código: "+codigo));
        return convertirDto(ejemplar);
    }

    //obtener todos los ejemplares por libro
    public List<EjemplarDto> obtenerPorLibro(Long id){
        return ejemplarRepository.findByLibroId(id)
                .stream()
                .map(this::convertirDto)
                .collect(Collectors.toList());
    }

    //obtener ejemplares por estado
    public List<EjemplarDto> obtenerPorEstado(EstadoEjemplar estado){
        return ejemplarRepository.findByEstado(estado)
                .stream()
                .map(this::convertirDto)
                .collect(Collectors.toList());
    }

    //obtener ejemplares disponibles de un libro
    public List<EjemplarDto> obtenerDisponiblePorLibro(Long libroId){
        return ejemplarRepository.findEjemplaresDisponiblesByLibroId(libroId)
                .stream()
                .map(this::convertirDto)
                .collect(Collectors.toList());
    }

    //obtener ejempalres por ubicacion
    public List<EjemplarDto> obtenerPorUbicacion(String ubicacion){
        return ejemplarRepository.findByUbicacionContainingIgnoreCase(ubicacion)
                .stream()
                .map(this::convertirDto)
                .collect(Collectors.toList());
    }

    //contar ejemplares por estado y libro
    public long contarPorLibroYEstado(Long libroId, EstadoEjemplar estadoEjemplar){
        return ejemplarRepository.countByLibroIdAndEstado(libroId,estadoEjemplar);
    }

    //crear nuevo ejemplar
    @Transactional
    public EjemplarDto crear(CrearEjemplarDto crearEjemplarDto){
        //verificar si el codigo de barras y existe
        if(ejemplarRepository.existsByCodigoBarras(crearEjemplarDto.codigoBarras())){
            throw new RuntimeException("El código de barraas ya esta registrado: "+crearEjemplarDto.codigoBarras());
        }

        Ejemplar ejemplar = convertirEntity(crearEjemplarDto);
        Ejemplar ejemplarGuardado = ejemplarRepository.save(ejemplar);
        return convertirDto(ejemplarGuardado);
    }

    //actualizar ejemplar
    @Transactional
    public EjemplarDto actualizar(Long id, ActualizarEjemplarDto actualizarEjemplarDto){
        Ejemplar ejemplarExistente = ejemplarRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Ejemplar no encontrado con Id: "+id));

        //actualizar solo los campos proporcionados
        if(actualizarEjemplarDto.estado() != null){
            ejemplarExistente.setEstado(actualizarEjemplarDto.estado());
        }

        if(actualizarEjemplarDto.ubicacion() != null){
            ejemplarExistente.setUbicacion(actualizarEjemplarDto.ubicacion());
        }

        Ejemplar ejemplarActualizado = ejemplarRepository.save(ejemplarExistente);
        return convertirDto(ejemplarActualizado);

    }

    //Eliminar ejemplar
    @Transactional
    public void eliminar(Long id){
        if(!ejemplarRepository.existsById(id)){
            throw new RuntimeException("Ejemplar no eonctrado con ID: "+id);
        }
        ejemplarRepository.deleteById(id);
    }
}
