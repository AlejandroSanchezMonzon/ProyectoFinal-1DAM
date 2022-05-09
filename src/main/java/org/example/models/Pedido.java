package org.example.models;

import java.util.List;
import java.util.UUID;

public class Pedido {
    private UUID uuid;
    private int id;
    private double precio;
    private String metodoPago;
    private List<IVenta> compra;
    private Cliente cliente;
    private Localizador mesa;
}
