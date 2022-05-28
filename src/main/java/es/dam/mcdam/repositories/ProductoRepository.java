package es.dam.mcdam.repositories;

import es.dam.mcdam.services.Storage;
import es.dam.mcdam.utils.Properties;
import es.dam.mcdam.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import es.dam.mcdam.managers.DataBaseManager;
import es.dam.mcdam.models.CodigoDescuento;
import es.dam.mcdam.models.Producto;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;


public class ProductoRepository implements IProductoRepository {
    private static ProductoRepository instance;
    private final ObservableList<Producto> repository = FXCollections.observableArrayList();
    //TODO: Añadir backup
    private final Storage storage = Storage.getInstance();
    //TODO usar Logger
    DataBaseManager db = DataBaseManager.getInstance();

    private ProductoRepository() {}

    public static ProductoRepository getInstance() {
        if (instance == null) {
            instance = new ProductoRepository();
        }
        return instance;
    }

    @Override
    public ObservableList<Producto> findAll() throws SQLException {
        String sql = "SELECT * FROM producto, codigoDescuento";
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
                            new CodigoDescuento(rs.getString("coddescuento"), rs.getInt("porcendesc"))
                    )
            );
        }
        db.close();
        if (repository.isEmpty()) {
            System.out.println("Aún no hay datos en este repositorio.");
        }
        return repository;
    }

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

    @Override
    public Producto update(Producto entity) throws SQLException {
        int index = repository.indexOf(entity);
        String sql = "UPDATE producto SET uuid = ?, nombre = ?, precio = ?, imagen = ?, descripcion = ?, disponible = ?, codigoDescuento = ?)";
        db.open();
        //TODO ¿Problemas con el UUID?
        var rs = db.update(sql, entity.getUuid(), entity.getNombre(), entity.getPrecio(), entity.getImagen(), entity.getDescripcion(), entity.getDisponible(), entity.getCodigoDescuento().getCodigo());

        db.close();
        repository.set(index, entity);
        return entity;
    }

    @Override
    public Producto delete(Producto entity) throws SQLException {
        String sql = "DELETE FROM producto WHERE uuid = ?";
        db.open();
        var rs = db.delete(sql, entity.getUuid());
        db.close();
        repository.remove(entity);
        return entity;
    }

    @Override
    public void deleteAll() throws SQLException {
        String sql = "DELETE FROM producto";
        db.open();
        var rs = db.delete(sql);
        db.delete(sql);
    }

    public void storeImagen(Producto p) throws IOException {
        String destination = Properties.IMAGES_DIR + File.separator + p.getUuid() + "." + Utils.getFileExtension(p.getImagen()).orElse("png");
        String source = p.getImagen().replace("file:", "");
        System.out.println("Origen: " + source);
        System.out.println("Destino: " + destination);
        storage.copyFile(source, destination);
        p.setImagen(destination);
    }

    public void deleteImagen(Producto p) throws IOException {
        String source = Properties.IMAGES_DIR + File.separator + p.getUuid() + "." + Utils.getFileExtension(p.getImagen()).orElse("png");
        storage.deleteFile(source);
    }
}
