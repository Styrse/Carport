package app.persistence.mappers;

import app.entities.products.materials.Material;
import app.entities.products.materials.planks.Beam;
import app.entities.products.materials.planks.Fascia;
import app.entities.products.materials.planks.Post;
import app.entities.products.materials.planks.Rafter;
import app.entities.products.materials.roof.RoofCover;
import app.exceptions.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static app.Main.connectionPool;

public class MaterialMapper {
    //Create
    public static void createMaterial(Material material) throws SQLException, DatabaseException {
        String sql = "INSERT INTO materials " +
                "(name, description, unit, width, height, material_type, " +
                "buckling_capacity, post_gap, length_overlap, side_overlap, gap_rafters) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING material_id";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            material.prepareStatement(ps);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    material.setItemId(rs.getInt("material_id"));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Error inserting material");
        }
    }

    //Read
    public static Material getMaterialById(int itemId) throws DatabaseException {
        String sql = "SELECT * " +
                "FROM materials " +
                "JOIN price_history USING (material_id) " +
                "WHERE material_id = ? " +
                "ORDER BY valid_to DESC " +
                "LIMIT 1";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, itemId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapMaterial(rs);
                } else {
                    throw new DatabaseException("Material not found with id: " + itemId);
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException(e, "Error fetching material with id: " + itemId);
        }
    }

    public static List<Material> getAllMaterials() throws DatabaseException {
        List<Material> allMaterials = new ArrayList<>();

        String sql = "SELECT * " +
                "FROM materials " +
                "JOIN (" +
                "    SELECT DISTINCT ON (material_id) * " +
                "    FROM price_history " +
                "    ORDER BY material_id, valid_from DESC" +
                ") AS latest_price USING (material_id) " +
                "JOIN material_lengths USING (material_id) " +
                "JOIN predefined_lengths USING (predefined_length_id) " +
                "WHERE materials.is_active = true";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int material_id = rs.getInt("material_id");

                Material material = allMaterials.stream().filter(m -> m.getItemId() == material_id)
                        .findFirst().orElse(null);

                if (material == null) {
                    material = mapMaterial(rs);
                    allMaterials.add(material);
                }

                int length = rs.getInt("length");
                material.addToPreCutsLengths(length);
                //TODO: sout
                System.out.println("Name: " + material.getName() + " ID: " + material.getItemId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e, "Error retrieving materials");
        }

        return allMaterials;
    }

    //Update
    public static void updateMaterial(Connection connection, Material material) throws DatabaseException {
        String sql = "UPDATE materials SET name = ?, description = ?, unit = ?, width = ?, height = ?," +
                "material_type = ?, buckling_capacity = ?, post_gap = ?, length_overlap = ?," +
                "side_overlap = ?, gap_rafters = ? WHERE material_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            material.prepareStatement(ps);

            ps.setInt(12, material.getItemId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e, "Error updating material");
        }
    }

    //Delete: Soft delete. Changes is_active to false so materials still can be found for a Carport
    public static void deleteMaterialById(int itemId) throws SQLException, DatabaseException {
        String sql = "UPDATE materials SET is_active = false WHERE material_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, itemId);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e, "Error deleting material");
        }
    }

    //Helper Methods
    private static List<Integer> getPreCutLengths(int itemId) throws DatabaseException {
        List<Integer> lengths = new ArrayList<>();

        String sql = "SELECT length " +
                "FROM predefined_lengths  " +
                "JOIN material_lengths  USING (predefined_length_id) " +
                "WHERE material_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, itemId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lengths.add(rs.getInt("length"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e, "Error retrieving materials");
        }

        return lengths;
    }

    public static Material mapMaterial(ResultSet rs) throws SQLException, DatabaseException {
        int itemId = rs.getInt("material_id");
        String name = rs.getString("name");
        String description = rs.getString("description");
        String unit = rs.getString("unit");
        int width = rs.getInt("width");
        int height = rs.getInt("height");
        float costPrice = rs.getFloat("cost_price");
        float salesPrice = rs.getFloat("sales_price");
        String type = rs.getString("material_type");

        int bucklingCapacity = rs.getInt("buckling_capacity");
        int postGap = rs.getInt("post_gap");
        int lengthOverlap = rs.getInt("length_overlap");
        int sideOverlap = rs.getInt("side_overlap");
        int gapRafters = rs.getInt("gap_rafters");

        return switch (type) {
            case "Post" ->
                    new Post(itemId, name, description, costPrice, salesPrice, unit, width, height, bucklingCapacity);
            case "Beam" ->
                    new Beam(itemId, name, description, costPrice, salesPrice, unit, width, height, postGap);
            case "Rafter" ->
                    new Rafter(itemId, name, description, costPrice, salesPrice, unit, width, height);
            case "Fascia" ->
                    new Fascia(itemId, name, description, costPrice, salesPrice, unit, width, height);
            case "RoofCover" ->
                    new RoofCover(itemId, name, description, costPrice, salesPrice, unit, width, lengthOverlap, sideOverlap, gapRafters);
            default -> throw new DatabaseException("Unknown material type: " + type);
        };
    }
}
