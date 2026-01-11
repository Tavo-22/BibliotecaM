package com.biblioteca.book_service.controller;

import com.biblioteca.book_service.dto.AutorDto;
import com.biblioteca.book_service.dto.CrearLibroAutorDto;
import com.biblioteca.book_service.dto.LibroAutorDto;
import com.biblioteca.book_service.dto.LibroDto;
import com.biblioteca.book_service.service.LibroAutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/libro-autor")
public class LibroAutorController {

    @Autowired
    private LibroAutorService libroAutorService;

    // obtener todas las relaciones
    @GetMapping
    public ResponseEntity<List<LibroAutorDto>> obtenerTodasLasRelaciones() {
        List<LibroAutorDto> relaciones = libroAutorService.obtenerTodasLasRelaciones();
        return ResponseEntity.ok(relaciones);
    }

    // agregar autor al libro
    @PostMapping
    public ResponseEntity<LibroAutorDto> agregarAutorALibro(@RequestBody CrearLibroAutorDto crearLibroAutorDto) {
        LibroAutorDto relacionCreada = libroAutorService.agregarAutorALibro(crearLibroAutorDto);
        return new ResponseEntity<>(relacionCreada, HttpStatus.CREATED);
    }

    // obtener autores de un libro
    @GetMapping("/libro/{libroId}/autores")
    public ResponseEntity<List<LibroAutorDto>> obtenerAutoresDeLibro(@PathVariable(name = "libroId") Long libroId) {
        List<LibroAutorDto> autores = libroAutorService.obtenerAutoresDeLibro(libroId);
        return ResponseEntity.ok(autores);
    }

    // ✅obtener libros de un autor
    @GetMapping("/autor/{autorId}/libros")
    public ResponseEntity<List<LibroAutorDto>> obtenerLibrosDeAutor(@PathVariable(name = "autorId") Long autorId) {
        List<LibroAutorDto> libros = libroAutorService.obtenerLibrosDeAutor(autorId);
        return ResponseEntity.ok(libros);
    }

    // eliminar autor de un libro
    @DeleteMapping("/libro/{libroId}/autor/{autorId}")
    public ResponseEntity<Void> eliminarAutorDeLibro(
            @PathVariable(name = "libroId") Long libroId,
            @PathVariable(name = "autorId") Long autorId) {
        libroAutorService.eliminarAutorDeLibro(libroId, autorId);
        return ResponseEntity.noContent().build();
    }

    // ✅ BUSCAR LIBROS POR NOMBRE DE AUTOR
    @GetMapping("/buscar/libros/autor/{nombreAutor}")
    public ResponseEntity<List<LibroDto>> buscarLibrosPorAutor(@PathVariable("nombreAutor") String nombreAutor) {
        List<LibroDto> libros = libroAutorService.buscarLibrosPorAutor(nombreAutor);
        return ResponseEntity.ok(libros);
    }

    // ✅ BUSCAR AUTORES POR NOMBRE DE LIBRO
    @GetMapping("/buscar/autores/libro/{nombreLibro}")
    public ResponseEntity<List<AutorDto>> buscarAutoresPorLibro(@PathVariable("nombreLibro") String nombreLibro) {
        List<AutorDto> autores = libroAutorService.buscarAutoresPorLibro(nombreLibro);
        return ResponseEntity.ok(autores);
    }
}
