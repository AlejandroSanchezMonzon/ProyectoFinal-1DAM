package es.dam.repositories;

import es.dam.mcdam.managers.DataBaseManager;
import es.dam.mcdam.models.*;
import es.dam.mcdam.repositories.PedidoRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PedidoRepositoryTest {
    private static final DataBaseManager db = DataBaseManager.getInstance();
    private static final PedidoRepository pedidoRepository = PedidoRepository.getInstance(db);

    private final List<LineaPedido> compra1 = List.of(
            new LineaPedido("hamburguesa", 2, 1.00f, 2.00f),
            new LineaPedido("bebida", 1, 2.50f, 2.50f)
    );
    private final List<LineaPedido> compra1V2 = List.of(
            new LineaPedido("hamburguesa", 1, 1.00f, 1.00f),
            new LineaPedido("bebida", 1, 2.50f, 2.50f)
    );
    private final List<LineaPedido> compra2 = List.of(
            new LineaPedido("nuggets", 1, 1.00f, 1.00f),
            new LineaPedido("patatas", 1, 2.00f, 2.00f),
            new LineaPedido("bebida", 1, 2.50f, 2.50f)
    );

    private final PersonaRegistrada cliente1 = new PersonaRegistrada(
            UUID.randomUUID().toString(), "Antonio", "antonio@gmail.com", "antonio1234", Tipo.USER
    );
    private final PersonaRegistrada cliente2 = new PersonaRegistrada(
            UUID.randomUUID().toString(), "Maria", "maria@gmail.com", "maria1234", Tipo.USER
    );

    private final Pedido pTest1 = new Pedido(compra1, cliente1,"CRÉDITO");
    private final Pedido pTest1V2 = new Pedido(compra1V2, cliente1,"CRÉDITO");
    private final Pedido pTest2 = new Pedido(compra2, cliente1,"METALICO");
    private final Pedido pTest3 = new Pedido(compra1, cliente2,"CRÉDITO");


    @BeforeAll
    static void setUp() throws SQLException {
        pedidoRepository.deleteAll();
    }

    @Test
    void findAll() {
        try {
            pedidoRepository.save(pTest1);
            pedidoRepository.save(pTest2);
            pedidoRepository.save(pTest3);
            var resLleno = pedidoRepository.findAll();
            assertAll(
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
            pedidoRepository.save(pTest1);
            var resOptional = pedidoRepository.findById(pTest1.getUuid());
            var res = resOptional.get();
            assertAll(
                    () -> assertTrue(resOptional.isPresent()),
                    () -> assertEquals(pTest1, res),
                    () -> assertEquals(pTest1.getUuid(), res.getUuid()),
                    () -> assertEquals(pTest1.getTotal(), res.getTotal()),
                    () -> assertEquals(pTest1.getMetodoPago(), res.getMetodoPago()),
                    () -> assertEquals(pTest1.getCompra(), res.getCompra()),
                    () -> assertEquals(pTest1.getCliente(), res.getCliente())
            );
        }catch (Exception e){
            fail();
        }
    }

    @Test
    void save() {
        try {
            var res = pedidoRepository.save(pTest1);
            assertAll(
                    () -> assertEquals(pTest1, res),
                    () -> assertEquals(pTest1.getUuid(), res.getUuid()),
                    () -> assertEquals(pTest1.getTotal(), res.getTotal()),
                    () -> assertEquals(pTest1.getMetodoPago(), res.getMetodoPago()),
                    () -> assertEquals(pTest1.getCompra(), res.getCompra()),
                    () -> assertEquals(pTest1.getCliente(), res.getCliente())
            );
        }catch (Exception e){
            fail();
        }
    }

    @Test
    void update() {
        try {
            var res = pedidoRepository.save(pTest1);
            var resUpd = pedidoRepository.update(pTest1V2);
            var resBusquedaOpt = pedidoRepository.findById(pTest1V2.getUuid());
            var resBusqueda = resBusquedaOpt.get();
            assertAll(
                    () -> assertTrue(resBusquedaOpt.isPresent()),
                    () -> assertEquals(pTest1.getUuid(), res.getUuid()),
                    () -> assertEquals(pTest1.getTotal(), res.getTotal()),
                    () -> assertEquals(pTest1.getMetodoPago(), res.getMetodoPago()),
                    () -> assertEquals(pTest1.getCompra(), res.getCompra()),
                    () -> assertEquals(pTest1.getCliente(), res.getCliente())
            );
        }catch (Exception e){
            fail();
        }
    }

    @Test
    void delete() {
        try {
            pedidoRepository.save(pTest1);
            var res = pedidoRepository.delete(pTest1);

            assertAll(
                    () -> assertEquals(pTest1, res),
                    () -> assertEquals(pTest1.getUuid(), res.getUuid()),
                    () -> assertEquals(pTest1.getTotal(), res.getTotal()),
                    () -> assertEquals(pTest1.getMetodoPago(), res.getMetodoPago()),
                    () -> assertEquals(pTest1.getCompra(), res.getCompra()),
                    () -> assertEquals(pTest1.getCliente(), res.getCliente())
            );
        }catch (Exception e){
            fail();
        }
    }

    @Test
    void deleteAll() {
        try {
            pedidoRepository.save(pTest1);
            pedidoRepository.save(pTest2);
            pedidoRepository.save(pTest3);
            pedidoRepository.deleteAll();

            assertAll(
                    () -> assertTrue(pedidoRepository.findAll().isEmpty()),
                    () -> assertTrue(pedidoRepository.findById(pTest1.getUuid()).isEmpty()),
                    () -> assertTrue(pedidoRepository.findById(pTest2.getUuid()).isEmpty()),
                    () -> assertTrue(pedidoRepository.findById(pTest3.getUuid()).isEmpty())
            );
        }catch (Exception e){
            fail();
        }
    }
}