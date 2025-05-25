package app.persistence.mappers;

import app.entities.orders.Order;
import app.entities.orders.OrderItem;
import app.entities.products.carport.Carport;
import app.entities.products.carport.carportTestFactory.TestCarportFactory;
import app.entities.products.carport.carportTestFactory.TestPlankFactory;
import app.entities.products.materials.planks.Post;
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

    @BeforeEach
    void setup() {
        try (Connection connection = connectionPool.getConnection()) {
            try (Statement stmt = connection.createStatement()) {
                //
                stmt.execute("DROP TABLE IF EXISTS test.order_item_material;");
                stmt.execute("DROP TABLE IF EXISTS test.order_item_carport;");
                stmt.execute("DROP TABLE IF EXISTS test.order_items;");
                stmt.execute("DROP TABLE IF EXISTS test.order_status_history;");
                stmt.execute("DROP TABLE IF EXISTS test.orders;");
                stmt.execute("DROP TABLE IF EXISTS test.carports;");
                stmt.execute("DROP TABLE IF EXISTS test.users;");
                stmt.execute("DROP TABLE IF EXISTS test.materials;");
                stmt.execute("DROP TABLE IF EXISTS test.price_history;");
                stmt.execute("DROP TABLE IF EXISTS test.material_lengths;");
                stmt.execute("DROP TABLE IF EXISTS test.predefined_lengths;");
                stmt.execute("DROP SEQUENCE IF EXISTS test.orders_order_id_seq CASCADE;");
                stmt.execute("DROP SEQUENCE IF EXISTS test.order_items_order_item_id_seq CASCADE;");
                stmt.execute("DROP SEQUENCE IF EXISTS test.carports_carport_id_seq CASCADE;");
                stmt.execute("DROP SEQUENCE IF EXISTS test.users_user_id_seq CASCADE;");
                stmt.execute("DROP SEQUENCE IF EXISTS test.predefined_lengths_predefined_length_id_seq CASCADE;");

                // Create test tables from the public schema
                stmt.execute("CREATE TABLE test.orders AS TABLE public.orders WITH NO DATA;");
                stmt.execute("CREATE TABLE test.order_status_history AS TABLE public.order_status_history WITH NO DATA;");
                stmt.execute("CREATE TABLE test.order_items AS TABLE public.order_items WITH NO DATA;");
                stmt.execute("CREATE TABLE test.order_item_carport AS TABLE public.order_item_carport WITH NO DATA;");
                stmt.execute("CREATE TABLE test.order_item_material AS TABLE public.order_item_material WITH NO DATA;");
                stmt.execute("CREATE TABLE test.carports AS TABLE public.carports WITH NO DATA;");
                stmt.execute("CREATE TABLE test.users AS TABLE public.users WITH NO DATA;");
                stmt.execute("CREATE TABLE test.materials AS TABLE public.materials WITH NO DATA;");
                stmt.execute("CREATE TABLE test.price_history AS TABLE public.price_history WITH NO DATA;");
                stmt.execute("CREATE TABLE test.predefined_lengths AS TABLE public.predefined_lengths WITH NO DATA;");
                stmt.execute("CREATE TABLE test.material_lengths AS TABLE public.material_lengths WITH NO DATA;");

                // Recreate and attach sequences
                stmt.execute("CREATE SEQUENCE test.orders_order_id_seq;");
                stmt.execute("ALTER TABLE test.orders ALTER COLUMN order_id SET DEFAULT nextval('test.orders_order_id_seq');");

                stmt.execute("CREATE SEQUENCE test.order_items_order_item_id_seq;");
                stmt.execute("ALTER TABLE test.order_items ALTER COLUMN order_item_id SET DEFAULT nextval('test.order_items_order_item_id_seq');");

                stmt.execute("CREATE SEQUENCE test.carports_carport_id_seq;");
                stmt.execute("ALTER TABLE test.carports ALTER COLUMN carport_id SET DEFAULT nextval('test.carports_carport_id_seq');");

                stmt.execute("CREATE SEQUENCE test.users_user_id_seq;");
                stmt.execute("ALTER TABLE test.users ALTER COLUMN user_id SET DEFAULT nextval('test.users_user_id_seq');");

                stmt.execute("CREATE SEQUENCE test.predefined_lengths_predefined_length_id_seq;");
                stmt.execute("ALTER TABLE test.predefined_lengths ALTER COLUMN predefined_length_id SET DEFAULT nextval('test.predefined_lengths_predefined_length_id_seq');");

                // Truncate all test tables and reset identities
                stmt.execute("TRUNCATE TABLE " +
                        "test.order_item_material, " +
                        "test.order_item_carport, " +
                        "test.order_items, " +
                        "test.order_status_history, " +
                        "test.orders, " +
                        "test.carports, " +
                        "test.users, " +
                        "test.materials, " +
                        "test.price_history RESTART IDENTITY CASCADE;");

                // Insert test carport (used for order_item_id = 3)
                stmt.execute("INSERT INTO test.carports (width, length, height, roof_type, roof_angle, " +
                        "post_material_id, beam_material_id, rafter_material_id, fascia_material_id, roof_cover_material_id, total_price) VALUES " +
                        "(600, 780, 250, 'flat', 0, 1, 2, 3, 4, 5, 6599.50);");

                // Insert test users (customers and staff)
                stmt.execute("INSERT INTO test.users (user_id, firstname, lastname, phone_number, email, password, role_id) VALUES " +
                        "(1, 'Alice', 'Anderson', 12345678, 'alice@example.com', 'pass1', 1), " +
                        "(2, 'Bob', 'Brown', 87654321, 'bob@example.com', 'pass2', 1), " +
                        "(3, 'Charlie', 'Clark', 11223344, 'charlie@example.com', 'pass3', 2);");

                // Insert test materials
                stmt.execute("INSERT INTO test.materials (material_id, name, description, unit, width, height, material_type, buckling_capacity, post_gap, length_overlap, side_overlap, gap_rafters, is_active) VALUES " +
                        "(1, 'post', 'Pressure-treated post', 'cm', 10, 10, 'Post', 5, 0, 0, 0, 0, true), " +
                        "(2, 'beam', 'Steel I-beam', 'cm', 15, 20, 'Beam', 0, 340, 0, 0, 0, true), " +
                        "(3, 'fascia', 'Aluminum fascia board', 'cm', 5, 15, 'Fascia', 0, 0, 0, 0, 0, true), " +
                        "(4, 'rafter', 'Wooden rafter plank', 'cm', 25, 55, 'Rafter', 0, 0, 0, 0, 0, true)," +
                        "(5, 'roofCover', 'Plastic roof', 'm2', 100, 10, 'RoofCover', 0, 0, 20, 10, 55, true);");

                // Insert price history for materials (active prices)
                stmt.execute("INSERT INTO test.price_history (material_id, cost_price, sales_price, valid_from, valid_to) VALUES " +
                        "(1, 10.0, 19.45, CURRENT_DATE - INTERVAL '10 days', CURRENT_DATE + INTERVAL '10 days')," +
                        "(2, 20.0, 49.95, CURRENT_DATE - INTERVAL '10 days', CURRENT_DATE + INTERVAL '10 days')," +
                        "(3, 25.0, 69.95, CURRENT_DATE - INTERVAL '10 days', CURRENT_DATE + INTERVAL '10 days')," +
                        "(4, 19.0, 109.95, CURRENT_DATE - INTERVAL '10 days', CURRENT_DATE + INTERVAL '10 days')," +
                        "(5, 30.0, 89.95, CURRENT_DATE - INTERVAL '10 days', CURRENT_DATE + INTERVAL '10 days');");

                // Insert test orders
                stmt.execute("INSERT INTO test.orders (user_id, staff_id, total_price) VALUES " +
                        "(1, 3, 7999.95), " +
                        "(2, 3, 10499.00), " +
                        "(2, 3, 6599.50);");

                // Insert corresponding order items
                stmt.execute("INSERT INTO test.order_items (order_id, item_type, quantity) VALUES " +
                        "(1, 'material', 1), " +
                        "(2, 'material', 1), " +
                        "(3, 'carport', 1);");

                // Link materials and carport to order items
                stmt.execute("INSERT INTO test.order_item_material (order_item_id, material_id) VALUES " +
                        "(1, 2), " +
                        "(2, 3);");

                stmt.execute("INSERT INTO test.order_item_carport (order_item_id, carport_id) VALUES " +
                        "(3, 1);"); // carport_id = 1 (auto-increment starts at 1)

                // Insert order status history
                stmt.execute("INSERT INTO test.order_status_history (order_id, status, update_date) VALUES " +
                        "(1, 'received', CURRENT_DATE-5), " +
                        "(1, 'under review', CURRENT_DATE + INTERVAL '1 day'), " +
                        "(2, 'received', CURRENT_DATE)," +
                        "(3, 'received', CURRENT_DATE);");

                // Insert predefined lengths
                stmt.execute("INSERT INTO test.predefined_lengths (predefined_length_id, length) VALUES " +
                        "(1, 300), " +
                        "(2, 400), " +
                        "(3, 500), " +
                        "(4, 600), " +
                        "(5, 700);");

// Link materials to lengths
                stmt.execute("INSERT INTO test.material_lengths (material_id, predefined_length_id) VALUES " +
                        "(1, 1), " +
                        "(1, 2)," +
                        "(1, 3)," +
                        "(2, 2), " +
                        "(2, 3), " +
                        "(3, 3), " +
                        "(3, 4), " +
                        "(4, 4), " +
                        "(4, 5), " +
                        "(5, 3), " +
                        "(5, 4), " +
                        "(5, 5);");

                // Reset sequences
                stmt.execute("SELECT setval('test.orders_order_id_seq', COALESCE((SELECT MAX(order_id) + 1 FROM test.orders), 1), false)");
                stmt.execute("SELECT setval('test.order_items_order_item_id_seq', COALESCE((SELECT MAX(order_item_id) + 1 FROM test.order_items), 1), false)");
                stmt.execute("SELECT setval('test.carports_carport_id_seq', COALESCE((SELECT MAX(carport_id) + 1 FROM test.carports), 1), false)");
                stmt.execute("SELECT setval('test.users_user_id_seq', COALESCE((SELECT MAX(user_id) + 1 FROM test.users), 1), false)");

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
        Customer user = new Customer(10,"John", "Doe", "12345678", "john@doe.com", "John123", 1);
        Order order = new Order(LocalDate.of(2025, 5, 1), "Shipped", user);
        Carport carport = TestCarportFactory.createCarportWidthLength(630, 500);
        Post post = TestPlankFactory.createStandardPost();
        order.getOrderItems().add(new OrderItem(carport, 1));
        order.getOrderItems().add(new OrderItem(post, 12));

        //Act
        OrderMapper.createOrder(order);
        int actual  = order.getOrderId();

        //Assert
        int expected = 4;
        assertEquals(expected, actual);

    }

    @Test
    @DisplayName("GetOrderById Test")
    void testGetOrderByOrderId() throws DatabaseException {
        //Arrange

        //Act
        String actual = OrderMapper.getOrderByOrderId(1).getCustomer().getFirstName();

        //Assert
        String expected = "Alice";
        assertEquals(expected, actual);
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
        String expected = "under review";
        assertEquals(expected, actual);

    }

    @Test
    @DisplayName("GetOrdersByCustomerId Test")
    void getOrdersByCustomerId() throws DatabaseException {
        //Arrange

        //Act
        int actual = OrderMapper.getOrdersByCustomerId(2).size();

        //Assert
        int expected = 2;
        assertEquals(expected, actual);

    }

    @Test
    @DisplayName("UpdateOrder Test")
    void updateOrder() throws DatabaseException {
        //Arrange
        Order order = OrderMapper.getOrderByOrderId(3);
        Carport carport = ((Carport) order.getOrderItems().get(0).getProduct());
        carport.setWidth(300);

        //Act
        OrderMapper.updateOrder(order);
        int actual = ((Carport) OrderMapper.getOrderByOrderId(order.getOrderId()).getOrderItems().get(0).getProduct()).getWidth();

        //Assert
        int expected = 300;
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("DeleteOrder Test")
    void deleteOrder() throws DatabaseException {
        //Arrange
        OrderMapper.deleteOrder(1);

        //Act
        int actual = OrderMapper.getAllOrders().size();

        //Assert
        int expected = 2;
        assertEquals(expected, actual);
    }
}