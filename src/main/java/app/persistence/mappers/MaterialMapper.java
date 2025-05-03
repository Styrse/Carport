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
    public static void createMaterial(Material material) throws SQLException {
        String sql = "INSERT INTO materials " +
                "(name, description, unit, width, height, material_type, " +
                "buckling_capacity, post_gap, length_overlap, side_overlap, gap_rafters) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            material.prepareStatement(ps);

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                material.setItemId(rs.getInt(1));
            }
        }
    }

    //Read
    public static List<Material> getAllMaterials() throws DatabaseException {
        List<Material> allMaterials = new ArrayList<>();

        String sql = "SELECT * " +
                "FROM materials " +
                "JOIN price_history USING (material_id)";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                allMaterials.add(mapMaterial(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e, "Error retrieving materials");
        }

        return allMaterials;
    }

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

    //Update
    public static void updateMaterialById(int itemId) {

    }

    //Delete
    public static void deleteMaterialById(int itemId) {

    }

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
        List<Integer> preCutLengths = getPreCutLengths(itemId);

        int bucklingCapacity = rs.getInt("buckling_capacity");
        int postGap = rs.getInt("post_gap");
        int lengthOverlap = rs.getInt("length_overlap");
        int sideOverlap = rs.getInt("side_overlap");
        int gapRafters = rs.getInt("gap_rafters");

        return switch (type) {
            case "post" ->
                    new Post(itemId, name, description, costPrice, salesPrice, preCutLengths, unit, width, height, bucklingCapacity);
            case "beam" ->
                    new Beam(itemId, name, description, costPrice, salesPrice, preCutLengths, unit, width, height, postGap);
            case "rafter" ->
                    new Rafter(itemId, name, description, costPrice, salesPrice, preCutLengths, unit, width, height);
            case "fascia" ->
                    new Fascia(itemId, name, description, costPrice, salesPrice, preCutLengths, unit, width, height);
            case "roof_cover" ->
                    new RoofCover(itemId, name, description, costPrice, salesPrice, preCutLengths, unit, width, lengthOverlap, sideOverlap, gapRafters);
            default -> throw new DatabaseException("Unknown material type: " + type);
        };
    }
}
