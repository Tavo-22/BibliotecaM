package com.biblioteca.book_service.controller;

import com.biblioteca.book_service.dto.ActualizarAutorDto;
import com.biblioteca.book_service.dto.AutorDto;
import com.biblioteca.book_service.dto.CrearAutorDto;
import com.biblioteca.book_service.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/autores")
public class AutorController {

    @Autowired
    private AutorService autorService;

    //obtener todos los autores
    @GetMapping
    public ResponseEntity<List<AutorDto>> obtenerTodos(){
        List<AutorDto> autores = autorService.obtenerTodos();
        return ResponseEntity.ok(autores);
    }

    //obtener autores ordenados por nombre
    @GetMapping("/ordenados")
    public ResponseEntity<List<AutorDto>> obtenerTodosOrdenadosPorNombre(){
        List<AutorDto> autores = autorService.obtenerTodosOrdenadosPorNombre();
        return ResponseEntity.ok(autores);
    }

    //Obtener autor por Id
    @GetMapping("/{id}")
    public ResponseEntity<AutorDto> obtenerPorId(@PathVariable("id") Long id){
        AutorDto autorDto = autorService.obtenerPorId(id);
        return ResponseEntity.ok(autorDto);
    }

    //Obtene autor por nombre exacto
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<AutorDto> obtenerPorNombre(@PathVariable("nombre") String nombre){
        AutorDto autorDto = autorService.obtenerPorNombre(nombre);
        return ResponseEntity.ok(autorDto);
    }

    //Buscar autres por nomre que contenga
    @GetMapping("/buscar/nombre/{nombre}")
    public ResponseEntity<List<AutorDto>> buscarPorNombre(@PathVariable("nombre") String nombre){
        List<AutorDto> autores = autorService.buscarPorNombre(nombre);
        return ResponseEntity.ok(autores);
    }

    //Buscar por nacionalidad
    @GetMapping("/buscar/nacionalidad/{nacionalidad}")
    public ResponseEntity<List<AutorDto>> buscarPorNacionalidad(@PathVariable("nacionalidad") String nacionalidad){
        List<AutorDto> autores = autorService.buscarPorNacionalidad(nacionalidad);
        return ResponseEntity.ok(autores);
    }

    @GetMapping("/buscar/biografia/{texto}")
    public ResponseEntity<List<AutorDto>> buscarPorBiografia(@PathVariable("texto") String texto){
        List<AutorDto> autorDto = autorService.buscarPorBibliografia(texto);
        return ResponseEntity.ok(autorDto);
    }

    @GetMapping("/contar/nacionalidad/{nacionalidad}")
    public ResponseEntity<Long> contarPorNacionalidad(@PathVariable("nacionalidad") String nacionalidad){
        Long cantidad = autorService.contarPorNacionlidad(nacionalidad);
        return ResponseEntity.ok(cantidad);
    }

    //crear nuevo autor
    @PostMapping
    public ResponseEntity<AutorDto> crearAutor(@RequestBody CrearAutorDto crearAutorDto){
        AutorDto autorCreado = autorService.crear(crearAutorDto);
        return new ResponseEntity<>(autorCreado, HttpStatus.CREATED);
    }

    //actualizar autor
    @PutMapping("/{id}")
    public ResponseEntity<AutorDto> actualizarAutor(@PathVariable("id") Long id, @RequestBody ActualizarAutorDto actualizarAutorDto){
        AutorDto autorActualizado = autorService.actualizar(id, actualizarAutorDto);
        return ResponseEntity.ok(autorActualizado);
    }

    //elimianr autor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAutor(@PathVariable("id") Long id){
        autorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
