package com.biblioteca.book_service.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ejemplares")
public class Ejemplar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "libro_id", nullable = false)
    private Libro libro;

    @Column(name = "codigo_barras", unique = true)
    private String codigoBarras;

    @Enumerated(EnumType.STRING)
    private EstadoEjemplar estado;

    private String ubicacion;

    public Ejemplar() {
        this.estado = EstadoEjemplar.DISPONIBLE;
    }

    public Ejemplar(Libro libro, String codigoBarras, String ubicacion){
        this();
        this.libro=libro;
        this.codigoBarras=codigoBarras;
        this.ubicacion=ubicacion;
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

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public EstadoEjemplar getEstado() {
        return estado;
    }

    public void setEstado(EstadoEjemplar estado) {
        this.estado = estado;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
}
