package app.persistence.mappers;

import app.exceptions.DatabaseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static app.Main.connectionPool;
import static org.junit.jupiter.api.Assertions.*;

class OrderMapperTest {

    @BeforeAll
    static void setupClass() {
        try (Connection connection = connectionPool.getConnection()) {
            try (Statement stmt = connection.createStatement()) {
                //Drop tables and sequences if they exist
                stmt.execute("DROP TABLE IF EXISTS test.orders;");
                stmt.execute("DROP SEQUENCE IF EXISTS test.orders_order_id_seq CASCADE;");

                //Create tables as empty copies of the originals
                stmt.execute("CREATE TABLE test.orders AS (SELECT * from public.orders) WITH NO DATA;");

                //Create sequences and attach to ID columns
                stmt.execute("CREATE SEQUENCE test.orders_order_id_seq;");
                stmt.execute("ALTER TABLE test.orders ALTER COLUMN order_id SET DEFAULT nextval('test.orders_order_id_seq');");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Database setup failed");
        }
    }

    @BeforeEach
    void setup() {
        try (Connection connection = connectionPool.getConnection()) {
            try (Statement stmt = connection.createStatement()) {
                //Clean the test table
                stmt.execute("TRUNCATE TABLE test.orders RESTART IDENTITY CASCADE;");

                //Insert 3 example orders
                stmt.execute("INSERT INTO test.orders (order_id, user_id, staff_id, product_id, order_date, payment_date, payment_status) VALUES " +
                        "(1, 1, 2, 101, '2024-01-10 10:30:00', '2024-01-12 14:00:00', 'paid'), " +
                        "(2, 2, 2, 102, '2024-02-05 09:15:00', null, 'pending'), " +
                        "(3, 3, 2, 103, '2024-03-20 16:45:00', '2024-03-22 10:00:00', 'paid');");

                //Reset sequences
                stmt.execute("SELECT setval('test.orders_order_id_seq', COALESCE((SELECT MAX(order_id) + 1 FROM test.orders), 1), false)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Database reset failed");
        }
    }

    @Test
    @DisplayName("CreateOrder Test")
    void createOrder() {
    }

    @Test
    @DisplayName("GetAllOrders Test")
    void getAllOrders() {
    }

    @Test
    @DisplayName("GetOrderByOrderId Test")
    void getOrderByOrderId() throws DatabaseException {
        //Arrange

        //Act
        String actual = OrderMapper.getOrderByOrderId(1).getPaymentStatus();

        //Assert
        String expected = "paid";
        assertEquals(expected, actual);

    }

    @Test
    @DisplayName("GetOrdersByCustomerId Test")
    void getOrdersByCustomerId() {
    }

    @Test
    @DisplayName("UpdateOrder Test")
    void updateOrder() {
    }

    @Test
    @DisplayName("DeleteOrder Test")
    void deleteOrder() throws DatabaseException {
        //Arrange

        //Act
        OrderMapper.deleteOrder(1);
        int actual = OrderMapper.getAllOrders().size();

        //Assert
        int expected = 2;
        assertEquals(expected, actual);
    }
}