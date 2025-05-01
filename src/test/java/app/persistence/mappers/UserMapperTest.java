package app.persistence.mappers;

import app.entities.users.Customer;
import app.entities.users.User;
import app.exceptions.DatabaseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static app.Main.connectionPool;
import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    @BeforeAll
    static void setupClass() {
        try (Connection connection = connectionPool.getConnection()) {
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
        try (Connection connection = connectionPool.getConnection()) {
            try (Statement stmt = connection.createStatement()) {
                //Clean the test table
                stmt.execute("TRUNCATE TABLE test.users RESTART IDENTITY CASCADE;");

                //Insert 3 example users
                stmt.execute("INSERT INTO test.users (user_id, firstname, email, password, is_staff, is_staff_manager) VALUES " +
                        "(1, 'alice', 'alice@gmail.com', 'Alice123', false, false), " +
                        "(2, 'bob', 'bob@hotmail.com', '123Bob', true, false), " +
                        "(3, 'charlie', 'charlie@me.com', 'Char123Lie', true, true);");

                //Reset sequences
                stmt.execute("SELECT setval('test.users_user_id_seq', COALESCE((SELECT MAX(user_id) + 1 FROM test.users), 1), false)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Database reset failed");
        }
    }

    @Test
    @DisplayName("CreateUser Test")
    void createUser() throws DatabaseException {
        //Arrange
        User user = new Customer("John", "Doe", 12345678, "john@doe.com", "Password123");

        //Act
        UserMapper.createUser(user);
        int actual = user.getUserId();

        //Assert
        int expected = 4;
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("GetAllUsers Test")
    void getAllUsers() throws DatabaseException {
        //Arrange

        //Act
        int actual = UserMapper.getAllUsers().size();

        //Assert
        int expected = 3;
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("GetUserByEmail Test")
    void getUserByEmail() throws DatabaseException {
        //Arrange

        //Act
        int actual = UserMapper.getUserByEmail("alice@gmail.com").getUserId();

        //Assert
        int expected = 1;
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("VerifyUser Test")
    void verifyUser() throws DatabaseException {
        //Arrange

        //Act
        boolean actual = UserMapper.verifyUser("alice@gmail.com", "Alice123");

        //Assert
        boolean expected = true;
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("UpdateUser Test")
    void updateUser() throws DatabaseException {
        //Arrange
        User user = new Customer("Alice", "Doe", 12345678, "alice@gmail.com", "Alice123");

        //Act
        UserMapper.updateUser(user);
        user = UserMapper.getUserByEmail("alice@gmail.com");
        String actual = user.getLastName();

        //Assert
        String expected = "Doe";
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("DeleteUser Test")
    void deleteUser() throws DatabaseException {
        //Arrange

        //Act
        UserMapper.deleteUser("alice@gmail.com");
        int actual = UserMapper.getAllUsers().size();

        //Assert
        int expected = 2;
        assertEquals(expected, actual);
    }
}