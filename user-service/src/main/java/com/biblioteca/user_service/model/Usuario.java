package com.biblioteca.user_service.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)  // ← CAMBIO IMPORTANTE
    @Column(name = "tipo_membresia")
    private TipoMembresia tipoMembresia;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    private Boolean activo;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Direccion> direcciones = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HistorialUsuario> historial = new ArrayList<>();

    public Usuario() {
        this.fechaRegistro = LocalDateTime.now();
        this.activo=true;
    }

    public Usuario(String nombre, String email, TipoMembresia tipoMembresia) {
        this();
        this.nombre=nombre;
        this.email = email;
        this.tipoMembresia = tipoMembresia;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TipoMembresia getTipoMembresia() {
        return tipoMembresia;
    }

    public void setTipoMembresia(TipoMembresia tipoMembresia) {
        this.tipoMembresia = tipoMembresia;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public List<Direccion> getDirecciones() { return direcciones; }
    public void setDirecciones(List<Direccion> direcciones) { this.direcciones = direcciones; }

    public List<HistorialUsuario> getHistorial() { return historial; }
    public void setHistorial(List<HistorialUsuario> historial) { this.historial = historial; }
}
