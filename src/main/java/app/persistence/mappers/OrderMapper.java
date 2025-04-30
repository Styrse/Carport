package app.persistence.mappers;

import app.entities.orders.Order;
import app.entities.orders.OrderItem;
import app.entities.users.Customer;
import app.entities.users.Staff;
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
 */
public class OrderMapper {

    // Create a new order and return the generated order_id
    public static int createOrder(Order order) throws DatabaseException {
        String sql = "INSERT INTO orders (user_id, staff_id, order_date, payment_date, payment_status) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, order.getCustomer().getUserId());
            if (order.getStaff() != null) {
                ps.setInt(2, order.getStaff().getUserId());
            } else {
                ps.setNull(2, Types.INTEGER);
            }
            ps.setDate(3, Date.valueOf(order.getOrderDate()));
            if (order.getPaymentDate() != null) {
                ps.setDate(4, Date.valueOf(order.getPaymentDate()));
            } else {
                ps.setNull(4, Types.DATE);
            }
            ps.setString(5, order.getPaymentStatus());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int orderId = rs.getInt(1);

                    // Insert the order items
                    for (OrderItem item : order.getOrderItems()) {
                        insertOrderItem(orderId, item);
                    }

                    return orderId;
                } else {
                    throw new DatabaseException("Failed to retrieve generated order ID");
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException(e, "Error creating order");
        }
    }

    // Helper to insert an order item
    private static void insertOrderItem(int orderId, OrderItem item) throws DatabaseException {
        String sql = "INSERT INTO order_items (order_id, product_id, item_id, quantity) VALUES (?, ?, ?, ?)";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            ps.setInt(2, item.getProductId());
            ps.setInt(3, item.getSubProductId());
            ps.setInt(4, item.getQuantity());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException(e, "Error inserting order item");
        }
    }

    // Read all orders
    public static List<Order> getAllOrders() throws DatabaseException {
        List<Order> orders = new ArrayList<>();

        String sql = "SELECT o.*, u.email AS user_email, s.email AS staff_email " +
                "FROM orders o " +
                "JOIN users u ON o.user_id = u.user_id " +
                "LEFT JOIN users s ON o.staff_id = s.user_id";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                orders.add(mapOrder(rs));
            }

        } catch (SQLException e) {
            throw new DatabaseException(e, "Error fetching all orders");
        }

        return orders;
    }

    // Read one order by orderId
    public static Order getOrderByOrderId(int orderId) throws DatabaseException {
        String sql = "SELECT o.*, u.email AS user_email, s.email AS staff_email " +
                "FROM orders o " +
                "JOIN users u ON o.user_id = u.user_id " +
                "LEFT JOIN users s ON o.staff_id = s.user_id " +
                "WHERE o.order_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, orderId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapOrder(rs);
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException(e, "Error fetching order by ID");
        }

        return null;
    }

    // Read all orders by customerId
    public static List<Order> getOrdersByCustomerId(int customerId) throws DatabaseException {
        List<Order> orders = new ArrayList<>();

        String sql = "SELECT o.*, u.email AS user_email, s.email AS staff_email " +
                "FROM orders o " +
                "JOIN users u ON o.user_id = u.user_id " +
                "LEFT JOIN users s ON o.staff_id = s.user_id " +
                "WHERE o.user_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, customerId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    orders.add(mapOrder(rs));
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException(e, "Error fetching orders by customer ID");
        }

        return orders;
    }

    // Update order
    public static void updateOrder(Order order) throws DatabaseException {
        String sql = "UPDATE orders SET payment_date = ?, payment_status = ?, staff_id = ? WHERE order_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            if (order.getPaymentDate() != null) {
                ps.setDate(1, Date.valueOf(order.getPaymentDate()));
            } else {
                ps.setNull(1, Types.DATE);
            }

            ps.setString(2, order.getPaymentStatus());

            if (order.getStaff() != null) {
                ps.setInt(3, order.getStaff().getUserId());
            } else {
                ps.setNull(3, Types.INTEGER);
            }

            ps.setInt(4, order.getOrderId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException(e, "Error updating order");
        }
    }

    // Delete order
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

    // Map result set to full Order
    private static Order mapOrder(ResultSet rs) throws SQLException, DatabaseException {
        int orderId = rs.getInt("order_id");
        LocalDate orderDate = rs.getDate("order_date").toLocalDate();
        String orderStatus = rs.getString("order_status");
        String paymentStatus = rs.getString("payment_status");
        LocalDate paymentDate = rs.getDate("payment_date").toLocalDate();

        String userEmail = rs.getString("user_email");
        Customer customer = (Customer) UserMapper.getUserByEmail(userEmail);

        String staffEmail = rs.getString("user_email");
        Staff staff = null;
        if (staffEmail != null) {
            staff = (Staff) UserMapper.getUserByEmail(staffEmail);
        }

        Order order = new Order(orderId, orderDate, orderStatus, paymentStatus, paymentDate, customer, staff);

        List<OrderItem> items = getOrderItemsByOrderId(orderId);
        order.getOrderItems().addAll(items);

        return order;
    }

    private static List<OrderItem> getOrderItemsByOrderId(int orderId) throws DatabaseException {
        List<OrderItem> items = new ArrayList<>();

        String sql = "SELECT oi.product_id, oi.sub_product_id, oi.quantity,\n" +
                "bm.name AS building_material_name\n" +
                "FROM order_items oi\n" +
                "LEFT JOIN building_materials bm ON oi.product_id = 1 AND oi.sub_product_id = bm.sub_product_id\n" +
                "WHERE oi.order_id = ?";


        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, orderId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int productId = rs.getInt("product_id");
                    int subProductId = rs.getInt("sub_product_id");
                    String itemName = rs.getString("item_name");
                    int quantity = rs.getInt("quantity");

                    OrderItem orderItem = new OrderItem(productId, subProductId, itemName, quantity);
                    items.add(orderItem);
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException(e, "Error loading order items");
        }

        return items;
    }
}
