package app.persistence.mappers;

import app.entities.users.Customer;
import app.entities.users.Staff;
import app.entities.users.StaffManager;
import app.entities.users.User;
import app.exceptions.DatabaseException;
import app.utils.PasswordUtil;

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
        String sql = "INSERT INTO users (firstname, lastname, address, postcode, phone_number, email, password, role_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?) RETURNING user_id";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            userHelper(user, ps);
            ps.setInt(8, user.getUserRole());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user.setUserId(rs.getInt("user_id"));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Error inserting user into database");
        }
    }

    //Read: get user by email
    public static User getUserByEmail(String email) throws DatabaseException {
        String sql = "SELECT * FROM users JOIN postcodes USING (postcode) WHERE email ILIKE ?";

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

    public static User getUserById(int id) throws DatabaseException {
        String sql = "SELECT * FROM users LEFT JOIN postcodes USING (postcode) WHERE user_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);
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
        String sql = "SELECT * FROM users ORDER BY user_id DESC";

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
        String sql = "SELECT password FROM users WHERE email ILIKE ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    //Using PasswordUtils to verify hashed password
                    String hashedPassword = rs.getString("password");
                    return PasswordUtil.checkPassword(password, hashedPassword);
                } else {
                    throw new DatabaseException("No user with that email");
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Error verifying user credentials");
        }
    }

    //Update full user
    public static void updateUser(User user) throws DatabaseException {
        String sql = "UPDATE users SET firstname = ?, lastname = ?, address = ?, postcode = ?, phone_number = ?, " +
                "email = ?, password = ? WHERE user_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            userHelper(user, ps);
            ps.setInt(8, user.getUserId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e, "Error updating user");
        }
    }

    private static void userHelper(User user, PreparedStatement ps) throws SQLException {
        ps.setString(1, user.getFirstName());
        ps.setString(2, user.getLastName());
        ps.setString(3, user.getAddress());
        ps.setString(4, user.getPostcode());
        ps.setString(5, user.getPhone());
        ps.setString(6, user.getEmail());
        ps.setString(7, user.getPassword());
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

    public static void deleteUserByUserId(int userId) throws DatabaseException {
        String sql = "DELETE FROM users WHERE user_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);

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
        String phone = rs.getString("phone_number");
        String email = rs.getString("email");
        String password = rs.getString("password");
        int roleId = rs.getInt("role_id");

        //Did this try as Staff don't have address etc
        String address;
        String postcode;
        String city;
        try {
            address = rs.getString("address");
            postcode = rs.getString("postcode");
            city = rs.getString("city");
        } catch (SQLException e) {
            address = null;
            postcode = null;
            city = null;
        }

        return switch (roleId) {
            case 1 -> new Customer(userId, firstname, lastname, address, postcode, city, phone, email, password, roleId);
            case 2 -> new Staff(userId, firstname, lastname, phone, email, password, roleId);
            case 3 -> new StaffManager(userId, firstname, lastname, phone, email, password, roleId);
            default -> throw new DatabaseException("Unknown user type: " + roleId);
        };
    }

    public static void insertPostcode(String postcode, String city) throws DatabaseException {
        String checkSql = "SELECT city FROM postcodes WHERE postcode = ?";
        String insertSql = "INSERT INTO postcodes (postcode, city) VALUES (?, ?)";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement check = connection.prepareStatement(checkSql)) {
                check.setString(1, postcode);
                try (ResultSet rs = check.executeQuery()) {
                    if (rs.next()) {
                        String existingCity = rs.getString("city");
                        if (!existingCity.equalsIgnoreCase(city)) {
                            throw new DatabaseException("Postcode exists with a different city name.");
                        }
                        return;
                    }
                }
            }

            try (PreparedStatement insert = connection.prepareStatement(insertSql)) {
                insert.setString(1, postcode);
                insert.setString(2, city);
                insert.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Error handling postcode insertion");
        }
    }
}
