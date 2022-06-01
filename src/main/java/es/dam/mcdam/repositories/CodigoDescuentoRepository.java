/**
 @author Información mostrada en la documentación.
 */

package es.dam.mcdam.repositories;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import es.dam.mcdam.managers.DataBaseManager;
import es.dam.mcdam.models.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class CodigoDescuentoRepository implements ICodigoDescuentoRepository{
    //ESTADO
    private static CodigoDescuentoRepository instance;
    private final ObservableList<CodigoDescuento> repository = FXCollections.observableArrayList();
    //private final Storage storage = Storage.getInstance();
    DataBaseManager db = DataBaseManager.getInstance();

    //CONSTRUCTOR
    private CodigoDescuentoRepository() {
    }

    //SINGLETON
    public static CodigoDescuentoRepository getInstance() {
        if (instance == null) {
            instance = new CodigoDescuentoRepository();
        }
        return instance;
    }

    /**
     * Método que devuelve el listado de códigos de descuento.
     * @return
     * @throws SQLException
     */
    @Override
    public ObservableList<CodigoDescuento> findAll() throws SQLException {
        String sql = "SELECT * FROM codigoDescuento";
        db.open();
        ResultSet rs = db.select(sql).orElseThrow(() -> new SQLException("Error al obtener todos los códigos de descuento"));
        repository.clear();
        while (rs.next()) {
            repository.add(
                    new CodigoDescuento(
                            rs.getString("codigo"),
                            rs.getFloat("porcendesc")
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
     * Método que devuelve el código de descuento que se le pasa como parámetro (ID).
     * @param uuid Id del elemento
     * @return
     * @throws SQLException
     */
    @Override
    public Optional<CodigoDescuento> findById(String uuid) throws SQLException {
        String sql = "SELECT * FROM codigoDescuento WHERE codigo = ?";
        db.open();
        ResultSet rs = db.select(sql, uuid).orElseThrow(() -> new SQLException("Error al obtener el codigo de descuento: " + uuid));
        while (rs.next()) {
            var codigoDescuento = new CodigoDescuento(
                    rs.getString("codigo"),
                    rs.getFloat("porcendesc")
            );
            return Optional.of(codigoDescuento);
        }
        db.close();
        return Optional.empty();
    }

    /**
     * Método que guarda un código de descuento en la base de datos.
     * @param entity Elemento a insertar
     * @return
     * @throws SQLException
     */
    @Override
    public CodigoDescuento save(CodigoDescuento entity) throws SQLException {
        String sql = "INSERT INTO codigoDescuento (codigo, porcendesc) VALUES (?, ?)";
        db.open();
        //TODO ¿Problemas con el UUID?
        ResultSet rs = db.insert(sql, entity.getCodigo(), entity.getPorcentajeDescuento())
                .orElseThrow(() -> new SQLException("Error al salvar el código de descuento: " + entity.toString()));
        db.close();
        repository.add(entity);
        return entity;
    }

    /**
     * Método que actualiza un código de descuento en la base de datos.
     * @param entity Elemento a actualizar
     * @return
     * @throws SQLException
     */
    @Override
    public CodigoDescuento update(CodigoDescuento entity) throws SQLException {
        int index = repository.indexOf(entity);
        String sql = "UPDATE codigoDescuento SET porcendesc = ? WHERE codigo = ?";
        db.open();
        //TODO ¿Problemas con el UUID?
        var rs = db.update(sql, entity.getPorcentajeDescuento(), entity.getCodigo());
        db.close();
        repository.set(index, entity);
        return entity;
    }

    /**
     * Método que elimina un código de descuento de la base de datos.
     * @param entity
     * @return
     * @throws SQLException
     */
    @Override
    public CodigoDescuento delete(CodigoDescuento entity) throws SQLException {
        String sql = "DELETE FROM CodigoDescuento WHERE codigo = ?";
        db.open();
        var rs = db.delete(sql, entity.getCodigo());
        db.close();
        repository.remove(entity);
        return entity;
    }

    /**
     * Método que elimina todos los códigos de descuento de la base de datos.
     * @throws SQLException
     */
    @Override
    public void deleteAll() throws SQLException {
        String sql = "DELETE FROM codigoDescuento";
        db.open();
        var rs = db.delete(sql);
        db.delete(sql);
    }
}
