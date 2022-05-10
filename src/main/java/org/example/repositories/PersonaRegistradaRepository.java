package org.example.repositories;

import org.example.managers.DataBaseManager;
import org.example.models.PersonaRegistrada;
import org.example.models.Tipo;
import org.example.services.Storage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PersonaRegistradaRepository implements IPersonaRegistradaRepository{
    private static PersonaRegistradaRepository instance;
    private final List<PersonaRegistrada> repository = new ArrayList<>();
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
    public Optional<List<PersonaRegistrada>> findAll() throws SQLException {
        String sql = "SELECT * FROM personaRegistrada";
        db.open();
        ResultSet rs = db.select(sql).orElseThrow(() -> new SQLException("Error al obtener todas las personas registradas"));
        repository.clear();
        while (rs.next()) {
            repository.add(
                    new PersonaRegistrada(
                            rs.getString("uuuid"),
                            rs.getString("nombre"),
                            rs.getString("correo"),
                            rs.getString("contraseña"),
                            (Tipo) rs.getObject("tipo")
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
    public Optional<PersonaRegistrada> findById(UUID uuid) throws SQLException {
        String sql = "SELECT * FROM personaRegistrada WHERE uuid = ?";
        db.open();
        var rs = db.select(sql, uuid).orElseThrow(() -> new SQLException("Error al obtener la persona con uuid: " + uuid));
        while (rs.next()) {
            var persona = new PersonaRegistrada(
                    rs.getString("uuuid"),
                    rs.getString("nombre"),
                    rs.getString("correo"),
                    rs.getString("contraseña"),
                    (Tipo) rs.getObject("tipo")
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
        String sql = "UPDATE personaRegistrada SET uuid = ?, nombre = ?, correo = ?, contraseña = ?, tipo = ?)";
        db.open();
        //TODO ¿Problemas con el UUID?
        var rs = db.update(sql, entity.getUuid(), entity.getNombre(), entity.getCorreo(), entity.getContraseña(), entity.getTipo());
              //  .orElseThrow(() -> new SQLException("Error al actualizar la persona: " + entity.toString()));

        db.close();
        repository.set(index, entity);
        return entity;
    }

    @Override
    public PersonaRegistrada delete(PersonaRegistrada entity) throws SQLException {
        String sql = "DELETE FROM personaRegistrada WHERE uuid = ?";
        db.open();
        var rs = db.delete(sql, entity.getUuid());
        //  .orElseThrow(() -> new SQLException("Error al eliminar la persona: " + entity.toString()));
        db.close();
        repository.remove(entity);
        return entity;
    }
}
