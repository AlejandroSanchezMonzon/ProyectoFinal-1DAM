package es.dam.mcdam.repositories;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import es.dam.mcdam.managers.DataBaseManager;
import es.dam.mcdam.models.PersonaRegistrada;
import es.dam.mcdam.models.Tipo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class PersonaRegistradaRepository implements IPersonaRegistradaRepository {
    private static PersonaRegistradaRepository instance;
    private final ObservableList<PersonaRegistrada> repository = FXCollections.observableArrayList();
    //TODO: Añadir backup
    //private final Storage storage = Storage.getInstance();
    //TODO usar Logger
    DataBaseManager db = DataBaseManager.getInstance();

    private PersonaRegistradaRepository() {}

    public static PersonaRegistradaRepository getInstance() {
        if (instance == null) {
            instance = new PersonaRegistradaRepository();
        }
        return instance;
    }

    @Override
    public ObservableList<PersonaRegistrada> findAll() throws SQLException {
        String sql = "SELECT * FROM personaRegistrada";
        db.open();
        ResultSet rs = db.select(sql).orElseThrow(() -> new SQLException("Error al obtener todas las personas registradas"));
        repository.clear();
        while (rs.next()) {
            repository.add(
                    new PersonaRegistrada(
                            rs.getString("uuid"),
                            rs.getString("nombre"),
                            rs.getString("correo"),
                            rs.getString("contraseña"),
                            Tipo.valueOf(rs.getString("tipo"))
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
    public Optional<PersonaRegistrada> findById(String uuid) throws SQLException {
        String sql = "SELECT * FROM personaRegistrada WHERE uuid = ?";
        db.open();
        var rs = db.select(sql, uuid).orElseThrow(() -> new SQLException("Error al obtener la persona con uuid: " + uuid));
        while (rs.next()) {
            var persona = new PersonaRegistrada(
                    rs.getString("uuid"),
                    rs.getString("nombre"),
                    rs.getString("correo"),
                    rs.getString("contraseña"),
                    Tipo.valueOf(rs.getString("tipo"))
            );
            return Optional.of(persona);
        }
        db.close();
        return Optional.empty();
    }

    @Override
    public PersonaRegistrada save(PersonaRegistrada entity) throws SQLException {
        String sql = "INSERT INTO personaRegistrada (uuid, nombre, correo, contraseña, tipo) VALUES (?, ?, ?, ?, ?)";
        db.open();
        //TODO ¿Problemas con el UUID?
        var rs = db.insert(sql, entity.getUuid(), entity.getNombre(), entity.getCorreo(), entity.getContraseña(), entity.getTipo())
                .orElseThrow(() -> new SQLException("Error al salvar la persona: " + entity.toString()));
        db.close();
        repository.add(entity);
        return entity;
    }

    @Override
    public PersonaRegistrada update(PersonaRegistrada entity) throws SQLException {
        int index = repository.indexOf(entity);
        String sql = "UPDATE personaRegistrada SET uuid = ?, nombre = ?, correo = ?, contraseña = ?, tipo = ?";
        db.open();
        //TODO ¿Problemas con el UUID?
        var rs = db.update(sql, entity.getUuid(), entity.getNombre(), entity.getCorreo(), entity.getContraseña(), entity.getTipo());

        db.close();
        repository.set(index, entity);
        return entity;
    }

    @Override
    public PersonaRegistrada delete(PersonaRegistrada entity) throws SQLException {
        String sql = "DELETE FROM personaRegistrada WHERE uuid = ?";
        db.open();
        var rs = db.delete(sql, entity.getUuid());
        db.close();
        repository.remove(entity);
        return entity;
    }

    @Override
    public void deleteAll() throws SQLException {
        String sql = "DELETE FROM personaRegistrada";
        db.open();
        var rs = db.delete(sql);
        db.close();
        db.delete(sql);
    }

}
