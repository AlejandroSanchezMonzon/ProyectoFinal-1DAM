package es.dam.mcdam.models;

import java.util.List;
import java.util.UUID;

public class Pedido {
    private String uuid = UUID.randomUUID().toString();
    private final float total;
    private final String metodoPago;
    private List<LineaPedido> compra = null;
    private final PersonaRegistrada cliente;

    public Pedido(String uuid, float total, String metodoPago, List<LineaPedido> compra, PersonaRegistrada cliente) {
        this.uuid = uuid;
        this.total = getTotal();
        this.metodoPago = metodoPago;
        this.compra = compra;
        this.cliente = cliente;
    }

    public Pedido(float total, String metodoPago, List<LineaPedido> compra, PersonaRegistrada cliente) {
        this.total = getTotal();
        this.metodoPago = metodoPago;
        this.compra = compra;
        this.cliente = cliente;
    }

    public String getUuid() {
        return uuid;
    }

    public float getTotal() {
        return (float) compra.stream().mapToDouble(LineaPedido::getTotal).sum();
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

    @Override
    public String toString() {
        return "Pedido{" + "uuid=" + uuid + ", total=" + total + ", metodoPago=" + metodoPago + ", compra=" + compra + ", cliente=" + cliente + '}';
    }
}
