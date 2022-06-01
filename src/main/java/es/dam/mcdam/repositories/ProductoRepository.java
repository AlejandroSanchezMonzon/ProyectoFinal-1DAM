/**
 @author Información mostrada en la documentación.
 */

package es.dam.mcdam.repositories;

import es.dam.mcdam.managers.DataBaseManager;
import es.dam.mcdam.models.CodigoDescuento;
import es.dam.mcdam.models.Producto;
import es.dam.mcdam.services.Storage;
import es.dam.mcdam.utils.Properties;
import es.dam.mcdam.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;


public class ProductoRepository implements IProductoRepository {
    //ESTADO
    private static ProductoRepository instance;
    private final ObservableList<Producto> repository;
    private final Storage storage;
    DataBaseManager db;


    //CONSTRUCTOR
    private ProductoRepository(DataBaseManager db, Storage storage) {
        this.db = db;
        this.storage = storage;
        this.repository = FXCollections.observableArrayList();
    }



    //SINGLETON
    public static ProductoRepository getInstance(DataBaseManager db, Storage storage) {
        if (instance == null) {
            instance = new ProductoRepository(db, storage);
        }
        return instance;
    }

    /**
     * Método que devuelve una lista de productos.
     * @return
     * @throws SQLException
     */
    @Override
    public ObservableList<Producto> findAll() throws SQLException {
        String sql = "SELECT * FROM producto";
        db.open();
        ResultSet rs = db.select(sql).orElseThrow(() -> new SQLException("Error al obtener todos los productos"));
        repository.clear();
        while (rs.next()) {
            repository.add(
                    new Producto(
                            rs.getString("uuid"),
                            rs.getString("nombre"),
                            rs.getFloat("precio"),
                            rs.getString("imagen"),
                            rs.getString("descripcion"),
                            rs.getBoolean("disponibilidad"),
                            new CodigoDescuento(rs.getString("coddescuento"),10.0f)
                    )
            );

        }
        db.close();
        if (repository.isEmpty()) {
            System.out.println("Aún no hay datos en este repositorio.");
        }
        return repository;
    }

    /**
     * Método que devuelve un producto con un ID específico.
     * @param uuid Id del elemento
     * @return
     * @throws SQLException
     */
    @Override
    public Optional<Producto> findById(String uuid) throws SQLException {
        String sql = "SELECT * FROM producto, codigoDescuento WHERE producto.uuid = ?";
        db.open();
        var rs = db.select(sql, uuid).orElseThrow(() -> new SQLException("Error al obtener el producto con uuid: " + uuid));
        while (rs.next()) {
            var producto = new Producto(
                    rs.getString("uuid"),
                    rs.getString("nombre"),
                    rs.getFloat("precio"),
                    rs.getString("imagen"),
                    rs.getString("descripcion"),
                    rs.getBoolean("disponibilidad"),
                    new CodigoDescuento(rs.getString("coddescuento"), rs.getInt("porcendesc"))
            );
            return Optional.of(producto);
        }
        db.close();
        return Optional.empty();
    }

    /**
     * Método que guarda un producto en la base de datos.
     * @param entity Elemento a insertar
     * @return
     * @throws SQLException
     */
    @Override
    public Producto save(Producto entity) throws SQLException {
        String sql = "INSERT INTO producto (uuid, nombre, precio, imagen, descripcion, disponibilidad, coddescuento) VALUES (?, ?, ?, ?, ?, ?, ?)";
        db.open();
        //TODO ¿Problemas con el UUID?
        var rs = db.insert(sql, entity.getUuid(), entity.getNombre(), entity.getPrecio(), entity.getImagen(), entity.getDescripcion(), entity.getDisponible(), entity.getCodigoDescuento().getCodigo());
        db.close();
        repository.add(entity);
        return entity;
    }

    /**
     * Método que actualiza un producto en la base de datos.
     * @param entity Elemento a actualizar
     * @return
     * @throws SQLException
     */
    @Override
    public Producto update(Producto entity) throws SQLException {
        int index = repository.indexOf(entity);
        String sql = "UPDATE producto SET nombre = ?, precio = ?, imagen = ?, descripcion = ?, disponibilidad = ?, coddescuento = ? WHERE uuid = ?";
        db.open();
        //TODO ¿Problemas con el UUID?
        var rs = db.update(sql, entity.getNombre(), entity.getPrecio(), entity.getImagen(), entity.getDescripcion(), entity.getDisponible(), entity.getCodigoDescuento().getCodigo(), entity.getUuid());

        db.close();
        repository.set(index, entity);
        return entity;
    }

    /**
     * Método que elimina un producto de la base de datos.
     * @param entity
     * @return
     * @throws SQLException
     */
    @Override
    public Producto delete(Producto entity) throws SQLException {
        String sql = "DELETE FROM producto WHERE uuid = ?";
        db.open();
        var rs = db.delete(sql, entity.getUuid());
        db.close();
        repository.remove(entity);
        return entity;
    }

    /**
     * Método que elimina todos los productos de la base de datos.
     * @throws SQLException
     */
    @Override
    public void deleteAll() throws SQLException {
        String sql = "DELETE FROM producto";
        db.open();
        var rs = db.delete(sql);
        db.close();
        repository.removeAll();
    }

    /**
     * Método que almacena la imagen de un producto específico en la base de datos.
     * @param p
     * @throws IOException
     */
    public void storeImagen(Producto p) throws IOException {
        String[] ruta = p.getImagen().split(File.separator);
        String destination = ruta[ruta.length - 1];
        String source = p.getImagen().replace("file:", "");
        System.out.println("Origen: " + source);
        System.out.println("Destino: " + p.getImagen());
        storage.copyFile(source, p.getImagen());
        p.setImagen(destination);
    }
}
