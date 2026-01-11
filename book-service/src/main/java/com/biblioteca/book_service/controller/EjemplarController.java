package com.biblioteca.book_service.controller;

import com.biblioteca.book_service.dto.ActualizarEjemplarDto;
import com.biblioteca.book_service.dto.CrearEjemplarDto;
import com.biblioteca.book_service.dto.EjemplarDto;
import com.biblioteca.book_service.model.EstadoEjemplar;
import com.biblioteca.book_service.service.EjemplarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ejemplares")
public class EjemplarController {

    @Autowired
    private EjemplarService ejemplarService;

    //obtener todos los ejemplares
    @GetMapping
    public ResponseEntity<List<EjemplarDto>> obtenerTodos(){
        List<EjemplarDto> ejemplarDto = ejemplarService.obtenerTodos();
        return ResponseEntity.ok(ejemplarDto);
    }

    //obtener ejemplar por id
    @GetMapping("/{id}")
    public ResponseEntity<EjemplarDto> obtenerPorId(@PathVariable("id") Long id){
        EjemplarDto ejemplarDto = ejemplarService.obtenerPorId(id);
        return ResponseEntity.ok(ejemplarDto);
    }

    //obtener ejemplar por codigo de barras
    @GetMapping("/codigo-barras/{codigoBarras}")
    public ResponseEntity<EjemplarDto> obtenerPorCodigoBarras(@PathVariable("codigoBarras") String codigoBarras){
        EjemplarDto ejemplarDto = ejemplarService.obtenerPorCodigoBarras(codigoBarras);
        return ResponseEntity.ok(ejemplarDto);
    }

    //obtener ejemplares por libro
    @GetMapping("/libro/{libroId}")
    public ResponseEntity<List<EjemplarDto>> obtenerPorLibro(@PathVariable("libroId") Long libroId){
        List<EjemplarDto> ejemplarDto = ejemplarService.obtenerPorLibro(libroId);
        return ResponseEntity.ok(ejemplarDto);
    }

    // ✅ OBTENER EJEMPLARES DISPONIBLES DE UN LIBRO
    @GetMapping("/libro/{libroId}/disponibles")
    public ResponseEntity<List<EjemplarDto>> obtenerDisponiblesPorLibro(@PathVariable("libroId") Long libroId) {
        List<EjemplarDto> ejemplares = ejemplarService.obtenerDisponiblePorLibro(libroId);
        return ResponseEntity.ok(ejemplares);
    }

    // ✅ OBTENER EJEMPLARES POR UBICACIÓN
    @GetMapping("/ubicacion/{ubicacion}")
    public ResponseEntity<List<EjemplarDto>> obtenerPorUbicacion(@PathVariable("ubicacion") String ubicacion) {
        List<EjemplarDto> ejemplares = ejemplarService.obtenerPorUbicacion(ubicacion);
        return ResponseEntity.ok(ejemplares);
    }

    // ✅ CONTAR EJEMPLARES POR ESTADO Y LIBRO
    @GetMapping("/contar/libro/{libroId}/estado/{estado}")
    public ResponseEntity<Long> contarPorLibroYEstado(
            @PathVariable("libroId") Long libroId,
            @PathVariable("estado") EstadoEjemplar estado) {
        long cantidad = ejemplarService.contarPorLibroYEstado(libroId, estado);
        return ResponseEntity.ok(cantidad);
    }

    // ✅ CREAR NUEVO EJEMPLAR
    @PostMapping
    public ResponseEntity<EjemplarDto> crearEjemplar(@RequestBody CrearEjemplarDto crearEjemplarDto) {
        EjemplarDto ejemplarCreado = ejemplarService.crear(crearEjemplarDto);
        return new ResponseEntity<>(ejemplarCreado, HttpStatus.CREATED);
    }

    // ✅ ACTUALIZAR EJEMPLAR
    @PutMapping("/{id}")
    public ResponseEntity<EjemplarDto> actualizarEjemplar(
            @PathVariable("id") Long id,
            @RequestBody ActualizarEjemplarDto actualizarEjemplarDto) {
        EjemplarDto ejemplarActualizado = ejemplarService.actualizar(id, actualizarEjemplarDto);
        return ResponseEntity.ok(ejemplarActualizado);
    }

    // ✅ ELIMINAR EJEMPLAR
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEjemplar(@PathVariable("id") Long id) {
        ejemplarService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
