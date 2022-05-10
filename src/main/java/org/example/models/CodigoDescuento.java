package org.example.models;

import java.util.UUID;

public class CodigoDescuento {
    private final String uuid = UUID.randomUUID().toString();
    private final String codigo;
    private final float porcentajeDescuento;

    public CodigoDescuento(String codigo, float porcentajeDescuento) {
        this.codigo = codigo;
        this.porcentajeDescuento = porcentajeDescuento;
    }
}
