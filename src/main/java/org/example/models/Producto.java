package org.example.models;

import java.util.UUID;

public class Producto{
    private String uuid = UUID.randomUUID().toString();
    private final String nombre;
    private final float precio;
    private final String imagen;
    private final String descripcion;
    private final boolean disponible;
    private final CodigoDescuento codigoDescuento;

    public Producto(String uuid, String nombre, float precio, String imagen, String descripcion, boolean disponible, CodigoDescuento codigoDescuento){
        this.uuid = uuid;
        this.nombre = nombre;
        this.precio = precio;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.disponible = disponible;
        this.codigoDescuento = codigoDescuento;
    }
    public Producto(String nombre, float precio, String imagen, String descripcion, boolean disponible, CodigoDescuento codigoDescuento){
        this.nombre = nombre;
        this.precio = precio;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.disponible = disponible;
        this.codigoDescuento = codigoDescuento;
    }

    public String getUuid(){
        return uuid;
    }

    public String getNombre(){
        return nombre;
    }

    public float getPrecio(){
        return precio;
    }

    public String getImagen(){
        return imagen;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public boolean isDisponible(){
        return disponible;
    }

    public CodigoDescuento getCodigoDescuento(){
        return codigoDescuento;
    }
}
