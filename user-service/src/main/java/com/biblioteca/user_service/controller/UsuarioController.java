package com.biblioteca.user_service.controller;

import com.biblioteca.user_service.dto.ActualizarUsuarioDto;
import com.biblioteca.user_service.dto.CrearUsuarioDto;
import com.biblioteca.user_service.dto.UsuarioDto;
import com.biblioteca.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UserService userService;

    // ✅ OBTENER TODOS LOS USUARIOS
    @GetMapping
    public ResponseEntity<List<UsuarioDto>> obtenerTodos() {
        List<UsuarioDto> usuarios = userService.obtenerTodos();
        return ResponseEntity.ok(usuarios);
    }

    // ✅ OBTENER USUARIO POR ID
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDto> obtenerPorId(@PathVariable("id") Long id) {
        UsuarioDto usuario = userService.obtenerPorId(id);
        return ResponseEntity.ok(usuario);
    }

    // ✅ OBTENER USUARIO POR EMAIL
    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioDto> obtenerPorEmail(@PathVariable("email") String email) {
        UsuarioDto usuario = userService.obtenerPorEmail(email);
        return ResponseEntity.ok(usuario);
    }

    // ✅ BUSCAR POR DOMINIO DE EMAIL
    @GetMapping("/dominio/{dominio}")
    public ResponseEntity<List<UsuarioDto>> buscarPorDominioEmail(@PathVariable("dominio") String dominio) {
        List<UsuarioDto> usuarios = userService.buscarPorDominioEmail(dominio);
        return ResponseEntity.ok(usuarios);
    }

    // ✅ CONTAR USUARIOS ACTIVOS
    @GetMapping("/contar-activos")
    public ResponseEntity<Long> contarUsuariosActivos() {
        long cantidad = userService.contarUsuariosActivos();
        return ResponseEntity.ok(cantidad);
    }

    // ✅ CREAR NUEVO USUARIO
    @PostMapping
    public ResponseEntity<UsuarioDto> crearUsuario(@RequestBody CrearUsuarioDto crearUsuarioDto) {
        UsuarioDto usuarioCreado = userService.crear(crearUsuarioDto);
        return new ResponseEntity<>(usuarioCreado, HttpStatus.CREATED);
    }

    // ✅ ACTUALIZAR USUARIO
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDto> actualizarUsuario(
            @PathVariable("id") Long id,
            @RequestBody ActualizarUsuarioDto actualizarUsuarioDto) {
        UsuarioDto usuarioActualizado = userService.actualizar(id, actualizarUsuarioDto);
        return ResponseEntity.ok(usuarioActualizado);
    }

    // ✅ DESACTIVAR USUARIO
    @PutMapping("/{id}/desactivar")
    public ResponseEntity<Void> desactivarUsuario(@PathVariable("id") Long id) {
        userService.desactivar(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ ACTIVAR USUARIO
    @PutMapping("/{id}/activar")
    public ResponseEntity<Void> activarUsuario(@PathVariable("id") Long id) {
        userService.activar(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ ELIMINAR PERMANENTEMENTE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable("id") Long id) {
        userService.eliminarPermanentemente(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/test")
    public String test() {
        return "User-service funcionando!";
    }
}
