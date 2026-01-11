package com.biblioteca.loan_service.client;

import com.biblioteca.loan_service.dto.EjemplarDto;
import com.biblioteca.loan_service.dto.LibroDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "book-service")
public interface BookServiceClient {

    @GetMapping("/api/libros/{id}")
    LibroDto obtenerLibro(@PathVariable(name = "id") Long id);

    @GetMapping("/api/ejemplares/{id}")
    EjemplarDto obtenerEjemplar(@PathVariable(name = "id") Long id);

}
