package org.example.models;

import java.util.UUID;

public class Producto implements IVenta{
    private UUID uuid;
    private String nombre;
    private double precio;
    private String descripcion;
    private boolean disponible;
    private CodigoDescuento codigoDescuento;


}
