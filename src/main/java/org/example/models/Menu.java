package org.example.models;

import java.util.List;
import java.util.UUID;

public class Menu implements IVenta{
    private UUID uuid;
    private String nombre;
    private double precio;
    private String descripcion;
    private CodigoDescuento codigoDescuento;
    private List<Producto> productos;
}
