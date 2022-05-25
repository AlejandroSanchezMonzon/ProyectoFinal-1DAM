package es.dam.mcdam.models;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.UUID;

public class Producto{
    private String uuid = UUID.randomUUID().toString();
    private SimpleStringProperty nombre;
    private SimpleFloatProperty precio;
    private SimpleStringProperty imagen;
    private SimpleStringProperty descripcion;
    private SimpleBooleanProperty disponibilidad;
    private SimpleObjectProperty<CodigoDescuento> codigoDescuento;

    public Producto(String uuid, String nombre, float precio, String imagen, String descripcion, boolean disponibilidad, CodigoDescuento codigoDescuento){
        this.uuid = uuid;
        this.nombre = new SimpleStringProperty(nombre);
        this.precio = new SimpleFloatProperty(precio);
        this.imagen = new SimpleStringProperty(imagen);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.disponibilidad = new SimpleBooleanProperty(disponibilidad);
        this.codigoDescuento = new SimpleObjectProperty(codigoDescuento);
    }
    public Producto(String nombre, float precio, String imagen, String descripcion, boolean disponibilidad, CodigoDescuento codigoDescuento){
        this.nombre = new SimpleStringProperty(nombre);
        this.precio = new SimpleFloatProperty(precio);
        this.imagen = new SimpleStringProperty(imagen);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.disponibilidad = new SimpleBooleanProperty(disponibilidad);
        this.codigoDescuento = new SimpleObjectProperty(codigoDescuento);
    }

    public Producto() {

    }


    public String getUuid(){
        return uuid;
    }
    public void setUuid(String uuid){
        this.uuid = uuid;
    }

    public String getNombre(){
        return nombre.get();
    }

    public void setNombre(String nombre){
        this.nombre.set(nombre);
    }

    public SimpleStringProperty nombreProperty() {
        return nombre;
    }

    public float getPrecio(){
        return precio.get();
    }

    public void setPrecio(float precio){
        this.precio.set(precio);
    }

    public SimpleFloatProperty precioProperty() {
        return precio;
    }

    public String getImagen(){
        return imagen.get();
    }

    public void setImagen(String imagen){
        this.imagen.set(imagen);
    }

    public SimpleStringProperty imagenProperty() {
        return imagen;
    }

    public String getDescripcion(){
        return descripcion.get();
    }

    public void setDescripcion(String descripcion){
        this.descripcion.set(descripcion);
    }

    public SimpleStringProperty descripcionProperty() {
        return descripcion;
    }

    public boolean getDisponible(){
        return disponibilidad.get();
    }

    public void setDisponible(boolean disponible){
        this.disponibilidad.set(disponible);
    }

    public SimpleBooleanProperty disponibleProperty() {
        return disponibilidad;
    }

    public CodigoDescuento getCodigoDescuento(){
        return codigoDescuento.get();
    }

    public void setCodigoDescuento(CodigoDescuento codigoDescuento){
        this.codigoDescuento.set(codigoDescuento);
    }
}
