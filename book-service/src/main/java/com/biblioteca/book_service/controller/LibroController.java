package com.biblioteca.book_service.controller;

import com.biblioteca.book_service.dto.ActualizarLibroDto;
import com.biblioteca.book_service.dto.CrearLibroDto;
import com.biblioteca.book_service.dto.LibroDto;
import com.biblioteca.book_service.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    @Autowired
    private LibroService libroService;

    //Obtener todos los libros
    @GetMapping
    public ResponseEntity<List<LibroDto>> obtenerTodos(){
        List<LibroDto> libros = libroService.obtenerTodos();
        return ResponseEntity.ok(libros);
    }

    //Obtener libro por id
    @GetMapping("/{id}")
    public ResponseEntity<LibroDto> obtenerPorId(@PathVariable("id") Long id){
        LibroDto libro  = libroService.obtenerPorId(id);
        return ResponseEntity.ok(libro);
    }

    //Obtener libro por isbn
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<LibroDto> obtenerPorIsbn(@PathVariable("isbn") String isbn){
        LibroDto libro = libroService.obtenerPorIsbn(isbn);
        return ResponseEntity.ok(libro);
    }

    //Buscar por titulo
    @GetMapping("/buscar/titulo/{titulo}")
    public ResponseEntity<List<LibroDto>> buscarPorTitulo(@PathVariable("titulo") String titulo){
        List<LibroDto> libros = libroService.buscarPorTitulo(titulo);
        return ResponseEntity.ok(libros);
    }

    //Buscar por editorial
    @GetMapping("/buscar/editorial/{editorial}")
    public ResponseEntity<List<LibroDto>> buscarPorEditorial(@PathVariable("editorial") String editorial) {
        List<LibroDto> libros = libroService.buscarPorEditorial(editorial);
        return ResponseEntity.ok(libros);
    }

    //Buscar por año de publicacion
    @GetMapping("/buscar/ano/{anoPublicacion}")
    public ResponseEntity<List<LibroDto>> buscarPorAnoPublicacion(@PathVariable("anoPublicacion") Integer anoPublicacion) {
        List<LibroDto> libros = libroService.buscarPorAnoPublicacion(anoPublicacion);
        return ResponseEntity.ok(libros);
    }

    //Buscar por rango de años
    @GetMapping("/buscar/rango-anos")
    public ResponseEntity<List<LibroDto>> buscarPorRangoAnos(
            @RequestParam(name = "inicio") Integer inicio,
            @RequestParam(name = "fin") Integer fin) {
        List<LibroDto> libros = libroService.buscarPorRangosAnos(inicio, fin);
        return ResponseEntity.ok(libros);
    }

    //Buscar por texto en titulo o descripcion
    @GetMapping("/buscar/texto/{texto}")
    public ResponseEntity<List<LibroDto>> buscarPorTexto(@PathVariable("texto") String texto) {
        List<LibroDto> libros = libroService.buscarPorTexto(texto);
        return ResponseEntity.ok(libros);
    }

    //contar libros por editorial
    @GetMapping("/contar/editorial/{editorial}")
    public ResponseEntity<Long> contarPorEditorial(@PathVariable("editorial") String editorial) {
        long cantidad = libroService.contarPorEditorial(editorial);
        return ResponseEntity.ok(cantidad);
    }

    //Crear nuevo libro
    @PostMapping
    public ResponseEntity<LibroDto> crearLibro(@RequestBody CrearLibroDto crearLibroDto){
        LibroDto libroCreado = libroService.crear(crearLibroDto);
        return new ResponseEntity<>(libroCreado, HttpStatus.CREATED);
    }

    //Actulizar libro
    @PutMapping("/{id}")
    public ResponseEntity<LibroDto> actualizarLibro(
            @PathVariable("id") Long id,
            @RequestBody ActualizarLibroDto actualizarLibroDto) {
        LibroDto libroActualizado = libroService.actualizar(id, actualizarLibroDto);
        return ResponseEntity.ok(libroActualizado);
    }

    //Eliminar libro
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLibro(
            @PathVariable("id") Long id){
        libroService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}
