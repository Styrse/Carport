package app.persistence.mappers;

import app.entities.orders.Order;
import app.entities.products.carport.Carport;
import app.entities.users.User;
import app.exceptions.DatabaseException;

import java.sql.*;
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

    // Create
    public static int createOrder(Order order) throws DatabaseException {
        String sql = "INSERT INTO orders (user_id, product_id, sub_product_id, order_date, payment_date, payment_status, staff_id, order_status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, order.getCustomer().getUserID());
            ps.setInt(2, order.getCarport().getRoofCover().getSubProductID());
            ps.setInt(3, order.get);
            //ps.setInt(3, order.getCarport().getSubProduct().getId());        // Replace with your actual method
            ps.setDate(4, Date.valueOf(order.getOrderDate()));
            ps.setDate(5, null); // You may want to support setting this
            ps.setString(6, order.getPaymentStatus());
            if (order.getStaff() != null) {
                ps.setInt(8, order.getStaff().getUserID());
            } else {
                ps.setNull(8, Types.INTEGER);
            }
            ps.setString(9, order.getOrderStatus());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    throw new DatabaseException("Failed to retrieve generated order ID");
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Error creating order");
        }
    }

    // Read: Get all orders
    public static List<Order> getAllOrders() throws DatabaseException {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                orders.add(mapOrder(rs));
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Error retrieving all orders");
        }

        return orders;
    }

    // Read: Get order by ID
    public static Order getOrderByOrderId(int orderId) throws DatabaseException {
        String sql = "SELECT * FROM orders WHERE order_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapOrder(rs);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Error retrieving order by ID");
        }

        return null;
    }

    // Read: Get all orders for a specific customer
    public static List<Order> getOrdersByCustomerId(int customerId) throws DatabaseException {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE user_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    orders.add(mapOrder(rs));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Error retrieving customer orders");
        }

        return orders;
    }

    // Update: Update payment status and order status
    public static void updateOrder(Order order) throws DatabaseException {
        String sql = "UPDATE orders SET order_status = ?, payment_status = ? WHERE order_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, order.getOrderStatus());
            ps.setString(2, order.getPaymentStatus());
            ps.setInt(3, order.getOrderID());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e, "Error updating order");
        }
    }

    // Delete
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

    // Helper: Map ResultSet to Order object
    private static Order mapOrder(ResultSet rs) throws SQLException, DatabaseException {
        int subProductId = rs.getInt("sub_product_id");
        Carport carport = CarportMapper.getCarportBySubProductID(subProductId);


        String userEmail = rs.getString("user_email");
        User customer = UserMapper.getUserByEmail(userEmail);


        String staffEmail = rs.getString("user_email");
        User staff = UserMapper.getUserByEmail(staffEmail);


        int orderId = rs.getInt("order_id");
        Date orderDate = rs.getDate("order_date");
        String orderStatus = rs.getString("order_status");
        String paymentStatus = rs.getString("payment_status");
        return new Order(orderId, orderDate.toLocalDate(), orderStatus, paymentStatus, carport, customer, staff);
    }
}
