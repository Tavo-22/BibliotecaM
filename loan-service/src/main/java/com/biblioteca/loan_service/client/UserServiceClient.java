package com.biblioteca.loan_service.client;

import com.biblioteca.loan_service.dto.UsuarioDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "usuario-service")//identificador de cada servicio, en el properties spring.application.name=usuario-service  # ← ESTE NOMBRE
public interface UserServiceClient {
    @GetMapping("/api/usuarios/{id}")
    UsuarioDto obtenerUsuario(@PathVariable(name = "id") Long id);
}
