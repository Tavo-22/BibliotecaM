package com.biblioteca.book_service.model;

import jakarta.persistence.*;

@Entity
@Table(name = "libro_autor")
public class LibroAutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "libro_id", nullable = false)
    private Libro libro;

    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    private Autor autor;

    public LibroAutor() {
    }

    public LibroAutor(Libro libro, Autor autor) {
        this.libro = libro;
        this.autor = autor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }
}
