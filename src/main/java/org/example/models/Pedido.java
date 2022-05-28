package org.example.models;

import java.util.List;
import java.util.UUID;

public class Pedido {
    private String uuid = UUID.randomUUID().toString();
    private final float total;
    private final String metodoPago;
    private final List<LineaPedido> compra;
    private final PersonaRegistrada cliente;
    private final Localizador mesa;

    public Pedido(String uuid, float total, String metodoPago, List<LineaPedido> compra, PersonaRegistrada cliente, Localizador mesa) {
        this.uuid = uuid;
        this.total = getTotal();
        this.metodoPago = metodoPago;
        this.compra = compra;
        this.cliente = cliente;
        this.mesa = mesa;
    }

    public Pedido(float total, String metodoPago, List<LineaPedido> compra, PersonaRegistrada cliente, Localizador mesa) {
        this.total = getTotal();
        this.metodoPago = metodoPago;
        this.compra = compra;
        this.cliente = cliente;
        this.mesa = mesa;
    }

    public String getUuid() {
        return uuid;
    }

    public float getTotal() {
        float total = 0;
        for (LineaPedido lineaPedido : compra) {
            total += lineaPedido.getPrecio();
        }
        return total;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public List<LineaPedido> getCompra() {

        return compra;
    }

    public PersonaRegistrada getCliente() {
        return cliente;
    }

    public Localizador getMesa() {
        return mesa;
    }

    @Override
    public String toString() {
        return "Pedido{" + "uuid=" + uuid + ", total=" + total + ", metodoPago=" + metodoPago + ", compra=" + compra + ", cliente=" + cliente + ", mesa=" + mesa + '}';
    }
}
