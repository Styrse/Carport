package app.persistence.mappers;

import app.entities.orders.Order;
import app.entities.orders.OrderItem;
import app.entities.products.carport.Carport;
import app.entities.products.materials.Material;
import app.entities.users.Customer;
import app.entities.users.Staff;
import app.entities.users.StaffManager;
import app.entities.users.User;
import app.exceptions.DatabaseException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static app.Main.connectionPool;

/**
 * {@code OrderMapper} handles all database operations related to the Order entity.
 * <p>
 * Motivation:
 * <ul>
 *   <li><b>Reusability:</b> Exposes standard CRUD operations for orders in one central place.</li>
 *   <li><b>Security:</b> Uses prepared statements to avoid SQL injection.</li>
 * </ul>
 * <p>
 * Connection is passed on to helper Methods to reuse the same connection instead of opening a new for each Method.
 * <p>
 * Used a transaction when creating order to make sure everything gets commited otherwise it we be rolled back
 */
public class OrderMapper {

    // Create a new order and return the generated order_id
    public static void createOrder(Order order) throws DatabaseException {
        String sql = "INSERT INTO orders (user_id, staff_id, total_price) VALUES (?, ?, ?) RETURNING order_id";

        try (Connection connection = connectionPool.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement ps = connection.prepareStatement(sql)) {

                ps.setInt(1, order.getCustomer().getUserId());
                if (order.getStaff() != null) {
                    ps.setInt(2, order.getStaff().getUserId());
                } else {
                    ps.setNull(2, Types.INTEGER);
                }
                ps.setFloat(3, order.getTotalPrice());

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        int orderId = rs.getInt(1);
                        order.setOrderId(orderId);

                        insertOrderStatus(connection, orderId);

                        //Insert the order items
                        for (OrderItem item : order.getOrderItems()) {
                            insertOrderItem(connection, orderId, item);
                        }

                        connection.commit();
                    } else {
                        throw new DatabaseException("Failed to retrieve generated order ID");
                    }
                }
            } catch (SQLException e) {
                connection.rollback();
                throw new DatabaseException(e, "Error creating order");
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Database connection error");
        }
    }

    private static void insertOrderStatus(Connection connection, int orderId) throws SQLException {
        String sql = "INSERT INTO order_status_history (order_id, status) VALUES (?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ps.setString(2, "Forespørgsel");
            ps.executeUpdate();
        }
    }

    private static void insertOrderItem(Connection connection, int orderId, OrderItem item) throws SQLException {
        String itemSql = "INSERT INTO order_items (order_id, item_type, quantity) VALUES (?, ?, ?) RETURNING order_item_id";

        try (PreparedStatement ps = connection.prepareStatement(itemSql)) {
            ps.setInt(1, orderId);
            ps.setString(2, item.getProduct().getItemType());
            ps.setInt(3, item.getQuantity());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int orderItemId = rs.getInt(1);

                    switch (item.getProduct().getItemType()) {
                        case "carport" -> insertOrderItemCarport(connection, orderItemId, item);
                        case "material" ->
                                insertOrderItemMaterial(connection, orderItemId, item.getProduct().getItemId());
                        default -> throw new SQLException("Unknown item type: " + item.getProduct().getItemType());
                    }
                }
            }
        }
    }

    private static void insertOrderItemCarport(Connection connection, int orderItemId, OrderItem orderItem) throws SQLException {
        CarportMapper.createCarport((Carport) orderItem.getProduct());

        String sql = "INSERT INTO order_item_carport (order_item_id, carport_id) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, orderItemId);
            ps.setInt(2, orderItem.getProduct().getItemId());
            ps.executeUpdate();
        }
    }

    private static void insertOrderItemMaterial(Connection connection, int orderItemId, int materialId) throws SQLException {
        String sql = "INSERT INTO order_item_material (order_item_id, material_id) VALUES (?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, orderItemId);
            ps.setInt(2, materialId);
            ps.executeUpdate();
        }
    }

    // Retrieves order details for a specific order ID, including:
    // - The customer's email (from the user who placed the order)
    // - The staff member's email, if assigned
    // - The date when the order status was set to 'received'
    // - The latest status of the order based on the most recent update
    // Uses LEFT JOINs to allow for missing data (e.g., unassigned staff or missing 'received' status)
    public static Order getOrderByOrderId(int orderId) throws DatabaseException {
        String sql = "SELECT o.order_id, o.total_price, u.email AS user_email, s.email AS staff_email, " +
                "received.update_date AS order_date, latest.status AS order_status " +
                "FROM orders o JOIN users u ON o.user_id = u.user_id " +
                "LEFT JOIN users s ON o.staff_id = s.user_id " +
                "LEFT JOIN order_status_history received " +
                "  ON o.order_id = received.order_id AND received.status = 'Forespørgsel' " +
                "LEFT JOIN order_status_history latest " +
                "  ON o.order_id = latest.order_id " +
                "  AND latest.update_date = ( " +
                "    SELECT MAX(update_date) " +
                "    FROM order_status_history " +
                "    WHERE order_id = o.order_id " +
                "  ) " +
                "WHERE o.order_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, orderId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    Order order = mapOrder(rs);

                    List<OrderItem> items = getOrderItemsForOrder(connection, orderId);
                    order.setOrderItems(items);

                    return order;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Error fetching order by ID");
        }
        return null;
    }

    private static List<OrderItem> getOrderItemsForOrder(Connection connection, int orderId) throws SQLException {
        List<OrderItem> items = new ArrayList<>();

        String sql = "SELECT * FROM order_items WHERE order_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, orderId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int orderItemId = rs.getInt("order_item_id");
                    String type = rs.getString("item_type");
                    int quantity = rs.getInt("quantity");

                    switch (type) {
                        case "carport" -> items.add(getCarportItem(connection, orderItemId, quantity));
                        case "material" -> items.add(getMaterialItem(connection, orderItemId, quantity));
                    }
                }
            } catch (DatabaseException e) {
                throw new RuntimeException(e);
            }
        }
        return items;
    }

    private static OrderItem getCarportItem(Connection connection, int orderItemId, int quantity) throws SQLException {
        String sql = "SELECT * FROM order_item_carport JOIN carports " +
                "USING (carport_id) WHERE order_item_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, orderItemId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int carportId = rs.getInt("carport_id");
                    Carport carport = CarportMapper.mapCarport(rs);
                    carport.setItemId(carportId);
                    return new OrderItem(carport, quantity);
                }
            } catch (DatabaseException e) {
                throw new RuntimeException(e);
            }
        }
        throw new SQLException("Carport not found for order_item_id=" + orderItemId);
    }

    private static OrderItem getMaterialItem(Connection connection, int orderItemId, int quantity) throws SQLException, DatabaseException {
        String sql = "SELECT m.*, ph.cost_price, ph.sales_price FROM order_item_material oim " +
                "JOIN materials m ON oim.material_id = m.material_id JOIN price_history ph " +
                "ON ph.material_id = m.material_id WHERE oim.order_item_id = ? " +
                "ORDER BY ph.valid_from DESC LIMIT 1";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, orderItemId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int materialId = rs.getInt("material_id");
                    Material material = MaterialMapper.mapMaterial(rs);
                    material.setItemId(materialId);
                    return new OrderItem(material, quantity);
                } else {
                    throw new DatabaseException("No material found for order_item_id: " + orderItemId);
                }
            }
        }
    }

    //Read all orders
    public static List<Order> getAllOrders() throws DatabaseException {
        String sql = "SELECT order_id FROM orders";
        List<Order> allOrders = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int orderId = rs.getInt("order_id");
                    allOrders.add(getOrderByOrderId(orderId));
                }
                return allOrders;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Read all orders by customerId
    public static List<Order> getOrdersByCustomerId(int customerId) throws DatabaseException {
        List<Order> orders = new ArrayList<>();

        String sql = "SELECT " +
                "  o.order_id, " +
                "  o.total_price, " +
                "  u.email AS user_email, " +
                "  s.email AS staff_email, " +
                "  received.update_date AS order_date, " +
                "  latest.status AS order_status " +
                "FROM orders o " +
                "JOIN users u ON o.user_id = u.user_id " +
                "LEFT JOIN users s ON o.staff_id = s.user_id " +
                "LEFT JOIN order_status_history received " +
                "  ON o.order_id = received.order_id AND received.status = 'Forespørgsel' " +
                "LEFT JOIN order_status_history latest " +
                "  ON o.order_id = latest.order_id " +
                "  AND latest.update_date = ( " +
                "    SELECT MAX(update_date) " +
                "    FROM order_status_history " +
                "    WHERE order_id = o.order_id " +
                "  ) " +
                "WHERE o.user_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, customerId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Order order = mapOrder(rs);
                    
                    List<OrderItem> items = getOrderItemsForOrder(connection, order.getOrderId());
                    order.setOrderItems(items);

                    orders.add(order);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Error fetching orders by customer ID");
        }

        return orders;
    }

    // Update order
    public static void updateOrder(Order order) throws DatabaseException {
        String sql = "UPDATE orders SET user_id = ?, staff_id = ?, total_price = ? WHERE order_id = ?";

        try (Connection connection = connectionPool.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, order.getCustomer().getUserId());
                if (order.getStaff() != null) {
                    ps.setInt(2, order.getStaff().getUserId());
                } else {
                    ps.setNull(2, Types.INTEGER);
                }
                ps.setFloat(3, order.getTotalPrice());
                ps.setInt(4, order.getOrderId());

                ps.executeUpdate();
            }

            // Update carport/materials using same connection
            for (OrderItem item : order.getOrderItems()) {
                if (item.getProduct() instanceof Carport carport) {
                    CarportMapper.updateCarport(carport);
                } else if (item.getProduct() instanceof Material material) {
                    MaterialMapper.updateMaterial(material);
                }
            }

            connection.commit();
        } catch (SQLException e) {
            throw new DatabaseException(e, "Error updating order");
        }
    }

    public static void deleteOrder(int orderId) throws DatabaseException {
        String sql = "DELETE FROM orders WHERE order_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException(e, "Error deleting order");
        }
    }

    public static void addStatus(int orderId, String status) throws DatabaseException {
        String sql = "INSERT INTO order_status_history (order_id, status) VALUES (?, ?)";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            ps.setString(2, status);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not insert order status");
        }
    }

    //Map result set to full Order
    private static Order mapOrder(ResultSet rs) throws Exception {
        int orderId = rs.getInt("order_id");

        LocalDate orderDate = rs.getDate("order_date").toLocalDate();
        String orderStatus = rs.getString("order_status");
        float totalPrice = rs.getFloat("total_price");

        String userEmail = rs.getString("user_email");
        User customerUser = UserMapper.getUserByEmail(userEmail);
        Customer customer = (Customer) customerUser;

        String staffEmail = rs.getString("staff_email");
        Staff staff = null;
        if (staffEmail != null) {
            User staffUser = UserMapper.getUserByEmail(staffEmail);
            staff = (staffUser instanceof StaffManager manager) ? manager : (Staff) staffUser;
        }
        Order order = new Order(orderId, orderDate, orderStatus, customer, staff);
        order.setTotalPrice(totalPrice);
        return order;
    }

    public static List<Order> getOrdersByStaffId(int staffId) throws DatabaseException {
        List<Order> orders = new ArrayList<>();

        String sql = "SELECT " +
                "  o.order_id, " +
                "  o.total_price, " +
                "  u.email AS user_email, " +
                "  s.email AS staff_email, " +
                "  received.update_date AS order_date, " +
                "  latest.status AS order_status " +
                "FROM orders o " +
                "JOIN users u ON o.user_id = u.user_id " +
                "LEFT JOIN users s ON o.staff_id = s.user_id " +
                "LEFT JOIN order_status_history received " +
                "  ON o.order_id = received.order_id AND received.status = 'Forespørgsel' " +
                "LEFT JOIN order_status_history latest " +
                "  ON o.order_id = latest.order_id " +
                "  AND latest.update_date = ( " +
                "    SELECT MAX(update_date) " +
                "    FROM order_status_history " +
                "    WHERE order_id = o.order_id " +
                "  ) " +
                "WHERE o.staff_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, staffId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Order order = mapOrder(rs);

                    List<OrderItem> items = getOrderItemsForOrder(connection, order.getOrderId());
                    order.setOrderItems(items);

                    orders.add(order);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Error fetching orders by staff ID");
        }

        return orders;
    }

    public static List<Order> getUnassignedRequestOrders() throws DatabaseException {
        List<Order> orders = new ArrayList<>();

        String sql = "SELECT o.order_id, o.user_id, o.total_price, " +
                "   latest.status AS order_status, " +
                "   u.firstname, u.lastname, u.phone_number, u.email " +
                "FROM orders o " +
                "JOIN users u ON o.user_id = u.user_id " +
                "JOIN ( " +
                "   SELECT DISTINCT ON (order_id) order_id, status, update_date " +
                "   FROM order_status_history " +
                "   ORDER BY order_id, update_date DESC" +
                ") latest ON o.order_id = latest.order_id " +
                "WHERE o.staff_id IS NULL AND latest.status = 'Forespørgsel' " +
                "ORDER BY o.order_id DESC";

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Customer customer = new Customer(
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("phone_number"),
                        rs.getString("email"),
                        null,
                        1
                );
                customer.setUserId(rs.getInt("user_id"));

                Order order = new Order(customer);
                order.setOrderId(rs.getInt("order_id"));
                order.setTotalPrice(rs.getFloat("total_price"));
                order.setOrderStatus(rs.getString("order_status"));

                List<OrderItem> items = getOrderItemsForOrder(conn, order.getOrderId());
                order.setOrderItems(items);

                orders.add(order);
            }

        } catch (SQLException e) {
            throw new DatabaseException(e, "Error fetching unassigned request orders");
        }

        return orders;
    }

    public static void assignOrderToStaff(int orderId, int staffId) throws DatabaseException {
        String sql = "UPDATE orders SET staff_id = ? WHERE order_id = ?";

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, staffId);
            ps.setInt(2, orderId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException(e, "Fejl ved tildeling af ordre");
        }
    }

    public static void removeStaffFromOrder(int orderId, int staffId) throws DatabaseException {
        String sql = "UPDATE orders SET staff_id = NULL WHERE order_id = ? AND staff_id = ?";

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            ps.setInt(2, staffId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException(e, "Fejl ved fjernelse af medarbejder fra ordre");
        }
    }
}
