/**
 @author Información mostrada en la documentación.
 */

package es.dam.mcdam.repositories;

import es.dam.mcdam.models.PersonaRegistrada;

import java.sql.SQLException;
import java.util.Optional;


public interface IPersonaRegistradaRepository extends ICRUDObservable<PersonaRegistrada, String> {

    /**
     * Método que devuelve un usuario en base a un correo específico.
     * @param identificacion
     * @return
     * @throws SQLException
     */
    public PersonaRegistrada findByCorreo(String identificacion) throws SQLException;
}
