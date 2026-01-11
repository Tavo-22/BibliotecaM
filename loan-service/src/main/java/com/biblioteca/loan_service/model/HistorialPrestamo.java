package com.biblioteca.loan_service.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "historial_prestamos")
public class HistorialPrestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "prestamo_id", nullable = false)
    private Long prestamoId;

    @Column(nullable = false)
    private String accion;

    @Column(name = "fecha_accion", nullable = false)
    private LocalDateTime fechaAccion;

    private String observaciones;

    @Column(name = "usuario_id")
    private Long usuarioId;

    public HistorialPrestamo() {
        this.fechaAccion = LocalDateTime.now();
    }

    public HistorialPrestamo(Long prestamoId, String accion, String observaciones, Long usuarioId){
        this();
        this.prestamoId = prestamoId;
        this.accion = accion;
        this.observaciones = observaciones;
        this.usuarioId = usuarioId;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPrestamoId() {
        return prestamoId;
    }

    public void setPrestamoId(Long prestamoId) {
        this.prestamoId = prestamoId;
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

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long userId) {
        this.usuarioId = userId;
    }
}