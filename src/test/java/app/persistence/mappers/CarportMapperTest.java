package app.persistence.mappers;

import app.entities.products.carport.Carport;
import app.entities.products.carport.carportTestFactory.TestCarportFactory;
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

class CarportMapperTest {

    @BeforeAll
    static void setupClass() {
        try (Connection connection = connectionPool.getConnection()) {
            try (Statement stmt = connection.createStatement()) {
                // Drop child tables first due to FK dependencies
                stmt.execute("DROP TABLE IF EXISTS test.carports;");
                stmt.execute("DROP TABLE IF EXISTS test.materials;");
                stmt.execute("DROP TABLE IF EXISTS test.price_history;");
                stmt.execute("DROP SEQUENCE IF EXISTS test.price_history_price_history_id_seq CASCADE;");
                stmt.execute("DROP SEQUENCE IF EXISTS test.carports_carport_id_seq CASCADE;");
                stmt.execute("DROP SEQUENCE IF EXISTS test.materials_material_id_seq CASCADE;");

                // Create empty copies of the original tables
                stmt.execute("CREATE TABLE test.materials AS (SELECT * FROM public.materials) WITH NO DATA;");
                stmt.execute("CREATE TABLE test.carports AS (SELECT * FROM public.carports) WITH NO DATA;");
                stmt.execute("CREATE TABLE test.price_history AS (SELECT * FROM public.price_history) WITH NO DATA;");

                // Recreate sequences
                stmt.execute("CREATE SEQUENCE test.materials_material_id_seq;");
                stmt.execute("CREATE SEQUENCE test.carports_carport_id_seq;");
                stmt.execute("CREATE SEQUENCE test.price_history_price_history_id_seq;");

                // Attach sequences to ID columns
                stmt.execute("ALTER TABLE test.materials ALTER COLUMN material_id SET DEFAULT nextval('test.materials_material_id_seq');");
                stmt.execute("ALTER TABLE test.carports ALTER COLUMN carport_id SET DEFAULT nextval('test.carports_carport_id_seq');");
                stmt.execute("ALTER TABLE test.price_history ALTER COLUMN price_history_id SET DEFAULT nextval('test.price_history_price_history_id_seq');");

                // Recreate constraints (optional, if dropped)
                // e.g., FK from carports to materials — if needed in the test schema
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
                stmt.execute("TRUNCATE TABLE test.materials RESTART IDENTITY CASCADE;");

                // Insert sample materials (IDs 1–4)
                stmt.execute("INSERT INTO test.materials (material_id, name, description, unit, width, height, material_type, buckling_capacity, post_gap, length_overlap, side_overlap, gap_rafters, is_active) VALUES " +
                        "(1, 'Post', 'Strong wood post', 'cm', 10, 10, 'Post', 500, 5, 2, 1, 60, true), " +
                        "(2, 'Beam', 'Galvanized steel beam', 'cm', 15, 20, 'Beam', 1000, 0, 3, 1, 90, true), " +
                        "(3, 'Rafter', 'Softwood rafter', 'cm', 8, 20, 'Rafter', 400, 4, 2, 1, 70, true), " +
                        "(4, 'Fascia', 'Aluminum fascia', 'cm', 5, 15, 'Fascia', 300, 0, 1, 0.5, 40, true);");

                stmt.execute("SELECT setval('test.materials_material_id_seq', COALESCE((SELECT MAX(material_id) + 1 FROM test.materials), 1), false)");

                // Insert 2 example carports
                stmt.execute("INSERT INTO test.carports (width, length, height, roof_type, roof_angle, post_material_id, beam_material_id, rafter_material_id, fascia_material_id, total_price) VALUES " +
                        "(300, 500, 250, 'flat', 0, 1, 2, 3, 2, 25000), " +
                        "(400, 600, 275, 'pitched', 25, 1, 2, 3, 1, 32000);");

                stmt.execute("SELECT setval('test.carports_carport_id_seq', COALESCE((SELECT MAX(carport_id) + 1 FROM test.carports), 1), false)");

                // Insert price history for all materials (1–4)
                stmt.execute("INSERT INTO test.price_history (price_history_id, material_id, cost_price, sales_price, valid_from, valid_to) VALUES " +
                        "(1, 1, 100.00, 150.00, '2024-01-01', '2024-03-01'), " +
                        "(2, 1, 110.00, 160.00, '2024-03-02', NULL), " +
                        "(3, 2, 200.00, 280.00, '2024-01-01', NULL), " +
                        "(4, 3, 180.00, 260.00, '2024-01-01', NULL), " +
                        "(5, 4, 75.00, 120.00, '2024-01-01', NULL);");

                stmt.execute("SELECT setval('test.price_history_price_history_id_seq', COALESCE((SELECT MAX(price_history_id) + 1 FROM test.price_history), 1), false);");

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
        Carport carport = TestCarportFactory.createCarport();

        //Act
        CarportMapper.createCarport(carport);
        int actual = carport.getItemId();

        //Assert
        int expected = 3;
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("GetAllOrders Test")
    void getAllCarports() throws DatabaseException {
        //Arrange

        //Act
        int actual = CarportMapper.getAllCarports().size();

        //Assert
        int expected = 3;
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("GetCarportById Test")
    void getCarportById() throws DatabaseException {
        //Arrange

        //Act
        int actual = CarportMapper.getCarportById(1).getLength();

        //Assert
        int expected = 500;
        assertEquals(expected, actual);

    }

    @Test
    @DisplayName("UpdateCarport Test")
    void updateCarport() throws DatabaseException {
        //Arrange
        Carport carport = TestCarportFactory.createCarport();

        //Act
        CarportMapper.updateCarport(carport);
        Carport actual = CarportMapper.getCarportById(1);

        //Assert
        Carport expected = TestCarportFactory.createCarport();
        assertEquals(expected.getLength(), actual.getLength());
        assertEquals(expected.getWidth(), actual.getWidth());
        assertEquals(expected.getHeight(), actual.getHeight());
    }

    @Test
    @DisplayName("DeleteCarportById Test")
    void deleteCarportById() throws DatabaseException {
        //Arrange

        //Act
        CarportMapper.deleteCarportById(1);
        int actual = CarportMapper.getAllCarports().size();

        //Assert
        int expected = 1;
        assertEquals(expected, actual);
    }
}