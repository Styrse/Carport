package app.persistence.mappers;

import app.entities.users.Customer;
import app.entities.users.User;
import app.exceptions.DatabaseException;
import app.utils.PasswordUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static app.Main.connectionPool;
import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    @BeforeEach
    void setup() {
        try (Connection connection = connectionPool.getConnection()) {
            try (Statement stmt = connection.createStatement()) {
                // --- DROP AND CREATE test.users ---
                stmt.execute("DROP TABLE IF EXISTS test.users;");
                stmt.execute("DROP SEQUENCE IF EXISTS test.users_user_id_seq CASCADE;");

                // Create test.users table as a copy of public.users schema
                stmt.execute("CREATE TABLE test.users AS (SELECT * from public.users) WITH NO DATA;");
                stmt.execute("CREATE SEQUENCE test.users_user_id_seq;");
                stmt.execute("ALTER TABLE test.users ALTER COLUMN user_id SET DEFAULT nextval('test.users_user_id_seq');");

                // --- OPTIONAL: Clean up postcodes in test schema ---
                stmt.execute("TRUNCATE TABLE public.postcodes RESTART IDENTITY CASCADE;");

                // --- INSERT REQUIRED POSTCODES ---
                stmt.execute("INSERT INTO public.postcodes (postcode, city) VALUES " +
                        "('1000', 'Copenhagen'), " +
                        "('2000', 'Frederiksberg'), " +
                        "('3000', 'Helsingør');");

                // --- INSERT TEST USERS ---
                String hash1 = PasswordUtil.hashPassword("Alice123");
                String hash2 = PasswordUtil.hashPassword("123Bob");
                String hash3 = PasswordUtil.hashPassword("Char123Lie");

                stmt.execute("INSERT INTO test.users (user_id, firstname, lastname, phone_number, email, password, role_id, address, postcode) VALUES " +
                        "(1, 'Alice', 'Smith', '12345678', 'alice@gmail.com', '" + hash1 + "', 1, 'Main Street 1', '1000'), " +
                        "(2, 'Bob', 'Johnson', '23456789', 'bob@hotmail.com', '" + hash2 + "', 2, 'Second Street 2', '2000'), " +
                        "(3, 'Charlie', 'Brown', '34567890', 'charlie@me.com', '" + hash3 + "', 3, 'Third Street 3', '3000');");

                // --- RESET USER ID SEQUENCE ---
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
        User user = new Customer("John", "Doe", "12345678", "john@doe.com", "Password123", 1) {};

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
        User user = UserMapper.getUserByEmail("alice@gmail.com");
        user.setLastName("Doe");

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
    void deleteUserByEmail() throws DatabaseException {
        //Arrange

        //Act
        UserMapper.deleteUserByEmail("alice@gmail.com");
        int actual = UserMapper.getAllUsers().size();

        //Assert
        int expected = 2;
        assertEquals(expected, actual);
    }
}