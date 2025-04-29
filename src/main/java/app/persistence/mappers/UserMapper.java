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
    // Create
    public static void createUser(User user) throws DatabaseException {
        String sql = "INSERT INTO users (firstname, lastname, phone_number, email, password, is_staff, is_staff_manager) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING user_id";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setInt(3, user.getPhoneNumber());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPassword());
            ps.setBoolean(6, user instanceof Staff || user instanceof StaffManager);
            ps.setBoolean(7, user instanceof StaffManager);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user.setUserId(rs.getInt("user_id"));
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException(e, "Error inserting user");
        }
    }

    // Read: get all users
    public static List<User> getAllUsers() throws DatabaseException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";

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

    // Read: get user by email
    public static User getUserByEmail(String email) throws DatabaseException {
        String sql = "SELECT * FROM users WHERE email = ?";

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

    // Read: verify user login
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

    // Update full user
    public static void updateUser(User user) throws DatabaseException {
        String sql = "UPDATE users SET firstname = ?, lastname = ?, phone_number = ?, email = ?, password = ?, is_staff = ?, is_staff_manager = ? WHERE email = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setInt(3, user.getPhoneNumber());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPassword());
            ps.setBoolean(6, user instanceof Staff);
            ps.setBoolean(7, user instanceof StaffManager);
            ps.setString(8, user.getEmail());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e, "Error updating user");
        }
    }

    // Delete user by userId (GDPR-compliant hard delete)
    public static void deleteUser(String email) throws DatabaseException {
        String sql = "DELETE FROM users WHERE email = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e, "Error deleting user");
        }
    }

    // Helper: map ResultSet → correct subclass of User
    public static User mapUser(ResultSet rs) throws SQLException {
        boolean isStaff = rs.getBoolean("is_staff");
        boolean isStaffManager = rs.getBoolean("is_staff_manager");

        User user;
        if (isStaffManager) {
            user = new StaffManager();
        } else if (isStaff) {
            user = new Staff();
        } else {
            user = new Customer();
        }

        user.setUserId(rs.getInt("user_id"));
        user.setFirstName(rs.getString("firstname"));
        user.setLastName(rs.getString("lastname"));
        user.setPhoneNumber(rs.getInt("phone_number"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));

        return user;
    }
}
