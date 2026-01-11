package com.biblioteca.book_service.controller;

import com.biblioteca.book_service.dto.ActualizarCategoriaDto;
import com.biblioteca.book_service.dto.CategoriaDto;
import com.biblioteca.book_service.dto.CrearCategoriaDto;
import com.biblioteca.book_service.service.CategoriaService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    //obtener todas las categorias
    @GetMapping
    public ResponseEntity<List<CategoriaDto>> obtenerTodas(){
        List<CategoriaDto> categoriaDto = categoriaService.obtenerTodas();
        return ResponseEntity.ok(categoriaDto);
    }

    //obtener categorias ordenadas por nombre
    @GetMapping("/ordenadas")
    public ResponseEntity<List<CategoriaDto>> obtenerTodasOrdenadas(){
        List<CategoriaDto> categoriaDto = categoriaService.obtenerTodasOrdenadas();
        return ResponseEntity.ok(categoriaDto);
    }

    //obtener categoria por id
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDto> obtenerPorId(@PathVariable(name = "id") Long id){
        CategoriaDto categoriaDto = categoriaService.obtenerPorId(id);
        return ResponseEntity.ok(categoriaDto);
    }

    //obtener categoria por nombre exacto
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<CategoriaDto> obtenerPorNombre(@PathVariable(name = "nombre") String nombre){
        CategoriaDto categoriaDto = categoriaService.obtenerPorNombre(nombre);
        return ResponseEntity.ok(categoriaDto);
    }

    //buscar categoria por nombre (contenido)
    @GetMapping("/buscar/nombre/{nombre}")
    public ResponseEntity<List<CategoriaDto>> buscarPorNombre(@PathVariable(name = "nombre") String nombre){
        List<CategoriaDto> categoriaDto = categoriaService.buscarPorNombre(nombre);
        return ResponseEntity.ok(categoriaDto);
    }

    //buscar categoria por descripcion
    @GetMapping("/buscar/descripcion/{texto}")
    public ResponseEntity<List<CategoriaDto>> buscarPorDescripcion(@PathVariable(name = "texto") String texto){
        List<CategoriaDto> categoriaDto = categoriaService.buscarPorDescripcion(texto);
        return ResponseEntity.ok(categoriaDto);
    }

    //verificar si existe categoria
    @GetMapping("/existe/{nombre}")
    public ResponseEntity<Boolean> existePorNombre(@PathVariable(name = "nombre") String nombre){
        boolean existe = categoriaService.existePorNombre(nombre);
        return ResponseEntity.ok(existe);
    }

    //crear nueva categoria
    @PostMapping
    public ResponseEntity<CategoriaDto> crearCategoria(@RequestBody CrearCategoriaDto crearCategoriaDto){
        CategoriaDto categoriaCreada = categoriaService.crear(crearCategoriaDto);
        return new ResponseEntity<>(categoriaCreada, HttpStatus.CREATED);
    }

    // actualizar
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDto> actualizarCategoria(
            @PathVariable(name = "id") Long id,
            @RequestBody ActualizarCategoriaDto actualizarCategoriaDto) {
        CategoriaDto categoriaActualizada = categoriaService.actualizar(id, actualizarCategoriaDto);
        return ResponseEntity.ok(categoriaActualizada);
    }

    // eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable(name = "id") Long id) {
        categoriaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
