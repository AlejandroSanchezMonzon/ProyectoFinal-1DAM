package es.dam.mcdam.models;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ItemCarrito {
    private final SimpleStringProperty uuid;
    private final SimpleStringProperty nombre;
    private final SimpleStringProperty imagen;
    private final SimpleFloatProperty precio;
    private final SimpleIntegerProperty cantidad;
    private double total;

    public ItemCarrito(String uuid, String nombre, String imagen, float precio, int cantidad) {
        this.uuid = new SimpleStringProperty(uuid);
        this.nombre = new SimpleStringProperty(nombre);
        this.imagen = new SimpleStringProperty(imagen);
        this.precio = new SimpleFloatProperty(precio);
        this.cantidad = new SimpleIntegerProperty(cantidad);
        this.total = precio * cantidad;
    }

    public String getUuid() {
        return uuid.get();
    }
    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public SimpleStringProperty nombreProperty() {
        return nombre;
    }

    public String getImagen() {
        return imagen.get();
    }

    public void setImagen(String imagen) {
        this.imagen.set(imagen);
    }

    public SimpleStringProperty imagenProperty() {
        return imagen;
    }

    public double getPrecio() {
        return precio.get();
    }

    public void setPrecio(float precio) {
        this.precio.set(precio);
    }

    public SimpleFloatProperty precioProperty() {
        return precio;
    }

    public int getCantidad() {
        return cantidad.get();
    }

    public void setCantidad(int cantidad) {
        this.cantidad.set(cantidad);
        this.total = precio.get() * cantidad;
    }

    public SimpleIntegerProperty cantidadProperty() {
        return cantidad;
    }

    public double getTotal() {
        return total;
    }


    @Override
    public String toString() {
        return "CarritoItem{" +
                "nombre=" + nombre +
                ", imagen=" + imagen +
                ", precio=" + precio +
                ", cantidad=" + cantidad +
                ", total=" + total +
                '}';
    }
}
