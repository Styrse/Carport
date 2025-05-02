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








    //Create
    /*public void createMaterial(Material material) throws SQLException {
        String sql = "INSERT INTO materials " +
                "(name, description, unit, width, heigth, material_type, " +
                "buckling_capacity, post_gap, length_overlap, side_overlap, gap_rafters, is_active) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, material.getName());
            ps.setString(2, material.getDescription());
            ps.setString(3, material.getUnit());

            setNullableDouble(ps, 4, material.getWidth());
            setNullableDouble(ps, 5, material.getHeigth());
            ps.setString(6, material.getMaterialType());
            setNullableDouble(ps, 7, material.getBucklingCapacity());
            setNullableDouble(ps, 8, material.getPostGap());
            setNullableDouble(ps, 9, material.getLengthOverlap());
            setNullableDouble(ps, 10, material.getSideOverlap());
            setNullableDouble(ps, 11, material.getGapRafters());

            ps.setBoolean(12, material.isActive());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                material.setId(rs.getInt(1));
            }
        }
    }

    private void setNullableDouble(PreparedStatement ps, int index, Double value) throws SQLException {
        if (value != null) {
            ps.setDouble(index, value);
        } else {
            ps.setNull(index, Types.NUMERIC);
        }
    }

*/










    //Read
    public static List<Material> getAllMaterials() throws DatabaseException {
        List<Material> allMaterials = new ArrayList<>();

        String sql =
                "SELECT DISTINCT ON (m.material_id) " +
                        "* " +
                        "FROM materials m " +
                        "JOIN price_history ph ON m.material_id = ph.material_id " +
                        "WHERE m.is_active = TRUE " +
                        "AND ph.valid_to IS NULL;";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int itemId = rs.getInt("material_id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                String unit = rs.getString("unit");
                int width = rs.getInt("width");
                int height = rs.getInt("height");
                double costPrice = rs.getDouble("cost_price");
                double salesPrice = rs.getDouble("sales_price");
                String type = rs.getString("material_type");
                int bucklingCapacity = rs.getInt("buckling_capacity");
                int postGap = rs.getInt("post_gap");
                List<Integer> preCutLengths = getPreCutLengths(itemId);
                int lengthOverlap = rs.getInt("length_overlap");
                int sideOverlap = rs.getInt("side_overlap");
                int gapRafters = rs.getInt("gap_rafters");

                Material material = switch (type) {
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

                allMaterials.add(material);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e, "Error retrieving materials");
        }

        return allMaterials;
    }

    public static Material getMaterialById(int itemId) throws DatabaseException {
        String sql = "SELECT * FROM materials WHERE material_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, itemId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapMaterial(rs);
                } else {
                    throw new DatabaseException("Material not found with Id: " + itemId);
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException(e, "Error fetching material with ID: " + itemId);
        }
    }


    private static List<Integer> getPreCutLengths(int itemId) throws DatabaseException {
        List<Integer> lengths = new ArrayList<>();

        String sql =
                "SELECT length " +
                        "FROM material_lengths ml " +
                        "JOIN predefined_lengths pl ON ml.predefined_length_id = pl.predefined_length_id " +
                        "WHERE ml.material_id = ?";

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

    //Update

    //Delete
}
