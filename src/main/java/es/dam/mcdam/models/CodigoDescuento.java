package es.dam.mcdam.models;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;

public class CodigoDescuento {
    private final SimpleStringProperty codigo;
    private final SimpleFloatProperty porcentajeDescuento;

    public CodigoDescuento(String codigo, float porcentajeDescuento) {
        this.codigo = new SimpleStringProperty(codigo);
        this.porcentajeDescuento = new SimpleFloatProperty(porcentajeDescuento);
    }

    public String getCodigo() {
        return codigo.get();
    }

    public float getPorcentajeDescuento() {
        return porcentajeDescuento.get();
    }
}
