package com.biblioteca.book_service.service;

import com.biblioteca.book_service.dto.ActualizarCategoriaDto;
import com.biblioteca.book_service.dto.CategoriaDto;
import com.biblioteca.book_service.dto.CrearCategoriaDto;
import com.biblioteca.book_service.model.Categoria;
import com.biblioteca.book_service.repository.CategoriaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    //conversion Entity-> dto
    private CategoriaDto convertirDto(Categoria categoria){
        return new CategoriaDto(
                categoria.getId(),
                categoria.getNombre(),
                categoria.getDescripcion()
        );
    }

    //conversion dto->entity
    private Categoria convetirEntity(CrearCategoriaDto crearCategoriaDto){
        Categoria categoria = new Categoria();
        categoria.setNombre(crearCategoriaDto.nombre());
        categoria.setDescripcion(crearCategoriaDto.descripcion());
        return categoria;
    }

    //obtener todas las categorias
    public List<CategoriaDto> obtenerTodas(){
        return categoriaRepository.findAll()
                .stream()
                .map(this::convertirDto)
                .collect(Collectors.toList());
    }

    //obtener categorias ordenadas por nombres
    public List<CategoriaDto> obtenerTodasOrdenadas(){
        return categoriaRepository.findAllByOrderByNombreAsc()
                .stream()
                .map(this::convertirDto)
                .collect(Collectors.toList());
    }

    //obtener categoria por id
    public CategoriaDto obtenerPorId(Long id){
       Categoria categoria = categoriaRepository.findById(id)
               .orElseThrow(()-> new RuntimeException("Categoria no econtrada con ID "+id));
       return convertirDto(categoria);
    }

    //obtener categoria pr nombre exacto
    public CategoriaDto obtenerPorNombre(String nombre){
        Categoria categoria = categoriaRepository.findByNombre(nombre)
                .orElseThrow(()->new RuntimeException("Categoria no encontrada con el nombre "+nombre));
        return convertirDto(categoria);
    }

    //buscar cateogiras por nombre (contenido)
    public List<CategoriaDto> buscarPorNombre(String nombre){
        return categoriaRepository.findByNombreContainingIgnoreCase(nombre)
                .stream()
                .map(this::convertirDto)
                .collect(Collectors.toList());
    }

    //buscar categorias por descripcion
    public List<CategoriaDto> buscarPorDescripcion(String texto) {
        return categoriaRepository.findByDescripcionContaining(texto)
                .stream()
                .map(this::convertirDto)
                .collect(Collectors.toList());
    }

    //verificar si existe categoria
    public boolean existePorNombre(String nombre){
        return categoriaRepository.existsByNombre(nombre);
    }

    //crear nueva categoria
    @Transactional
    public CategoriaDto crear (CrearCategoriaDto crearCategoriaDto){
        //verificar si el nombre ya existe
        if(categoriaRepository.existsByNombre(crearCategoriaDto.nombre())){
            throw new RuntimeException("Ya existe una categoría con el nombre "+crearCategoriaDto.nombre());
        }
        Categoria categoria = convetirEntity(crearCategoriaDto);
        Categoria categoriaGuardada = categoriaRepository.save(categoria);
        return convertirDto(categoriaGuardada);
    }

    //actualizar categoria
    @Transactional
    public CategoriaDto actualizar(Long id, ActualizarCategoriaDto actualizarCategoriaDto){
            Categoria categoriaExistente = categoriaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));

            // Verificar si el nuevo nombre ya existe (si se está cambiando)
            if (actualizarCategoriaDto.nombre() != null &&
                    !actualizarCategoriaDto.nombre().equals(categoriaExistente.getNombre()) &&
                    categoriaRepository.existsByNombre(actualizarCategoriaDto.nombre())) {
                throw new RuntimeException("Ya existe una categoría con el nombre: " + actualizarCategoriaDto.nombre());
            }

            // Actualizar solo los campos proporcionados
            if (actualizarCategoriaDto.nombre() != null) {
                categoriaExistente.setNombre(actualizarCategoriaDto.nombre());
            }
            if (actualizarCategoriaDto.descripcion() != null) {
                categoriaExistente.setDescripcion(actualizarCategoriaDto.descripcion());
            }

            Categoria categoriaActualizada = categoriaRepository.save(categoriaExistente);
            return convertirDto(categoriaActualizada);
    }

    //eliminar categoria
    @Transactional
    public void eliminar(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new RuntimeException("Categoría no encontrada con ID: " + id);
        }
        categoriaRepository.deleteById(id);
    }
}
