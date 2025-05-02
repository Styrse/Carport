package app.persistence.mappers;

import app.entities.products.carport.Carport;
import app.entities.products.carport.carportTestFactory.TestCarportFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static app.Main.connectionPool;
import static org.junit.jupiter.api.Assertions.*;

class CarportMapperTest {

    @BeforeAll
    static void setupClass() {
        try (Connection connection = connectionPool.getConnection()) {
            try (Statement stmt = connection.createStatement()) {
                // Drop child tables first due to FK dependencies
                stmt.execute("DROP TABLE IF EXISTS test.carports;");
                stmt.execute("DROP SEQUENCE IF EXISTS test.carports_carport_id_seq CASCADE;");

                // Create empty copies of the original tables
                stmt.execute("CREATE TABLE test.carports AS (SELECT * FROM public.carports) WITH NO DATA;");

                // Recreate sequences
                stmt.execute("CREATE SEQUENCE test.carports_carport_id_seq;");

                // Attach sequences to ID columns
                stmt.execute("ALTER TABLE test.carports ALTER COLUMN carport_id SET DEFAULT nextval('test.carports_carport_id_seq');");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Database setup for carports failed");
        }
    }

    @BeforeEach
    void setup() {
        try (Connection connection = connectionPool.getConnection()) {
            try (Statement stmt = connection.createStatement()) {
                // Truncate in FK order: child → parent
                stmt.execute("TRUNCATE TABLE test.carports RESTART IDENTITY CASCADE;");

                // Insert 2 example carports
                stmt.execute("INSERT INTO test.carports (width, length, height, roof_type, roof_angle, post_material_id, beam_material_id, rafter_material_id, fascia_material_id, total_price) VALUES " +
                        "(300, 500, 250, 'flat', 0, 1, 2, 3, 4, 25000), " +
                        "(400, 600, 275, 'pitched', 25, 1, 2, 3, 4, 32000);");

                stmt.execute("SELECT setval('test.carports_carport_id_seq', COALESCE((SELECT MAX(carport_id) + 1 FROM test.carports), 1), false)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Database reset for carports failed");
        }
    }


    @Test
    @DisplayName("CreateCarport Test")
    void createCarport() throws SQLException {
        //Arrange
        Carport carport = TestCarportFactory.createCarport(600, 400);

        //Act
        CarportMapper.createCarport(carport);
        int actual = carport.getItemId();

        //Assert
        int expected = 3;
        assertEquals(expected, actual);
    }

    @Test
    void getAllCarports() {
    }

    @Test
    void getCarportById() {
    }

    @Test
    void updateCarport() {
    }

    @Test
    void deleteCarportById() {
    }
}