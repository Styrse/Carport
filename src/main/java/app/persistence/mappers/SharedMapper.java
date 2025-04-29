package app.persistence.mappers;


import app.entities.Color;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SharedMapper {
    public static List<Color> getColors(ConnectionPool connectionPool) throws DatabaseException{
        List<Color> allColors = new ArrayList<>();

        String sql = "SELECT * FROM \"colors\"";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {

                ResultSet rs = ps.executeQuery();

                while(rs.next()) {
                    int colorId = rs.getInt("color_id");
                    String colorName = rs.getString("color_name");
                    String hexCode = rs.getString("hex_code");

                    allColors.add(new Color(colorId,colorName,hexCode));
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Error");
        }
        return allColors;
    }


    public static void addOrder(ConnectionPool connectionPool, int colorId, String colorName, String hexCode) throws DatabaseException {
        String sql = "INSERT INTO \"colors\" (color_id, color_name, hex_code) VALUES (?, ?, ?)";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {

                ps.setInt(1, colorId);
                ps.setString(2, colorName);
                ps.setString(3, hexCode);

                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Error executing query");
        }
    }


    public static void removeColor(ConnectionPool connectionPool, int colorId) throws DatabaseException {
        String sql = "DELETE FROM \"colors\" WHERE color_id=?;";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {

                ps.setInt(1, colorId);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Error executing query");
        }
    }




}
