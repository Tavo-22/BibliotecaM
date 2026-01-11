package com.biblioteca.user_service.controller;

import com.biblioteca.user_service.dto.CrearHistorialUsuarioDto;
import com.biblioteca.user_service.dto.HistorialUsuarioDto;
import com.biblioteca.user_service.service.HistorialUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historial-usuario")
public class HistorialUsuarioController {

    @Autowired
    private HistorialUsuarioService historialUsuarioService;

    //Obtener todo el historial
    @GetMapping
    public ResponseEntity<List<HistorialUsuarioDto>> obtenerTodoElHistorial(){
        List<HistorialUsuarioDto> historialUsuarioDto = historialUsuarioService.obtenerTodoElHistorial();
        return ResponseEntity.ok(historialUsuarioDto);
    }

    //Obtener historial por Usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<HistorialUsuarioDto>> obtenerHistorialPorUsuario(@PathVariable("usuarioId") Long usuarioId){
        List<HistorialUsuarioDto> historial = historialUsuarioService.obtenerHistorialPorUsuario(usuarioId);
        return ResponseEntity.ok(historial);
    }

    //Obtener historial por accion
    @GetMapping("/accion/{accion}")
    public ResponseEntity<List<HistorialUsuarioDto>> obtenerHistorialPorAccion(@PathVariable("accion") String accion){
        List<HistorialUsuarioDto> historial = historialUsuarioService.obtenerHistorialPorAccion(accion);
        return ResponseEntity.ok(historial);
    }

    //Crear registro de historial (manual)
    @PostMapping
    public ResponseEntity<HistorialUsuarioDto> crearRegistroHistorial(@RequestBody CrearHistorialUsuarioDto crearHistorialUsuarioDto){
        HistorialUsuarioDto historialUsuarioDto = historialUsuarioService.crearRegistroHistorial(crearHistorialUsuarioDto);
        return new ResponseEntity<>(historialUsuarioDto, HttpStatus.CREATED);
    }

    // Registrar creación de usuario automáticamente
    @PostMapping("/registrar-creacion/{usuarioId}")
    public ResponseEntity<Void> registrarCreacionUsuario(@PathVariable("usuarioId") Long usuarioId) {
        historialUsuarioService.registrarCreacionUsuario(usuarioId);
        return ResponseEntity.ok().build();
    }

    // Registrar actualización de perfil
    @PostMapping("/registrar-actualizacion/{usuarioId}")
    public ResponseEntity<Void> registrarActualizacionPerfil(
            @PathVariable("usuarioId") Long usuarioId,
            @RequestParam String cambios) {
        historialUsuarioService.registrarActualizacionPerfil(usuarioId, cambios);
        return ResponseEntity.ok().build();
    }

    // Registrar cambio de membresía
    @PostMapping("/registrar-cambio-membresia/{usuarioId}")
    public ResponseEntity<Void> registrarCambioMembresia(
            @PathVariable("usuarioId") Long usuarioId,
            @RequestParam String nuevaMembresia) {
        historialUsuarioService.registrarCambioMembresia(usuarioId, nuevaMembresia);
        return ResponseEntity.ok().build();
    }


}
