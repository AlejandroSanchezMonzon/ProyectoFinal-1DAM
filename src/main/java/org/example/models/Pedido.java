package org.example.models;

import java.util.List;
import java.util.UUID;

public class Pedido {
    //TODO ¿Debería ser reactivo?
    private UUID uuid;
    private int id;
    private double total;
    private String metodoPago;
    private List<LineaPedido> compra;
    private PersonaRegistrada cliente;
    private Localizador mesa;
}
