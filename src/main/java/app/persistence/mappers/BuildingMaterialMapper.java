package app.persistence.mappers;

import app.entities.materials.Material;
import app.entities.materials.planksAndRoofCovers.planks.Beam;
import app.entities.materials.planksAndRoofCovers.planks.Fascia;
import app.entities.materials.planksAndRoofCovers.planks.Post;
import app.entities.materials.planksAndRoofCovers.planks.Rafter;
import app.entities.materials.planksAndRoofCovers.roof.RoofCover;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BuildingMaterialMapper {

    public static List<Material> getMaterials(ConnectionPool connectionPool) throws DatabaseException {
        List<Material> allMaterials = new ArrayList<>();

        String sql = "SELECT * FROM building_materials";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int subProductId = rs.getInt("sub_product_id");
                int productId = rs.getInt("product_id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                int width = rs.getInt("width");
                int height = rs.getInt("height");
                double costPrice = rs.getDouble("cost_price");
                double salesPrice = rs.getDouble("sales_price");
                String type = rs.getString("material_type");
                boolean treated = rs.getBoolean("treated_for_ground_contact");
                int maxPostGap = rs.getInt("max_length_between_posts");
                boolean gutterSupport = rs.getBoolean("support_gutter");


                Material material = switch (type) {
                    case "post" -> new Post( name,  description,costPrice, salesPrice,
                    unit, length, width,  height, treated);

                    case "beam" -> new Beam(name, description,  costPrice, salesPrice,
                     unit,length, width, height, maxPostGap);

                    case "rafter" -> new Rafter(name, description, costPrice, salesPrice,unit, length, width, height);

                    case "fascia" -> new Fascia(name,description,costPrice,salesPrice, unit, length, width,height);

                    case "roof_cover" -> new RoofCover(name,description,costPrice, salesPrice,unit,length,
                    width, lengthOverlap,sideOverlap, maxLengthBetweenRafters);

                    default -> throw new DatabaseException("Unknown material type: " + type);
                };


                allMaterials.add(material);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Error retrieving materials", e);
        }

        return allMaterials;
    }





}
