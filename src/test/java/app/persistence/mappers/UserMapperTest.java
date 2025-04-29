package app.persistence.mappers;

import app.entities.users.Customer;
import app.entities.users.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static app.Main.connectionPool;
import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/%s?currentSchema=test";
    private static final String DB = "carport";

    public static final ConnectionPool connectionPoolTest = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);

    @BeforeAll
    static void setupClass() {
        try (Connection connection = connectionPoolTest.getConnection()) {
            try (Statement stmt = connection.createStatement()) {
                //Drop tables and sequences if they exist
                stmt.execute("DROP TABLE IF EXISTS test.users;");
                stmt.execute("DROP SEQUENCE IF EXISTS test.users_user_id_seq CASCADE;");

                //Create tables as empty copies of the originals
                stmt.execute("CREATE TABLE test.users AS (SELECT * from public.users) WITH NO DATA;");;

                //Create sequences and attach to ID columns
                stmt.execute("CREATE SEQUENCE test.users_user_id_seq;");
                stmt.execute("ALTER TABLE test.users ALTER COLUMN user_id SET DEFAULT nextval('test.users_user_id_seq');");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Database setup failed");
        }
    }

    @BeforeEach
    void setup() {
        try (Connection connection = connectionPoolTest.getConnection()) {
            try (Statement stmt = connection.createStatement()) {
                //Clean the test table
                stmt.execute("TRUNCATE TABLE test.users RESTART IDENTITY CASCADE;");

                //Insert 3 example users
                stmt.execute("INSERT INTO test.users (user_id, firstname, password, is_staff, is_staff_manager) VALUES " +
                        "(1, 'alice', 'pass123', false, false), " +
                        "(2, 'bob', 'pass123', true, false), " +
                        "(3, 'charlie', 'pass123', true, true);");

                //Reset sequences
                stmt.execute("SELECT setval('test.users_user_id_seq', COALESCE((SELECT MAX(user_id) + 1 FROM test.users), 1), false)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Database reset failed");
        }
    }

    @Test
    void createUser() throws DatabaseException {
        // Arrange
        User user = new Customer("John", "Doe", 12345678, "john@doe.com", "Password123");

        // Act
        UserMapper.createUser(user);

        // Assert
        assertTrue(user.getUserId() > 0);
    }

    @Test
    void getAllUsers() {
    }

    @Test
    void getUserByEmail() {
    }

    @Test
    void verifyUser() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void mapUser() {
    }
}