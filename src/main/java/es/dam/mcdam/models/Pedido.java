package es.dam.mcdam.models;

import java.util.List;
import java.util.UUID;

public class Pedido {
    private final String uuid = UUID.randomUUID().toString();
    private final double total;
    private  String metodoPago;
    private final List<LineaPedido> compra;
    private final PersonaRegistrada cliente;

    public Pedido(List<LineaPedido> compra, PersonaRegistrada cliente, String metodoPago) {
        this.compra = compra;
        this.cliente = cliente;
        this.total = getTotal();
    }



    public String getUuid() {
        return uuid;
    }

    public double getTotal() {
        return compra.stream().mapToDouble(LineaPedido::getTotal).sum();
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
