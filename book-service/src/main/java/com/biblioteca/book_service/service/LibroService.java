package com.biblioteca.book_service.service;

import com.biblioteca.book_service.dto.ActualizarLibroDto;
import com.biblioteca.book_service.dto.CrearLibroDto;
import com.biblioteca.book_service.dto.LibroDto;
import com.biblioteca.book_service.model.Libro;
import com.biblioteca.book_service.repository.LibroRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    //Converion Entity -> Dto
    private LibroDto convertirDto(Libro libro){
        return new LibroDto(
                libro.getId(),
                libro.getTitulo(),
                libro.getIsbn(),
                libro.getEditorial(),
                libro.getAnoPublicacion(),
                libro.getDescripcion(),
                libro.getFechaCreacion()
        );
    }

    //Conversion Dto -> Entity
    private Libro convertirEntity(CrearLibroDto libroDto){
        Libro libro = new Libro();
        libro.setTitulo(libroDto.titulo());
        libro.setIsbn(libroDto.isbn());
        libro.setEditorial(libroDto.editorial());
        libro.setAnoPublicacion(libroDto.anoPublicacion());
        libro.setDescripcion(libroDto.descripcion());
        libro.setFechaCreacion(LocalDateTime.now());
        return libro;
    }

    //Obtener todos los libros
    public List<LibroDto> obtenerTodos(){
        return libroRepository.findAll()
                .stream()
                .map(this::convertirDto)
                .collect(Collectors.toList());
    }

    //obter libro por id
    public LibroDto obtenerPorId(Long id){
        Libro libro = libroRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Libro no encontrado con el ID "+id));
        return convertirDto(libro);
    }

    //Obtener libro por isbn
    public LibroDto obtenerPorIsbn(String isbn){
        Libro libro = libroRepository.findByIsbn(isbn)
                .orElseThrow(()-> new RuntimeException("Libro no encontrado con ISBN "+isbn));
        return convertirDto(libro);
    }

    //Obtener libro por titulo
    public List<LibroDto> buscarPorTitulo(String titulo){
        return libroRepository.findByTituloContainingIgnoreCase(titulo)
                .stream()
                .map(this::convertirDto)
                .collect(Collectors.toList());
    }

    //Buscar libro por editorial
    public List<LibroDto> buscarPorEditorial(String editorial){
        return libroRepository.findByEditorial(editorial)
                .stream()
                .map(this::convertirDto)
                .collect(Collectors.toList());
    }

    //Buscar libros por año de publicacion
    public List<LibroDto> buscarPorAnoPublicacion(Integer anoPublicacion){
        return libroRepository.findByAnoPublicacion(anoPublicacion)
                .stream()
                .map(this::convertirDto)
                .collect(Collectors.toList());
    }

    //buscar libros por rango de años
    public List<LibroDto> buscarPorRangosAnos(Integer inicio, Integer fin){
        return libroRepository.findByAnoPublicacionBetween(inicio,fin)
                .stream()
                .map(this::convertirDto)
                .collect(Collectors.toList());
    }

    //buscar libros porr texto en titulo o descripcion
    public List<LibroDto> buscarPorTexto(String texto){
        return libroRepository.buscarPorTituloODescripcion(texto)
                .stream()
                .map(this::convertirDto)
                .collect(Collectors.toList());
    }

    //contar libros por editorial
    public long contarPorEditorial(String editorial){
        return libroRepository.countByEditorial(editorial);
    }

    //crear nuevo libro
    @Transactional
    public LibroDto crear(CrearLibroDto libroDto){
        //verificar si el isbn ya existe
        if(libroRepository.findByIsbn(libroDto.isbn()).isPresent()){
            throw new RuntimeException("El isbn ya esta registrado: "+libroDto.isbn());
        }

        Libro libro = convertirEntity(libroDto);
        Libro libroGuardado = libroRepository.save(libro);
        return convertirDto(libroGuardado);
    }

    //actualizar libro
    @Transactional
    public LibroDto actualizar(Long id, ActualizarLibroDto actualizarLibroDto){
        Libro libroExistente = libroRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Libro no encontrado con el ID "+id));

        //Actulizar solo los datos proporcionados
        if(actualizarLibroDto.titulo()!=null){
            libroExistente.setTitulo(actualizarLibroDto.titulo());
        }
        if (actualizarLibroDto.editorial() != null) {
            libroExistente.setEditorial(actualizarLibroDto.editorial());
        }
        if (actualizarLibroDto.anoPublicacion() != null) {
            libroExistente.setAnoPublicacion(actualizarLibroDto.anoPublicacion());
        }
        if (actualizarLibroDto.descripcion() != null) {
            libroExistente.setDescripcion(actualizarLibroDto.descripcion());
        }

        Libro libroActualizado = libroRepository.save(libroExistente);
        return convertirDto(libroActualizado);
    }

    //elimianr libro
    @Transactional
    public void eliminar(Long id){
        if(!libroRepository.existsById(id)){
            throw new RuntimeException("Libro no encontrado con ID: "+id);
        }
        libroRepository.deleteById(id);
    }


}
