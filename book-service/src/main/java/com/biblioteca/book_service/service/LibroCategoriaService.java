package com.biblioteca.book_service.service;

import com.biblioteca.book_service.dto.*;
import com.biblioteca.book_service.model.Categoria;
import com.biblioteca.book_service.model.Libro;
import com.biblioteca.book_service.model.LibroCategoria;
import com.biblioteca.book_service.repository.CategoriaRepository;
import com.biblioteca.book_service.repository.LibroCategoriaRepository;
import com.biblioteca.book_service.repository.LibroRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class LibroCategoriaService {

    @Autowired
    private LibroCategoriaRepository libroCategoriaRepository;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private LibroService libroService;

    @Autowired
    private CategoriaService categoriaService;

    //Conversion Entity->Dto
    private LibroCategoriaDto convetirDto(LibroCategoria libroCategoria){
        return new LibroCategoriaDto(
                libroCategoria.getId(),
                libroCategoria.getLibro().getId(),
                libroCategoria.getCategoria().getId(),
                libroCategoria.getLibro().getTitulo(),
                libroCategoria.getCategoria().getNombre()
        );
    }

    //Converseion dto->Entity
    private LibroCategoria convertirEntity(CrearLibroCatgoriaDto crearLibroCatgoriaDto){
        Libro libro = libroRepository.findById(crearLibroCatgoriaDto.libroId())
                .orElseThrow(()->new RuntimeException("Libro no encontrado con ID: "+crearLibroCatgoriaDto.libroId()));

        Categoria categoria = categoriaRepository.findById(crearLibroCatgoriaDto.categoriaId())
                .orElseThrow(()->new RuntimeException("Categoria no encontrada con ID: "+crearLibroCatgoriaDto.categoriaId()));

        LibroCategoria libroCategoria = new LibroCategoria();
        libroCategoria.setLibro(libro);
        libroCategoria.setCategoria(categoria);

        return libroCategoria;
    }

    //agregar categoria a libro
    public LibroCategoriaDto agregarCategoriaAlLibro(CrearLibroCatgoriaDto crearLibroCatgoriaDto){
        //verificar si la relacion  ya existe
        if(libroCategoriaRepository.existsByLibroIdAndCategoriaId(crearLibroCatgoriaDto.libroId(), crearLibroCatgoriaDto.categoriaId())){
            throw new RuntimeException("La categoria ya esta asociada a este libro");
        }

        LibroCategoria libroCategoria = convertirEntity(crearLibroCatgoriaDto);
        LibroCategoria libroCategoriaGuardado = libroCategoriaRepository.save(libroCategoria);
        return convetirDto(libroCategoriaGuardado);
    }

    //obtener libros de una categoria
    public List<LibroCategoriaDto> obtenerCategoriasDelLibro(Long libroId){
        return libroCategoriaRepository.findByLibroId(libroId)
                .stream()
                .map(this::convetirDto)
                .collect(Collectors.toList());
    }

    //obtener libros de una categoria
    public List<LibroCategoriaDto> obtenerLibrosDeCategoria(Long categoriaId){
        return libroCategoriaRepository.findByCategoriaId(categoriaId)
                .stream()
                .map(this::convetirDto)
                .collect(Collectors.toList());
    }

    //eliminar categoria de libro
    public void eliminarCategoriaDeLibro(Long libroId, Long categoriaId){
        if(!libroCategoriaRepository.existsByLibroIdAndCategoriaId(libroId,categoriaId)){
            throw new RuntimeException("La relación libro-categoria no existe");
        }
        libroCategoriaRepository.deleteByLibroIdAndCategoriaId(libroId,categoriaId);
    }

    //obtener todas las relaciones
    public List<LibroCategoriaDto> obtenerTodasLasRelaciones(){
        return libroCategoriaRepository.findAll()
                .stream()
                .map(this::convetirDto)
                .collect(Collectors.toList());
    }

    //buscar libros por nombre de categoira
    public List<LibroDto> buscarLibrosPorCategoria(String nombreCategoria){
        //buscar categorias que coincidan con el nombre
        List<Categoria> categorias = categoriaRepository.findByNombreContainingIgnoreCase(nombreCategoria);

        if(categorias.isEmpty()){
            return List.of();
        }

        //Para cada categoria obtener sus libros
        List<Long> libroIds = categorias.stream()
                .flatMap(categoria -> libroCategoriaRepository.findByCategoriaId(categoria.getId()).stream())
                .map(relacion -> relacion.getLibro().getId())
                .distinct()
                .collect(Collectors.toList());

        //usar libroservice para convetir a dto
        return libroIds.stream()
                .map(libroId->{
                    try{
                        return libroService.obtenerPorId(libroId);
                    }catch (RuntimeException e){
                        return null; // si el libro no existe omitirlo
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    //buscar categoiras por nombre de libro
    public List<CategoriaDto> buscarCategoriasPorLibro(String nombreLibro){
        //buscr libros que coincidan con el nombre
        List<Libro> libros = libroRepository.findByTituloContainingIgnoreCase(nombreLibro);

        if(libros.isEmpty()){
            return List.of();
        }

        // Para cada libro, obtener sus categorías
        List<Long> categoriaIds = libros.stream()
                .flatMap(libro -> libroCategoriaRepository.findByLibroId(libro.getId()).stream())
                .map(relacion -> relacion.getCategoria().getId())
                .distinct()
                .collect(Collectors.toList());

        // Usar CategoriaService para convertir a DTO
        return categoriaIds.stream()
                .map(categoriaId -> {
                    try {
                        return categoriaService.obtenerPorId(categoriaId);
                    } catch (RuntimeException e) {
                        return null; // Si la categoría no existe, omitirla
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
