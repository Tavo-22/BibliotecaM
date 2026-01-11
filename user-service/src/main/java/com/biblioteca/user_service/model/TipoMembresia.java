package com.biblioteca.user_service.model;

public enum TipoMembresia {
    BASICA(3, 15),    // 3 libros máximo, 15 días préstamo
    PREMIUM(10, 30),  // 10 libros máximo, 30 días préstamo
    VIP(20, 45);      // 20 libros máximo, 45 días préstamo

    private final int maxLibros;
    private final int periodoPrestamoDias;

    TipoMembresia(int maxLibros, int periodoPrestamoDias) {
        this.maxLibros = maxLibros;
        this.periodoPrestamoDias = periodoPrestamoDias;
    }

    public int getMaxLibros() { return maxLibros; }
    public int getPeriodoPrestamoDias() { return periodoPrestamoDias; }
}
