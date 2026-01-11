package com.biblioteca.book_service.controller;

import com.biblioteca.book_service.dto.CategoriaDto;
import com.biblioteca.book_service.dto.CrearLibroCatgoriaDto;
import com.biblioteca.book_service.dto.LibroCategoriaDto;
import com.biblioteca.book_service.dto.LibroDto;
import com.biblioteca.book_service.service.LibroCategoriaService;
import jakarta.ws.rs.POST;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@RestController
@RequestMapping("/api/libro-categoria")
public class LibroCategoriaController {

    @Autowired
    private LibroCategoriaService libroCategoriaService;

    //obtener todas las relaciones
    @GetMapping
    public ResponseEntity<List<LibroCategoriaDto>> obtenerTodasLasRelaciones(){
        List<LibroCategoriaDto> relaciones = libroCategoriaService.obtenerTodasLasRelaciones();
        return ResponseEntity.ok(relaciones);
    }

    //Agregar categoria a libro
    @PostMapping
    public ResponseEntity<LibroCategoriaDto> agregarCategoriaAlLibro(@RequestBody CrearLibroCatgoriaDto crearLibroCatgoriaDto){
        LibroCategoriaDto relacionCreada = libroCategoriaService.agregarCategoriaAlLibro(crearLibroCatgoriaDto);
        return new ResponseEntity<>(relacionCreada, HttpStatus.CREATED);
    }

    //obtener categorias de un libro
    @GetMapping("/libro/{libroId}/categorias")
    public ResponseEntity<List<LibroCategoriaDto>> obtenerCategoriasDelLibro(@PathVariable(name = "libroId") Long libroId){
        List<LibroCategoriaDto> categorias = libroCategoriaService.obtenerCategoriasDelLibro(libroId);
        return ResponseEntity.ok(categorias);
    }

    //obtener libros de una categoira
    @GetMapping("/categoria/{categoriaId}/libros")
    public ResponseEntity<List<LibroCategoriaDto>> obtenerLibrosDeCategoria(@PathVariable(name = "categoriaId") Long categoriaId){
        List<LibroCategoriaDto> libros = libroCategoriaService.obtenerLibrosDeCategoria(categoriaId);
        return ResponseEntity.ok(libros);
    }

    //eliminar categoria de libro
    @DeleteMapping("/libro/{libroId}/categoria/{categoriaId}")
    public ResponseEntity<Void> eliminarCategoriaDeLibro(@PathVariable(name = "libroId") Long libroId,
                                                         @PathVariable(name = "categoriaId") Long categoriaId){
        libroCategoriaService.eliminarCategoriaDeLibro(libroId,categoriaId);
        return ResponseEntity.noContent().build();
    }

    // buscar libros por nombre de categoria
    @GetMapping("/buscar/libros/categoria/{nombreCategoria}")
    public ResponseEntity<List<LibroDto>> buscarLibrosPorCategoria(@PathVariable(name = "nombreCategoria") String nombreCategoria) {
        List<LibroDto> libros = libroCategoriaService.buscarLibrosPorCategoria(nombreCategoria);
        return ResponseEntity.ok(libros);
    }

    // buscar categoria por nombre de libros
    @GetMapping("/buscar/categorias/libro/{nombreLibro}")
    public ResponseEntity<List<CategoriaDto>> buscarCategoriasPorLibro(@PathVariable(name = "nombreLibro") String nombreLibro) {
        List<CategoriaDto> categorias = libroCategoriaService.buscarCategoriasPorLibro(nombreLibro);
        return ResponseEntity.ok(categorias);
    }

}
