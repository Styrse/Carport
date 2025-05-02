package app.persistence.mappers;

import app.entities.orders.OrderItem;
import app.exceptions.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static app.Main.connectionPool;

public class OrderItemMapper {

    //Create: order_items row
    public static int createOrderItem(int orderId, OrderItem item) throws DatabaseException {
        String sql = "INSERT INTO order_items (order_id, item_type, quantity) VALUES (?, ?, ?) RETURNING order_item_id";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            ps.setString(2, item.getProduct().getItemType());
            ps.setInt(3, item.getQuantity());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("order_item_id");
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException(e, "Error creating order item");
        }

        throw new DatabaseException("Could not retrieve order_item_id after insert");
    }

    //Create: link to material
    public static void linkToMaterial(int orderItemId, int materialId) throws DatabaseException {
        String sql = "INSERT INTO order_item_material (order_item_id, material_id) VALUES (?, ?)";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, orderItemId);
            ps.setInt(2, materialId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException(e, "Error linking order item to material");
        }
    }

    //Create: link to carport
    public static void linkToCarport(int orderItemId, int carportId) throws DatabaseException {
        String sql = "INSERT INTO order_item_carport (order_item_id, carport_id) VALUES (?, ?)";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, orderItemId);
            ps.setInt(2, carportId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException(e, "Error linking order item to carport");
        }
    }

    //Read: get items by order ID
    public static List<OrderItem> getItemsByOrderId(int orderId) throws DatabaseException {
        List<OrderItem> items = new ArrayList<>();
        String sql = "SELECT * FROM order_items WHERE order_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    items.add(mapOrderItem(rs));
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException(e, "Error retrieving order items");
        }

        return items;
    }

    //Update: quantity
    public static void updateQuantity(int orderItemId, int quantity) throws DatabaseException {
        String sql = "UPDATE order_items SET quantity = ? WHERE order_item_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, quantity);
            ps.setInt(2, orderItemId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException(e, "Error updating order item quantity");
        }
    }

    //Delete: order_item
    public static void deleteOrderItem(int orderItemId) throws DatabaseException {
        String sql = "DELETE FROM order_items WHERE order_item_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, orderItemId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException(e, "Error deleting order item");
        }
    }

    //Helper: map ResultSet -> OrderItem object
    private static OrderItem mapOrderItem(ResultSet rs) throws SQLException, DatabaseException {
        int id = rs.getInt("order_item_id");
        int orderId = rs.getInt("order_id");
        String itemType = rs.getString("item_type");
        int quantity = rs.getInt("quantity");

        return switch (itemType) {
            case "carport" -> new OrderItem(CarportMapper.getCarportById(id), quantity);
            case "material" -> new OrderItem(MaterialMapper.getMaterialById(id), quantity);
            default -> throw new IllegalStateException("Unexpected value: " + itemType);
        };
    }
}
