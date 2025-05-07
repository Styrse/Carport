package app.persistence.mappers;

import app.entities.users.Customer;
import app.entities.users.Staff;
import app.entities.users.StaffManager;
import app.entities.users.User;
import app.exceptions.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static app.Main.connectionPool;

/**
 * {@code UserMapper} handles all database operations related to the {@code User} hierarchy
 * ({@code Customer}, {@code Staff}, and {@code StaffManager}).
 * <p>
 * Motivation:
 * <ul>
 *   <li><b>Separation of Concerns:</b> Encapsulates all persistence logic related to users, keeping business logic clean.</li>
 *   <li><b>CRUD Operations:</b> Provides a unified interface to create, read, update, and delete user records.</li>
 *   <li><b>Polymorphism Support:</b> Dynamically maps users to their correct subclass based on database flags.</li>
 *   <li><b>Security:</b> Uses prepared statements to safely handle user input and prevent SQL injection.</li>
 *   <li><b>Error Handling:</b> Converts SQL errors into custom {@code DatabaseException}s for easier debugging and consistency.</li>
 *   <li><b>Maintainability:</b> Isolates SQL logic, making future changes to the user schema or queries easier to manage.</li>
 * </ul>
 */

public class UserMapper {
    //Create
    public static void createUser(User user) throws DatabaseException {
        String sql =
                "INSERT INTO users (firstname, lastname, phone_number, email, password, role_id) " +
                "VALUES (?, ?, ?, ?, ?, ?) RETURNING user_id";
        //TODO: Address

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPassword());
            ps.setInt(6, user.getUserRole());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user.setUserId(rs.getInt("user_id"));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Error inserting user");
        }
    }

    //Read: get user by email
    public static User getUserByEmail(String email) throws DatabaseException {
        String sql = "SELECT * FROM users JOIN postcodes USING (postcode) WHERE email = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapUser(rs);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Error retrieving user by email");
        }
        return null;
    }

    //Read: get all users
    public static List<User> getAllUsers() throws DatabaseException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users JOIN postcodes USING (postcode)";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                users.add(mapUser(rs));
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Error retrieving users");
        }
        return users;
    }

    //Read: verify user login
    public static boolean verifyUser(String email, String password) throws DatabaseException {
        String sql = "SELECT 1 FROM users WHERE email = ? AND password = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // true if a row exists
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Error verifying user credentials");
        }
    }

    //Update full user
    public static void updateUser(User user) throws DatabaseException {
        String sql = "UPDATE users SET firstname = ?, lastname = ?, phone_number = ?, email = ?," +
                "password = ?,user_id = ? WHERE email = ?";
        //TODO: Address

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPassword());
            ps.setInt(6, user.getUserRole());
            ps.setString(7, user.getEmail());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e, "Error updating user");
        }
    }

    //Delete user by userId (GDPR-compliant hard delete)
    public static void deleteUserByEmail(String email) throws DatabaseException {
        String sql = "DELETE FROM users WHERE email = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, email);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e, "Error deleting user");
        }
    }

    //Helper: map ResultSet -> correct subclass of User
    public static User mapUser(ResultSet rs) throws SQLException, DatabaseException {
        int userId = rs.getInt("user_id");
        String firstname = rs.getString("firstname");
        String lastname = rs.getString("lastname");
        String address = rs.getString("address");
        int postcode = rs.getInt("postcode");
        String city = rs.getString("city");
        String phoneNumber = rs.getString("phone_number");
        String email = rs.getString("email");
        String password = rs.getString("password");
        int roleId = rs.getInt("role_id");

        return switch (roleId) {
            case 1 -> new Customer(userId, firstname, lastname, address, postcode, city, phoneNumber, email, password, roleId);
            case 2 -> new Staff(userId, firstname, lastname, phoneNumber, email, password, roleId);
            case 3 -> new StaffManager(userId, firstname, lastname, phoneNumber, email, password, roleId);
            default -> throw new DatabaseException("Unknown role type: " + roleId);
        };
    }
}
