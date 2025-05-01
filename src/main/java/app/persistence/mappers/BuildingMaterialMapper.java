package app.persistence.mappers;

import app.entities.products.materials.Material;
import app.entities.products.materials.planks.Beam;
import app.entities.products.materials.planks.Fascia;
import app.entities.products.materials.planks.Post;
import app.entities.products.materials.planks.Rafter;
import app.entities.products.materials.roof.RoofCover;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static app.Main.connectionPool;

public class BuildingMaterialMapper {

    public static List<Material> getMaterials(ConnectionPool connectionPool) throws DatabaseException {
        List<Material> allMaterials = new ArrayList<>();

        String sql =
                "SELECT * " +
                "FROM building_materials " +
                "JOIN price_history USING (building_material_id) " +
                "JOIN predefined_lengths USING (predefined_length_id)";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int materialId = rs.getInt("building_material_id");
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
                List<Integer> preCutLengths = getPreCutLengths(materialId);
                int lengthOverlap = rs.getInt("length_overlap");
                int sideOverlap = rs.getInt("side_overlap");
                int gapRafters = rs.getInt("gap_rafters");

                Material material = switch (type) {
                    case "post" -> new Post(name,description, costPrice, salesPrice, preCutLengths, unit, width, materialId, height, bucklingCapacity);
                    case "beam" -> new Beam(name, description, costPrice, salesPrice, preCutLengths, unit, width, materialId, height, postGap);
                    case "rafter" -> new Rafter(name, description, costPrice, salesPrice, preCutLengths, unit, width, materialId, height);
                    case "fascia" -> new Fascia(name, description, costPrice, salesPrice, preCutLengths, unit, width, materialId, height);
                    case "roof_cover" -> new RoofCover(name, description, costPrice, salesPrice, preCutLengths, unit, width, materialId, lengthOverlap, sideOverlap, gapRafters);
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

    private static List<Integer> getPreCutLengths(int materialId) throws DatabaseException {
        List<Integer> lengths = new ArrayList<>();

        String sql =
                "SELECT length " +
                "FROM building_materials " +
                "JOIN material_lengths USING (building_material_id) " +
                "JOIN predefined_lengths USING (predefined_length_id);" +
                "WHERE building_material_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, materialId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lengths.add(rs.getInt("length"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e, "Error retrieving materials");
        }

        return lengths;
    }


}
