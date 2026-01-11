package com.biblioteca.loan_service.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "multas")
public class Multa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Column(name = "prestamo_id")
    private Long prestamoId;

    @Column(nullable = false, precision =10, scale = 2)
    private BigDecimal monto;

    @Column(name = "fecha_multa", nullable = false)
    private LocalDate fechaMulta;

    @Column(name = "fecha_pago")
    private LocalDate fechaPago;

    @Enumerated(EnumType.STRING)
    private EstadoMulta estado;

    private String descripcion;

    public Multa() {
        this.fechaMulta = LocalDate.now();
        this.estado = EstadoMulta.PENDIENTE;

    }

    public Multa(Long usuarioId, Long prestamoId, BigDecimal monto, String descripcion){
        this();
        this.usuarioId = usuarioId;
        this.prestamoId = prestamoId;
        this.monto = monto;
        this.descripcion = descripcion;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getPrestamoId() {
        return prestamoId;
    }

    public void setPrestamoId(Long prestamoId) {
        this.prestamoId = prestamoId;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public LocalDate getFechaMulta() {
        return fechaMulta;
    }

    public void setFechaMulta(LocalDate fechaMulta) {
        this.fechaMulta = fechaMulta;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }

    public EstadoMulta getEstado() {
        return estado;
    }

    public void setEstado(EstadoMulta estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
