package es.dam.mcdam.repositories;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import es.dam.mcdam.managers.DataBaseManager;
import es.dam.mcdam.models.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public class PedidoRepository implements IPedidoRepository{
    private static PedidoRepository instance;
    private final ObservableList<Pedido> repository = FXCollections.observableArrayList();
    //TODO: Añadir backup
    //private final Storage storage = Storage.getInstance();
    //TODO usar Logger
    DataBaseManager db = DataBaseManager.getInstance();

    private PedidoRepository() {}

    public static PedidoRepository getInstance() {
        if (instance == null) {
            instance = new PedidoRepository();
        }
        return instance;
    }

    @Override
    public Optional<ObservableList<Pedido>> findAll() throws SQLException {
        String sql = "SELECT * FROM pedido";
        db.open();
        ResultSet rs = db.select(sql).orElseThrow(() -> new SQLException("Error al obtener todos los pedidos"));
        repository.clear();
        while (rs.next()) {
            repository.add(
                    new Pedido(
                            rs.getString("uuuid"),
                            rs.getFloat("total"),
                            rs.getString("metodoPago"),
                            (java.util.List<LineaPedido>)rs.getObject("compra"),
                            (PersonaRegistrada) rs.getObject("cliente"),
                            (Localizador) rs.getObject("mesa")
                    )
            );
        }
        db.close();
        if (repository.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(repository);
    }

    @Override
    public Optional<Pedido> findById(String uuid) throws SQLException {
        String sql = "SELECT * FROM pedido WHERE uuid = ?";
        db.open();
        var rs = db.select(sql, uuid).orElseThrow(() -> new SQLException("Error al obtener el pedido con uuid: " + uuid));
        while (rs.next()) {
            var pedido = new Pedido(
                    rs.getString("uuuid"),
                    rs.getFloat("total"),
                    rs.getString("metodoPago"),
                    (java.util.List<LineaPedido>)rs.getObject("compra"),
                    (PersonaRegistrada) rs.getObject("cliente"),
                    (Localizador) rs.getObject("mesa")
            );
            return Optional.of(pedido);
        }
        db.close();
        return Optional.empty();
    }

    @Override
    public Pedido save(Pedido entity) throws SQLException {
        String sql = "INSERT INTO pedido (uuid, total, metodoPago, compra, cliente, mesa) VALUES (?, ?, ?, ?, ?, ?)";
        db.open();
        //TODO ¿Problemas con el UUID?
        var rs = db.insert(sql, entity.getUuid(), entity.getTotal(), entity.getMetodoPago(), entity.getCompra(), entity.getCliente(), entity.getMesa())
                .orElseThrow(() -> new SQLException("Error al salvar el pedido: " + entity.toString()));
        db.close();
        repository.add(entity);
        return entity;
    }

    @Override
    public Pedido update(Pedido entity) throws SQLException {
        int index = repository.indexOf(entity);
        String sql = "UPDATE pedido SET uuid = ?, total = ?, metodoPago = ?, compra = ?, cliente = ?, mesa = ?";
        db.open();
        //TODO ¿Problemas con el UUID?
        var rs = db.update(sql, entity.getUuid(), entity.getTotal(), entity.getMetodoPago(), entity.getCompra(), entity.getCliente(), entity.getMesa());

        db.close();
        repository.set(index, entity);
        return entity;
    }

    @Override
    public Pedido delete(Pedido entity) throws SQLException {
        String sql = "DELETE FROM pedido WHERE uuid = ?";
        db.open();
        var rs = db.delete(sql, entity.getUuid());
        db.close();
        repository.remove(entity);
        return entity;
    }

    @Override
    public void deleteAll() throws SQLException {
        String sql = "DELETE FROM pedido";
        db.open();
        var rs = db.delete(sql);
        db.delete(sql);
    }
}
