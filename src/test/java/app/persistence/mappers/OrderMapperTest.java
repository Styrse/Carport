package app.persistence.mappers;

import app.entities.orders.Order;
import app.entities.orders.OrderItem;
import app.entities.products.carport.Carport;
import app.entities.products.carport.carportTestFactory.TestCarportFactory;
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
                // Drop test tables and sequences if they exist
                stmt.execute("DROP TABLE IF EXISTS test.order_item_material;");
                stmt.execute("DROP TABLE IF EXISTS test.order_item_carport;");
                stmt.execute("DROP TABLE IF EXISTS test.order_items;");
                stmt.execute("DROP TABLE IF EXISTS test.order_status_history;");
                stmt.execute("DROP TABLE IF EXISTS test.orders;");
                stmt.execute("DROP TABLE IF EXISTS test.carports;");
                stmt.execute("DROP SEQUENCE IF EXISTS test.orders_order_id_seq CASCADE;");
                stmt.execute("DROP SEQUENCE IF EXISTS test.order_items_order_item_id_seq CASCADE;");
                stmt.execute("DROP SEQUENCE IF EXISTS test.carports_carport_id_seq CASCADE;");

                // Create test tables from the public schema
                stmt.execute("CREATE TABLE test.orders AS TABLE public.orders WITH NO DATA;");
                stmt.execute("CREATE TABLE test.order_status_history AS TABLE public.order_status_history WITH NO DATA;");
                stmt.execute("CREATE TABLE test.order_items AS TABLE public.order_items WITH NO DATA;");
                stmt.execute("CREATE TABLE test.order_item_carport AS TABLE public.order_item_carport WITH NO DATA;");
                stmt.execute("CREATE TABLE test.order_item_material AS TABLE public.order_item_material WITH NO DATA;");
                stmt.execute("CREATE TABLE test.carports AS TABLE public.carports WITH NO DATA;");

                // Recreate and attach sequences
                stmt.execute("CREATE SEQUENCE test.orders_order_id_seq;");
                stmt.execute("ALTER TABLE test.orders ALTER COLUMN order_id SET DEFAULT nextval('test.orders_order_id_seq');");

                stmt.execute("CREATE SEQUENCE test.order_items_order_item_id_seq;");
                stmt.execute("ALTER TABLE test.order_items ALTER COLUMN order_item_id SET DEFAULT nextval('test.order_items_order_item_id_seq');");

                stmt.execute("CREATE SEQUENCE test.carports_carport_id_seq;");
                stmt.execute("ALTER TABLE test.carports ALTER COLUMN carport_id SET DEFAULT nextval('test.carports_carport_id_seq');");
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
                // Truncate all test tables and reset identities
                stmt.execute("TRUNCATE TABLE test.order_item_material, test.order_item_carport, test.order_items, test.order_status_history, test.orders, test.carports RESTART IDENTITY CASCADE;");

                // Insert test carport (used for order_item_id = 3)
                stmt.execute("INSERT INTO test.carports (width, length, height, roof_type, roof_angle, " +
                        "post_building_material_id, beam_building_material_id, rafter_building_material_id, fascia_building_material_id, total_price) VALUES " +
                        "(600, 780, 250, 'flat', 0, 1, 2, 3, 4, 6599.50);");

                // Insert test orders
                stmt.execute("INSERT INTO test.orders (user_id, staff_id, total_price) VALUES " +
                        "(1, 3, 7999.95), " +
                        "(2, 3, 10499.00), " +
                        "(3, 2, 6599.50);");

                // Insert corresponding order items
                stmt.execute("INSERT INTO test.order_items (order_id, item_type, quantity) VALUES " +
                        "(1, 'material', 1), " +
                        "(2, 'material', 1), " +
                        "(3, 'carport', 1);");

                // Link to item details
                stmt.execute("INSERT INTO test.order_item_material (order_item_id, building_material_id) VALUES " +
                        "(1, 2), " +
                        "(2, 3);");

                stmt.execute("INSERT INTO test.order_item_carport (order_item_id, carport_id) VALUES " +
                        "(3, 1);"); // carport_id is 1 because carports is empty before insert

                // Insert order status history
                stmt.execute("INSERT INTO test.order_status_history (order_id, status, update_date) VALUES " +
                        "(1, 'Received', CURRENT_DATE), " +
                        "(1, 'Under Review', CURRENT_DATE + INTERVAL '1 day'), " +
                        "(2, 'Received', CURRENT_DATE);");

                // Reset sequences
                stmt.execute("SELECT setval('test.orders_order_id_seq', COALESCE((SELECT MAX(order_id) + 1 FROM test.orders), 1), false)");
                stmt.execute("SELECT setval('test.order_items_order_item_id_seq', COALESCE((SELECT MAX(order_item_id) + 1 FROM test.order_items), 1), false)");
                stmt.execute("SELECT setval('test.carports_carport_id_seq', COALESCE((SELECT MAX(carport_id) + 1 FROM test.carports), 1), false)");

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
        Customer user = new Customer(10,"John", "Doe", 12345678, "john@doe.com", "John123", 1);
        Order order = new Order(LocalDate.of(2025, 5, 1), "Shipped", user);
        Carport carport = TestCarportFactory.createCarport(630, 500);
        order.getOrderItems().add(new OrderItem(carport, 1));

        //Act
        OrderMapper.createOrder(order);

        //Assert

    }

    @Test
    @DisplayName("GetAllOrders Test")
    void getAllOrders() throws DatabaseException {
        //Arrange

        //Act

        //Assert

    }

    @Test
    @DisplayName("GetOrderByOrderId Test")
    void getOrderByOrderId() throws DatabaseException {
        //Arrange

        //Act

        //Assert
        String expected = "Under Review";

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

        //Assert
    }
}