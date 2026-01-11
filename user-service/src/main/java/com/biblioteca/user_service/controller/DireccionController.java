package com.biblioteca.user_service.controller;

import com.biblioteca.user_service.dto.CrearDireccionDto;
import com.biblioteca.user_service.dto.DireccionDto;
import com.biblioteca.user_service.service.DireccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/direcciones")
public class DireccionController {

    @Autowired
    private DireccionService direccionService;

    //obtener todas las direcciones
    @GetMapping
    public ResponseEntity<List<DireccionDto>> obtenerTodas(){
        List<DireccionDto> direcciones = direccionService.obtenerTodas();
        return ResponseEntity.ok(direcciones);
    }

    //Obtener direccion por id
    @GetMapping("/{id}")
    public ResponseEntity<DireccionDto> obtenerPorId(@PathVariable("id") Long id){
        DireccionDto direccionDto = direccionService.obtenerPorId(id);
        return ResponseEntity.ok(direccionDto);
    }

    //Obtener Direcciones por usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<DireccionDto>> obtenerPorUsuario(@PathVariable("usuarioId") Long usuarioId){
        List<DireccionDto> direcciones = direccionService.obtenerPorUsuario(usuarioId);
        return ResponseEntity.ok(direcciones);
    }

    //Obtener direcciones por ciudad
    @GetMapping("/ciudad/{ciudad}")
    public ResponseEntity<List<DireccionDto>> obtenerPorCiudad(@PathVariable("ciudad") String ciudad){
        List<DireccionDto> direcciones = direccionService.obtenerPorCiudad(ciudad);
        return ResponseEntity.ok(direcciones);
    }

    //Obtener direcciones por codigo postal
    @GetMapping("/codigo-postal/{codigoPostal}")
    public ResponseEntity<List<DireccionDto>> obtenerPorCodigoPostal(@PathVariable("codigoPostal") String codigoPostal){
        List<DireccionDto> direcciones = direccionService.obtenerPorCodigoPostal(codigoPostal);
        return ResponseEntity.ok(direcciones);
    }

    //Crear Direccion
    @PostMapping
    public ResponseEntity<DireccionDto> crearDireccion(@RequestBody CrearDireccionDto crearDireccionDto) {
        DireccionDto direccionCreada = direccionService.crear(crearDireccionDto);
        return new ResponseEntity<>(direccionCreada, HttpStatus.CREATED);
    }

    //Actulizar Direccion
    @PutMapping("/{id}")
    public ResponseEntity<DireccionDto> actualizarDireccion(@PathVariable("id") Long id, @RequestBody CrearDireccionDto crearDireccionDto){
        DireccionDto direccionDto = direccionService.actualizar(id, crearDireccionDto);
        return ResponseEntity.ok(direccionDto);
    }

    //Eliminacion direccion
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDireccion(@PathVariable("id") Long id){
        direccionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }



}
