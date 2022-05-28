package es.dam.repositories;

import es.dam.mcdam.models.PersonaRegistrada;
import es.dam.mcdam.models.Tipo;
import es.dam.mcdam.repositories.PersonaRegistradaRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PersonaRegistradaRepositoryTest {
    private static final PersonaRegistradaRepository personaRepository = PersonaRegistradaRepository.getInstance();

    private final PersonaRegistrada pTest1 = new PersonaRegistrada(
            UUID.randomUUID().toString(), "Antonio", "antonio@gmail.com", "antonio1234", Tipo.USER
    );
    private final PersonaRegistrada pTest1V2 = new PersonaRegistrada(
            UUID.randomUUID().toString(), "Antonio", "antonio@gmail.com", "antonioContraseñaNueva", Tipo.USER
    );
    private final PersonaRegistrada pTest2 = new PersonaRegistrada(
            UUID.randomUUID().toString(), "Maria", "maria@gmail.com", "maria1234", Tipo.ADMIN
    );
    private final PersonaRegistrada pTest3 = new PersonaRegistrada(
        UUID.randomUUID().toString(), "Paola", "paola@gmail.com", "paola1234", Tipo.USER
    );


    @BeforeAll
    static void setUp() throws SQLException {
        personaRepository.deleteAll();
    }


    @Test
    void findAll() throws SQLException {
        try {
            var resVacio = personaRepository.findAll();
            personaRepository.save(pTest1);
            personaRepository.save(pTest2);
            personaRepository.save(pTest3);
            var resLleno = personaRepository.findAll();
            assertAll(
                    () -> assertEquals(3, resLleno.size()),
                    () -> assertEquals(pTest1, resLleno.get(0)),
                    () -> assertEquals(pTest2, resLleno.get(1)),
                    () -> assertEquals(pTest3, resLleno.get(2))
            );
        }catch (Exception e){
            throw new SQLException(e.getMessage());
        }
    }

    @Test
    void findById() throws SQLException {
        try {
            personaRepository.save(pTest1);
            var resOptional = personaRepository.findById(pTest1.getUuid());
            var res = resOptional.get();
            assertAll(
                    () -> assertTrue(resOptional.isPresent()),
                    () -> assertEquals(pTest1, res),
                    () -> assertEquals(pTest1.getUuid(), res.getUuid()),
                    () -> assertEquals(pTest1.getNombre(), res.getNombre()),
                    () -> assertEquals(pTest1.getCorreo(), res.getCorreo()),
                    () -> assertEquals(pTest1.getContraseña(), res.getContraseña()),
                    () -> assertEquals(pTest1.getTipo(), res.getTipo())

            );
        }catch (Exception e){
            throw new SQLException(e.getMessage());
        }
    }

    @Test
    void save() throws SQLException {
        try {
            var res = personaRepository.save(pTest1);
            assertAll(
                    () -> assertEquals(pTest1, res),
                    () -> assertEquals(pTest1.getUuid(), res.getUuid()),
                    () -> assertEquals(pTest1.getNombre(), res.getNombre()),
                    () -> assertEquals(pTest1.getCorreo(), res.getCorreo()),
                    () -> assertEquals(pTest1.getContraseña(), res.getContraseña()),
                    () -> assertEquals(pTest1.getTipo(), res.getTipo())
            );
        }catch (Exception e){
            throw new SQLException(e.getMessage());
        }
    }

    @Test
    void update() throws SQLException {
        try {
            var res = personaRepository.save(pTest1);
            var resUpd = personaRepository.update(pTest1V2);
            var resBusquedaOpt = personaRepository.findById(pTest1V2.getUuid());
            var resBusqueda = resBusquedaOpt.get();
            assertAll(
                    () -> assertTrue(resBusquedaOpt.isPresent()),
                    () -> assertEquals(pTest1.getUuid(), res.getUuid()),
                    () -> assertEquals(pTest1.getNombre(), res.getNombre()),
                    () -> assertEquals(pTest1.getCorreo(), res.getCorreo()),
                    () -> assertEquals(pTest1.getContraseña(), res.getContraseña()),
                    () -> assertEquals(pTest1.getTipo(), res.getTipo())
            );
        }catch (Exception e){
            throw new SQLException(e.getMessage());
        }
    }

    @Test
    void delete() throws SQLException {
        try {
            personaRepository.save(pTest1);
            var res = personaRepository.delete(pTest1);

            assertAll(
                    () -> assertEquals(pTest1, res),
                    () -> assertEquals(pTest1.getUuid(), res.getUuid()),
                    () -> assertEquals(pTest1.getNombre(), res.getNombre()),
                    () -> assertEquals(pTest1.getCorreo(), res.getCorreo()),
                    () -> assertEquals(pTest1.getContraseña(), res.getContraseña()),
                    () -> assertEquals(pTest1.getTipo(), res.getTipo())
            );
        }catch (Exception e){
            throw new SQLException(e.getMessage());
        }
    }
}