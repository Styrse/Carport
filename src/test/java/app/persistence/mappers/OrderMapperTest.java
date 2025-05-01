package app.persistence.mappers;

import app.entities.orders.Order;
import app.entities.users.Customer;
import app.exceptions.DatabaseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

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
                stmt.execute("DROP TABLE IF EXISTS test.order_status_history");
                stmt.execute("DROP TABLE IF EXISTS test.order_items");

                //Create tables as empty copies of the originals
                stmt.execute("CREATE TABLE test.orders AS (SELECT * from public.orders) WITH NO DATA;");
                stmt.execute("CREATE TABLE test.order_status_history AS (SELECT * from public.order_status_history) WITH NO DATA");
                stmt.execute("CREATE TABLE test.order_items AS (SELECT * from public.order_items) WITH NO DATA");

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
                stmt.execute("INSERT INTO test.orders (user_id, carport_id, building_material_id, staff_id, total_price) VALUES " +
                        "(1, NULL, 2, 3, 7999.95), " +
                        "(2, NULL, 3, 3, 10499.00), " +
                        "(3, 4, NULL, 2, 6599.50);");

                stmt.execute("INSERT INTO test.order_status_history (order_id, status, update_date) VALUES " +
                        "(1, 'Received', CURRENT_DATE), " +
                        "(1, 'Under Review', CURRENT_DATE + INTERVAL '1 day'), " +
                        "(2, 'Received', CURRENT_DATE);");

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
    void createOrder() throws DatabaseException {
        //Arrange
        Customer user = new Customer("John", "Doe", 12345678, "john@doe.com", "John123", 1);
        Order order = new Order(LocalDate.of(2025, 5, 1), "Shipped", user);

        //Act
        OrderMapper.createOrder(order);


        //Assert

    }

    @Test
    @DisplayName("GetAllOrders Test")
    void getAllOrders() throws DatabaseException {
        //Arrange

        //Act
        int actual = OrderMapper.getAllOrders().size();

        //Assert
        int expected = 3;
        assertEquals(expected, actual);

    }

    @Test
    @DisplayName("GetOrderByOrderId Test")
    void getOrderByOrderId() throws DatabaseException {
        //Arrange

        //Act
        String actual = OrderMapper.getOrderByOrderId(1).getOrderStatus();

        //Assert
        String expected = "Under Review";
        assertEquals(expected, actual);

    }

    @Test
    @DisplayName("GetOrdersByCustomerId Test")
    void getOrdersByCustomerId() {
        //Arrange

        //Act

        //Assert

    }

    @Test
    @DisplayName("UpdateOrder Test")
    void updateOrder() {
        //Arrange

        //Act

        //Assert

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