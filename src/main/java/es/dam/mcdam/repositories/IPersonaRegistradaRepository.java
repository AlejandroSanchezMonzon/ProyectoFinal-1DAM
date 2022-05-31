package es.dam.mcdam.repositories;

import es.dam.mcdam.models.PersonaRegistrada;

import java.sql.SQLException;
import java.util.Optional;


public interface IPersonaRegistradaRepository extends ICRUDObservable<PersonaRegistrada, String> {
    public PersonaRegistrada findByCorreo(String identificacion) throws SQLException;
}
