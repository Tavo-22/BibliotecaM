package com.biblioteca.user_service.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "direcciones")
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonBackReference
    private Usuario usuario;

    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false)
    private String ciudad;

    @Column(name = "codigo_postal")
    private String codigoPostal;

    public Direccion() {
    }

    public Direccion(Usuario usuario, String direccion, String ciudad, String codigoPostal){
        this.usuario = usuario;
        this.direccion=direccion;
        this.ciudad=ciudad;
        this.codigoPostal=codigoPostal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUser() {
        return usuario;
    }

    public void setUser(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }
}
