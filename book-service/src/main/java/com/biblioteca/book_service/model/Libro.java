package com.biblioteca.book_service.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(unique = true)
    private String isbn;

    @Column(name = "año_publicacion")
    private Integer anoPublicacion;

    private String editorial;

    private String descripcion;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    public Libro() {
        this.fechaCreacion = LocalDateTime.now();
    }

    public Libro(String titulo, String isbn, Integer anoPublicacion, String editorial){
        this();
        this.titulo=titulo;
        this.isbn=isbn;
        this.anoPublicacion=anoPublicacion;
        this.editorial=editorial;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getAnoPublicacion() {
        return anoPublicacion;
    }

    public void setAnoPublicacion(Integer anoPublicacion) {
        this.anoPublicacion = anoPublicacion;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
