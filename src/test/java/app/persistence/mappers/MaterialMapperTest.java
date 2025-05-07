package app.persistence.mappers;

import app.entities.products.carport.carportTestFactory.TestPlankFactory;
import app.entities.products.materials.Material;
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

class MaterialMapperTest {

    @BeforeEach
    void setup() {
        try (Connection connection = connectionPool.getConnection()) {
            try (Statement stmt = connection.createStatement()) {
                stmt.execute("DROP TABLE IF EXISTS test.material_lengths;");
                stmt.execute("DROP TABLE IF EXISTS test.price_history;");
                stmt.execute("DROP TABLE IF EXISTS test.predefined_lengths;");
                stmt.execute("DROP TABLE IF EXISTS test.materials;");

                // Drop sequences
                stmt.execute("DROP SEQUENCE IF EXISTS test.materials_material_id_seq CASCADE;");
                stmt.execute("DROP SEQUENCE IF EXISTS test.price_history_price_history_id_seq CASCADE;");
                stmt.execute("DROP SEQUENCE IF EXISTS test.predefined_lengths_predefined_length_id_seq CASCADE;");

                // Create tables as empty copies of public tables
                stmt.execute("CREATE TABLE test.materials AS (SELECT * FROM public.materials) WITH NO DATA;");
                stmt.execute("CREATE TABLE test.price_history AS (SELECT * FROM public.price_history) WITH NO DATA;");
                stmt.execute("CREATE TABLE test.predefined_lengths AS (SELECT * FROM public.predefined_lengths) WITH NO DATA;");
                stmt.execute("CREATE TABLE test.material_lengths AS (SELECT * FROM public.material_lengths) WITH NO DATA;");

                // Recreate sequences
                stmt.execute("CREATE SEQUENCE test.materials_material_id_seq;");
                stmt.execute("CREATE SEQUENCE test.price_history_price_history_id_seq;");
                stmt.execute("CREATE SEQUENCE test.predefined_lengths_predefined_length_id_seq;");

                // Attach sequences
                stmt.execute("ALTER TABLE test.materials ALTER COLUMN material_id SET DEFAULT nextval('test.materials_material_id_seq');");
                stmt.execute("ALTER TABLE test.price_history ALTER COLUMN price_history_id SET DEFAULT nextval('test.price_history_price_history_id_seq');");
                stmt.execute("ALTER TABLE test.predefined_lengths ALTER COLUMN predefined_length_id SET DEFAULT nextval('test.predefined_lengths_predefined_length_id_seq');");

                //Truncate in FK order
                stmt.execute("TRUNCATE TABLE test.material_lengths RESTART IDENTITY CASCADE;");
                stmt.execute("TRUNCATE TABLE test.price_history RESTART IDENTITY CASCADE;");
                stmt.execute("TRUNCATE TABLE test.predefined_lengths RESTART IDENTITY CASCADE;");
                stmt.execute("TRUNCATE TABLE test.materials RESTART IDENTITY CASCADE;");

                //Insert predefined lengths (IDs 1–3)
                stmt.execute("INSERT INTO test.predefined_lengths (predefined_length_id, length) VALUES " +
                        "(1, 300), (2, 400), (3, 500);");
                stmt.execute("SELECT setval('test.predefined_lengths_predefined_length_id_seq', COALESCE((SELECT MAX(predefined_length_id) + 1 FROM test.predefined_lengths), 1), false)");

                //Insert materials (IDs 1–3)
                stmt.execute("INSERT INTO test.materials (material_id, name, description, unit, width, height, material_type, buckling_capacity, post_gap, length_overlap, side_overlap, gap_rafters, is_active) VALUES " +
                        "(1, 'post', 'Pressure-treated post', 'cm', 10, 10, 'Post', 500, 5, 2, 1, 60, true), " +
                        "(2, 'beam', 'Steel I-beam', 'cm', 15, 20, 'Beam', 1200, 0, 3, 1.5, 90, true), " +
                        "(3, 'fascia', 'Aluminum fascia board', 'cm', 5, 15, 'Fascia', 300, 0, 1, 0.5, 40, true);");
                stmt.execute("SELECT setval('test.materials_material_id_seq', COALESCE((SELECT MAX(material_id) + 1 FROM test.materials), 1), false)");

                //Insert price history for material 1
                stmt.execute("INSERT INTO test.price_history (price_history_id, material_id, cost_price, sales_price, valid_from, valid_to) VALUES " +
                        "(1, 1, 125.50, 199.99, '2024-01-01', '2024-03-01'), " +
                        "(2, 1, 135.00, 250.00, '2024-03-02', '2024-05-01'), " +
                        "(3, 1, 140.75, 269.95, '2024-05-02', NULL);");
                stmt.execute("SELECT setval('test.price_history_price_history_id_seq', COALESCE((SELECT MAX(price_history_id) + 1 FROM test.price_history), 1), false)");

                //Insert material_lengths entries
                stmt.execute("INSERT INTO test.material_lengths (material_id, predefined_length_id) VALUES " +
                        "(1, 1), (1, 2), " +
                        "(2, 2), (2, 3), " +
                        "(3, 1);");

                //Insert price history for all 3 materials
                stmt.execute("INSERT INTO test.price_history (price_history_id, material_id, cost_price, sales_price, valid_from, valid_to) VALUES " +
                        "(1, 1, 125.50, 199.99, '2024-01-01', '2024-03-01'), " +
                        "(2, 1, 135.00, 250.00, '2024-03-02', '2024-05-01'), " +
                        "(3, 1, 140.75, 269.95, '2024-05-02', NULL), " +
                        "(4, 2, 210.00, 315.00, '2024-01-01', '2024-04-01'), " +
                        "(5, 2, 220.00, 330.00, '2024-04-02', NULL), " +
                        "(6, 3, 180.00, 270.00, '2024-01-01', '2024-03-15'), " +
                        "(7, 3, 185.00, 278.00, '2024-03-16', NULL);");

                stmt.execute("SELECT setval('test.price_history_price_history_id_seq', COALESCE((SELECT MAX(price_history_id) + 1 FROM test.price_history), 1), false);");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Database reset for materials test failed");
        }
    }


    @Test
    @DisplayName("CreateMaterial Test")
    void createMaterial() throws SQLException, DatabaseException {
        //Arrange
        Material material = TestPlankFactory.createStandardBeam();
        MaterialMapper.createMaterial(material);

        //Act
        int actual = material.getItemId();

        //Assert
        int expected = 4;
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("GetAllMaterials Test")
    void getAllMaterials() throws DatabaseException {
        //Arrange

        //Act
        int actual = MaterialMapper.getAllMaterials().size();

        //Assert
        int expected = 3;
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("GetMaterialById Test")
    void getMaterialById() throws DatabaseException {
        //Arrange

        //Act
        float actual = MaterialMapper.getMaterialById(1).getWidth();

        //Assert
        int expected = 10;
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("UpdateMaterial Test")
    void updateMaterialById() throws DatabaseException, SQLException {
        //Arrange
        Material material = TestPlankFactory.createStandardPost();

        //Act
        MaterialMapper.updateMaterial(material);
        Material actual = MaterialMapper.getMaterialById(1);

        //Assert
        Material expected = material;
        assertEquals(expected.getWidth(), actual.getWidth());
        assertEquals(expected.getDescription(), actual.getDescription());
    }

    @Test
    @DisplayName("DeleteMaterialById Test")
    void deleteMaterialById() throws SQLException, DatabaseException {
        //Arrange

        //Act
        MaterialMapper.deleteMaterialById(1);
        int actual = MaterialMapper.getAllMaterials().size();

        //Assert
        int expected = 2;
        assertEquals(expected, actual);
    }
}