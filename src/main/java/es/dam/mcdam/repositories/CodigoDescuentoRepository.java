package es.dam.mcdam.repositories;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import es.dam.mcdam.managers.DataBaseManager;
import es.dam.mcdam.models.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class CodigoDescuentoRepository implements ICodigoDescuentoRepository{
    private static CodigoDescuentoRepository instance;
    private final ObservableList<CodigoDescuento> repository = FXCollections.observableArrayList();
    //TODO: Añadir backup
    //private final Storage storage = Storage.getInstance();
    //TODO usar Logger
    DataBaseManager db = DataBaseManager.getInstance();

    private CodigoDescuentoRepository() {
    }

    public static CodigoDescuentoRepository getInstance() {
        if (instance == null) {
            instance = new CodigoDescuentoRepository();
        }
        return instance;
    }

    @Override
    public Optional<ObservableList<CodigoDescuento>> findAll() throws SQLException {
        String sql = "SELECT * FROM codigoDescuento";
        db.open();
        ResultSet rs = db.select(sql).orElseThrow(() -> new SQLException("Error al obtener todos los códigos de descuento"));
        repository.clear();
        while (rs.next()) {
            repository.add(
                    new CodigoDescuento(
                            rs.getString("codigo"),
                            rs.getFloat("porcentajeDescuento")
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
    public Optional<CodigoDescuento> findById(String uuid) throws SQLException {
        String sql = "SELECT * FROM codigoDescuento WHERE codigo = ?";
        db.open();
        ResultSet rs = db.select(sql, uuid).orElseThrow(() -> new SQLException("Error al obtener el codigo de descuento: " + uuid));
        while (rs.next()) {
            var codigoDescuento = new CodigoDescuento(
                    rs.getString("codigo"),
                    rs.getFloat("porcentajeDescuento")
            );
            return Optional.of(codigoDescuento);
        }
        db.close();
        return Optional.empty();
    }

    @Override
    public CodigoDescuento save(CodigoDescuento entity) throws SQLException {
        String sql = "INSERT INTO codigoDescuento (codigo, porcentajeDecuento) VALUES (?, ?)";
        db.open();
        //TODO ¿Problemas con el UUID?
        ResultSet rs = db.insert(sql, entity.getCodigo(), entity.getPorcentajeDescuento())
                .orElseThrow(() -> new SQLException("Error al salvar el código de descuento: " + entity.toString()));
        db.close();
        repository.add(entity);
        return entity;
    }

    @Override
    public CodigoDescuento update(CodigoDescuento entity) throws SQLException {
        int index = repository.indexOf(entity);
        String sql = "UPDATE codigoDescuento SET codigo = ?, porcentajeDescuento = ?";
        db.open();
        //TODO ¿Problemas con el UUID?
        var rs = db.update(sql, entity.getCodigo(), entity.getPorcentajeDescuento());

        db.close();
        repository.set(index, entity);
        return entity;
    }

    @Override
    public CodigoDescuento delete(CodigoDescuento entity) throws SQLException {
        String sql = "DELETE FROM CodigoDescuento WHERE codigo = ?";
        db.open();
        var rs = db.delete(sql, entity.getCodigo());
        db.close();
        repository.remove(entity);
        return entity;
    }

    @Override
    public void deleteAll() throws SQLException {
        String sql = "DELETE FROM codigoDescuento";
        db.open();
        var rs = db.delete(sql);
        db.delete(sql);
    }
}
