package com.biblioteca.user_service.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "historial_usuarios")
public class HistorialUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private String accion;

    @Column(name = "fecha_accion", nullable = false)
    private LocalDateTime fechaAccion;

    private String descripcion;

    public HistorialUsuario() {
        this.fechaAccion = LocalDateTime.now();
    }

    public HistorialUsuario(Usuario usuario, String accion, String descripcion){
        this();
        this.usuario = usuario;
        this.accion=accion;
        this.descripcion=descripcion;
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

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public LocalDateTime getFechaAccion() {
        return fechaAccion;
    }

    public void setFechaAccion(LocalDateTime fechaAccion) {
        this.fechaAccion = fechaAccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
