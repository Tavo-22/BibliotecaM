package com.biblioteca.book_service.service;

import com.biblioteca.book_service.dto.AutorDto;
import com.biblioteca.book_service.dto.CrearLibroAutorDto;
import com.biblioteca.book_service.dto.LibroAutorDto;
import com.biblioteca.book_service.dto.LibroDto;
import com.biblioteca.book_service.model.Autor;
import com.biblioteca.book_service.model.Libro;
import com.biblioteca.book_service.model.LibroAutor;
import com.biblioteca.book_service.repository.AutorRepository;
import com.biblioteca.book_service.repository.LibroAutorRepository;
import com.biblioteca.book_service.repository.LibroRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibroAutorService {

    @Autowired
    private LibroAutorRepository libroAutorRepository;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private AutorService autorService;

    @Autowired
    private LibroService libroService;

    //convetir Entity->dt
    private LibroAutorDto convertirADTO(LibroAutor libroAutor) {
        return new LibroAutorDto(
                libroAutor.getId(),
                libroAutor.getLibro().getId(),
                libroAutor.getAutor().getId(),
                libroAutor.getLibro().getTitulo(),
                libroAutor.getAutor().getNombre()
        );
    }

    //convetir dto-entity
    private LibroAutor convertirAEntidad(CrearLibroAutorDto dto) {
        Libro libro = libroRepository.findById(dto.libroId())
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con ID: " + dto.libroId()));

        Autor autor = autorRepository.findById(dto.autorId())
                .orElseThrow(() -> new RuntimeException("Autor no encontrado con ID: " + dto.autorId()));

        LibroAutor libroAutor = new LibroAutor();
        libroAutor.setLibro(libro);
        libroAutor.setAutor(autor);

        return libroAutor;
    }

    // agregar autor al libro
    @Transactional
    public LibroAutorDto agregarAutorALibro(CrearLibroAutorDto crearLibroAutorDto) {
        // Verificar si la relación ya existe
        if (libroAutorRepository.existsByLibroIdAndAutorId(
                crearLibroAutorDto.libroId(), crearLibroAutorDto.autorId())) {
            throw new RuntimeException("El autor ya está asociado a este libro");
        }

        LibroAutor libroAutor = convertirAEntidad(crearLibroAutorDto);
        LibroAutor libroAutorGuardado = libroAutorRepository.save(libroAutor);
        return convertirADTO(libroAutorGuardado);
    }

    // obtener autores de un libro
    public List<LibroAutorDto> obtenerAutoresDeLibro(Long libroId) {
        return libroAutorRepository.findByLibroId(libroId)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // obtener libros de todos los autores
    public List<LibroAutorDto> obtenerLibrosDeAutor(Long autorId) {
        return libroAutorRepository.findByAutorId(autorId)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // Eliminar autor de un libro
    @Transactional
    public void eliminarAutorDeLibro(Long libroId, Long autorId) {
        if (!libroAutorRepository.existsByLibroIdAndAutorId(libroId, autorId)) {
            throw new RuntimeException("La relación libro-autor no existe");
        }
        libroAutorRepository.deleteByLibroIdAndAutorId(libroId, autorId);
    }

    // obtener todas las relaciones
    public List<LibroAutorDto> obtenerTodasLasRelaciones() {
        return libroAutorRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // ✅ BUSCAR LIBROS POR NOMBRE DE AUTOR
    public List<LibroDto> buscarLibrosPorAutor(String nombreAutor) {
        // Buscar autores que coincidan con el nombre
        List<Autor> autores = autorRepository.findByNombreContainingIgnoreCase(nombreAutor);

        if (autores.isEmpty()) {
            return List.of();
        }

        // Para cada autor, obtener sus libros a través de las relaciones
        List<Long> libroIds = autores.stream()
                .flatMap(autor -> libroAutorRepository.findByAutorId(autor.getId()).stream())
                .map(relacion -> relacion.getLibro().getId())
                .distinct()
                .collect(Collectors.toList());

        // Usar LibroService para convertir a DTO
        return libroIds.stream()
                .map(libroId -> libroService.obtenerPorId(libroId))
                .collect(Collectors.toList());
    }

    // ✅ BUSCAR AUTORES POR NOMBRE DE LIBRO
    public List<AutorDto> buscarAutoresPorLibro(String nombreLibro) {
        // Buscar libros que coincidan con el nombre
        List<Libro> libros = libroRepository.findByTituloContainingIgnoreCase(nombreLibro);

        if (libros.isEmpty()) {
            return List.of();
        }

        // Para cada libro, obtener sus autores a través de las relaciones
        List<Long> autorIds = libros.stream()
                .flatMap(libro -> libroAutorRepository.findByLibroId(libro.getId()).stream())
                .map(relacion -> relacion.getAutor().getId())
                .distinct()
                .collect(Collectors.toList());

        // Usar AutorService para convertir a DTO
        return autorIds.stream()
                .map(autorId -> autorService.obtenerPorId(autorId))
                .collect(Collectors.toList());
    }
}
