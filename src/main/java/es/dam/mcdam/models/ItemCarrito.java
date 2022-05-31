package es.dam.mcdam.models;

import javafx.beans.property.*;

public class ItemCarrito {
    private final StringProperty uuid;
    private final StringProperty nombre;
    private final StringProperty imagen;
    private final DoubleProperty precio;
    private final IntegerProperty cantidad;
    private final DoubleProperty total;

    public ItemCarrito(String uuid, String nombre, String imagen, double precio, int cantidad) {
        this.uuid = new SimpleStringProperty(uuid);
        this.nombre = new SimpleStringProperty(nombre);
        this.imagen = new SimpleStringProperty(imagen);
        this.precio = new SimpleDoubleProperty(precio);
        this.cantidad = new SimpleIntegerProperty(cantidad);
        this.total = new SimpleDoubleProperty(precio * cantidad);
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

    public StringProperty nombreProperty() {
        return nombre;
    }

    public String getImagen() {
        return imagen.get();
    }

    public void setImagen(String imagen) {
        this.imagen.set(imagen);
    }

    public StringProperty imagenProperty() {
        return imagen;
    }

    public double getPrecio() {
        return precio.get();
    }

    public void setPrecio(double precio) {
        this.precio.set(precio);
    }

    public DoubleProperty precioProperty() {
        return precio;
    }

    public int getCantidad() {
        return cantidad.get();
    }

    public void setCantidad(int cantidad) {
        this.cantidad.set(cantidad);
        this.total.set(precio.get() * cantidad);
    }

    public IntegerProperty cantidadProperty() {
        return cantidad;
    }

    public double getTotal() {
        return total.get();
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
