package app.persistence.mappers;

import app.entities.carport.Carport;
import app.entities.materials.planksAndRoofCovers.planks.Beam;
import app.entities.materials.planksAndRoofCovers.planks.Fascia;
import app.entities.materials.planksAndRoofCovers.planks.Post;
import app.entities.materials.planksAndRoofCovers.planks.Rafter;
import app.entities.materials.planksAndRoofCovers.roof.RoofCover;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import javassist.convert.TransformBefore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarportMapper {

    public static List<Carport> getCarports(ConnectionPool connectionPool) throws DatabaseException {
        List<Carport> allCarports = new ArrayList<>();

        String sql = "SELECT * FROM \"carports\"";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {

                ResultSet rs = ps.executeQuery();

                while(rs.next()) {
                    int subProductId = rs.getInt("sub_product_id");
                    int productId = rs.getString("product_id");
                    int width = rs.getString("width");
                    int length = rs.getInt("length");
                    int height = rs.getInt("height");
                    String roofType = rs.getString("roof_type");
                    String roofAngle = rs.getString("roof_angle");
                    int postSubProductId = rs.getInt("post_sub_product_id");
                    int postColorId = rs.getInt("post_color_id");
                    int beamSubProductId = rs.getInt("beam_sub_product_id");
                    int beamColorId = rs.getInt("beam_color_id");
                    int rafterSubProductId = rs.getInt("rafter_sub_product_id");
                    int rafterColorId = rs.getInt("rafter_color_id");
                    int fasciaSubProductId = rs.getInt("fascia_sub_product_id");
                    int fasciaColorId = rs.getInt("fascia_color_id");
                    int roofCoverSubProductId = rs.getInt("roof_cover_sub_product_id");
                    int roofCoverColorId = rs.getInt("roof_cover_color_id");


                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Error");
        }
        return allCarports;
    }

    public static Carport getCarportBySubproductID(List<Carport> allCarports, int subProductId) {
        for (Carport carport : allCarports) {
            if (carport.getSubProductId() == subProductId) {
                return carport;
            }
        }
        return null; // or throw exception if not found
    }



}
