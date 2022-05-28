package org.example.models;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CodigoDescuento {
    private final StringProperty codigo;
    private final FloatProperty porcentajeDescuento;

    public CodigoDescuento(String codigo, float porcentajeDescuento) {
        this.codigo = new SimpleStringProperty(codigo);
        this.porcentajeDescuento = new SimpleFloatProperty(porcentajeDescuento);
    }

    public String getCodigo(){
        return codigo.get();
    }

    public void setNombre(String codigo){
        this.codigo.set(codigo);
    }

    public StringProperty  codigoProperty() {
        return codigo;
    }


    public float getPorcentajeDescuento(){
        return porcentajeDescuento.get()  ;
    }

    public void setPorcentajeDescuento(Float porcentajeDescuento){
        this.porcentajeDescuento.set(porcentajeDescuento);
    }

    public FloatProperty  porcentajeDescuentoProperty() {
        return porcentajeDescuento;
    }
}
