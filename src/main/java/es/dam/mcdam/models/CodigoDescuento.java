package es.dam.mcdam.models;

import javafx.beans.property.*;

public class CodigoDescuento {
    private final StringProperty codigo;
    private final DoubleProperty porcentajeDescuento;

    public CodigoDescuento(String codigo, double porcentajeDescuento) {
        this.codigo = new SimpleStringProperty(codigo);
        this.porcentajeDescuento = new SimpleDoubleProperty(porcentajeDescuento);
    }

    public CodigoDescuento() {
        this(null, 0.0f);
    }

    public String getCodigo(){
        return codigo.get();
    }

    public void setCodigo(String codigo){
        this.codigo.set(codigo);
    }

    public StringProperty codigoProperty() {
        return codigo;
    }


    public double getPorcentajeDescuento(){
        return porcentajeDescuento.get()  ;
    }

    public void setPorcentajeDescuento(double porcentajeDescuento){
        this.porcentajeDescuento.set(porcentajeDescuento);
    }

    public DoubleProperty  porcentajeDescuentoProperty() {
        return porcentajeDescuento;
    }
}
