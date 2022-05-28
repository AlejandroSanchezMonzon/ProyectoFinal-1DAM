package org.example.repositories;

import org.example.models.CodigoDescuento;
import org.junit.jupiter.api.*;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Código de descuento Test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CodigoDescuentoRepositoryTest {
    private final CodigoDescuentoRepository codigoDescuentoRepository = CodigoDescuentoRepository.getInstance();


    private final CodigoDescuento pTest1 = new CodigoDescuento("A111",30.00f);
    private final CodigoDescuento pTest1V2 = new CodigoDescuento("A111",60.00f);
    private final CodigoDescuento pTest2 = new CodigoDescuento("B222",40.00f);
    private final CodigoDescuento pTest3 = new CodigoDescuento("C333",50.00f);

    @BeforeAll
    void setUp() throws SQLException {
        codigoDescuentoRepository.deleteAll();
    }


    @Test
    void findAll() {
        try {
            var resVacioOptional = codigoDescuentoRepository.findAll();
            var resVacio = resVacioOptional.get();
            codigoDescuentoRepository.save(pTest1);
            codigoDescuentoRepository.save(pTest2);
            codigoDescuentoRepository.save(pTest3);
            var resLlenoOptional = codigoDescuentoRepository.findAll();
            var resLleno = resLlenoOptional.get();
            assertAll(
                    () -> assertFalse(resVacioOptional.isPresent()),
                    () -> assertTrue(resLlenoOptional.isPresent()),
                    () -> assertEquals(3, resLleno.size()),
                    () -> assertEquals(pTest1, resLleno.get(0)),
                    () -> assertEquals(pTest2, resLleno.get(1)),
                    () -> assertEquals(pTest3, resLleno.get(2))
            );
        }catch (Exception e){
            fail();
        }
    }

    @Test
    void findById() {
        try {
            codigoDescuentoRepository.save(pTest1);
            var resOptional = codigoDescuentoRepository.findById(pTest1.getCodigo());
            var res = resOptional.get();
            assertAll(
                    () -> assertTrue(resOptional.isPresent()),
                    () -> assertEquals(pTest1, res),
                    () -> assertEquals(pTest1.getCodigo(), res.getCodigo()),
                    () -> assertEquals(pTest1.getPorcentajeDescuento(), res.getPorcentajeDescuento())
            );
        }catch (Exception e){
            fail();
        }
    }

    @Test
    void save() {
        try {
            var res = codigoDescuentoRepository.save(pTest1);
            assertAll(
                    () -> assertEquals(pTest1, res),
                    () -> assertEquals(pTest1.getCodigo(), res.getCodigo()),
                    () -> assertEquals(pTest1.getPorcentajeDescuento(), res.getPorcentajeDescuento())
            );
        }catch (Exception e){
            fail();
        }
    }

    @Test
    void update() {
        try {
            var res = codigoDescuentoRepository.save(pTest1);
            var resUpd = codigoDescuentoRepository.update(pTest1V2);
            var resBusquedaOpt = codigoDescuentoRepository.findById(pTest1V2.getCodigo());
            var resBusqueda = resBusquedaOpt.get();
            assertAll(
                    () -> assertTrue(resBusquedaOpt.isPresent()),
                    () -> assertEquals(pTest1.getCodigo(), res.getCodigo()),
                    () -> assertEquals(pTest1.getPorcentajeDescuento(), res.getPorcentajeDescuento())
            );
        }catch (Exception e){
            fail();
        }
    }

    @Test
    void delete() {
        try {
            codigoDescuentoRepository.save(pTest1);
            var res = codigoDescuentoRepository.delete(pTest1);

            assertAll(
                    () -> assertEquals(pTest1, res),
                    () -> assertEquals(pTest1.getCodigo(), res.getCodigo()),
                    () -> assertEquals(pTest1.getPorcentajeDescuento(), res.getPorcentajeDescuento())
            );
        }catch (Exception e){
            fail();
        }
    }

    @Test
    void deleteAll() {
        try {
            codigoDescuentoRepository.save(pTest1);
            codigoDescuentoRepository.save(pTest2);
            codigoDescuentoRepository.save(pTest3);
            codigoDescuentoRepository.deleteAll();

            assertAll(
                    () -> assertTrue(codigoDescuentoRepository.findAll().isEmpty()),
                    () -> assertTrue(codigoDescuentoRepository.findById(pTest1.getCodigo()).isEmpty()),
                    () -> assertTrue(codigoDescuentoRepository.findById(pTest2.getCodigo()).isEmpty()),
                    () -> assertTrue(codigoDescuentoRepository.findById(pTest3.getCodigo()).isEmpty())
            );
        }catch (Exception e){
            fail();
        }
    }
}