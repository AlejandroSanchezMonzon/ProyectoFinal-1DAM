/**
 @author Información mostrada en la documentación.
 */

package es.dam.mcdam.repositories;

import es.dam.mcdam.managers.DataBaseManager;
import es.dam.mcdam.models.LineaPedido;
import es.dam.mcdam.models.Pedido;
import es.dam.mcdam.models.PersonaRegistrada;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PedidoRepository implements IPedidoRepository{
    //ESTADO
    private static PedidoRepository instance;
    private final ObservableList<Pedido> repository = FXCollections.observableArrayList();
    //private final Storage storage = Storage.getInstance();
    DataBaseManager db = DataBaseManager.getInstance();

    //CONSTRUCTOR
    private PedidoRepository() {}

    //SINGLETON
    public static PedidoRepository getInstance() {
        if (instance == null) {
            instance = new PedidoRepository();
        }
        return instance;
    }

    /**
     * Método que devuelve una lista de pedidos.
     * @return
     * @throws SQLException
     */
    @Override
    public ObservableList<Pedido> findAll() throws SQLException {
        String sql = "SELECT * FROM pedido";
        db.open();
        ResultSet rs = db.select(sql).orElseThrow(() -> new SQLException("Error al obtener todos los pedidos"));
        repository.clear();
        while (rs.next()) {
            String lineaPedido = (String) rs.getObject("compra");
            repository.add(
                    new Pedido(
                            List.of((LineaPedido)rs.getObject("compra")),
                            (PersonaRegistrada) rs.getObject("cliente"),
                            rs.getString("metodoPago")
                    )
            );

        }
        db.close();
        if (repository.isEmpty()) {
            System.out.println("Aún no hay  datos en este repositorio");
        }
        return repository;
    }

    /**
     * Método que devuelve los pedidos de un cliente concreto.
     * @param uuid Id del elemento
     * @return
     * @throws SQLException
     */
    @Override
    public Optional<Pedido> findById(String uuid) throws SQLException {
        String sql = "SELECT * FROM pedido WHERE uuid = ?";
        db.open();
        var rs = db.select(sql, uuid).orElseThrow(() -> new SQLException("Error al obtener el pedido con uuid: " + uuid));
        while (rs.next()) {
            var pedido = new Pedido(
                    List.of((LineaPedido)rs.getObject("compra")),
                    (PersonaRegistrada) rs.getObject("cliente"),
                    rs.getString("metodoPago")
            );
            return Optional.of(pedido);
        }
        db.close();
        return Optional.empty();
    }

    /**
     * Método que sirve para salvar un pedido.
     * @param entity Elemento a insertar
     * @return
     * @throws SQLException
     */
    @Override
    public Pedido save(Pedido entity) throws SQLException {
        String sql = "INSERT INTO pedido (uuid, total, metodoPago, compra, cliente) VALUES (?, ?, ?, ?, ?)";
        db.open();
        //TODO ¿Problemas con el UUID?
        var rs = db.insert(sql, entity.getUuid(), entity.getTotal(), entity.getMetodoPago(), entity.getCompra(), entity.getCliente())
                .orElseThrow(() -> new SQLException("Error al salvar el pedido: " + entity.toString()));
        db.close();
        repository.add(entity);
        return entity;
    }

    /**
     * Método que sirve para actualizar un pedido.
     * @param entity Elemento a actualizar
     * @return
     * @throws SQLException
     */
    @Override
    public Pedido update(Pedido entity) throws SQLException {
        int index = repository.indexOf(entity);
        String sql = "UPDATE pedido SET uuid = ?, total = ?, metodoPago = ?, compra = ?, cliente = ?";
        db.open();
        //TODO ¿Problemas con el UUID?
        var rs = db.update(sql, entity.getUuid(), entity.getTotal(), entity.getMetodoPago(), entity.getCompra(), entity.getCliente());

        db.close();
        repository.set(index, entity);
        return entity;
    }

    /**
     * Método que sirve para eliminar un pedido.
     * @param entity
     * @return
     * @throws SQLException
     */
    @Override
    public Pedido delete(Pedido entity) throws SQLException {
        String sql = "DELETE FROM pedido WHERE uuid = ?";
        db.open();
        var rs = db.delete(sql, entity.getUuid());
        db.close();
        repository.remove(entity);
        return entity;
    }

    /**
     * Método que elimina todos los pedidos.
     * @throws SQLException
     */
    @Override
    public void deleteAll() throws SQLException {
        String sql = "DELETE FROM pedido";
        db.open();
        var rs = db.delete(sql);
        db.delete(sql);
    }

    /**
     * Método que devuelve una lista de elementos que sean Strings.
     * @return
     * @throws SQLException
     */
    public List<String> findAllString() throws SQLException {
        String sql = "SELECT * FROM pedido";
        db.open();
        ResultSet rs = db.select(sql).orElseThrow(() -> new SQLException("Error al obtener todos los pedidos"));
        List<String> pedido= new ArrayList<>();
        while (rs.next()) {
            String lineaPedido = (String) rs.getObject("compra");
            pedido.add(rs.getString("compra"));
            pedido.add(rs.getString("cliente"));
            pedido.add(rs.getString("metodoPago"));
        }
        db.close();
        if (pedido.isEmpty()) {
            System.out.println("Aún no hay datos de pedidos");
        }
        return pedido;
    }
}
