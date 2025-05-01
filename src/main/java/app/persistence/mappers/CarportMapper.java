package app.persistence.mappers;

import app.entities.products.materials.MaterialRole;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.entities.products.carport.Carport;

import static app.Main.connectionPool;

public class CarportMapper {

    //Create
    public static int createCarport(Carport carport) throws SQLException {
        String sql = "INSERT INTO carports (width, length, height, roof_type, roof_angle, " +
                "post_building_material_id, beam_building_material_id, rafter_building_material_id, fascia_building_material_id, total_price) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING carport_id";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setFloat(1, carport.getWidth());
            ps.setFloat(2, carport.getLength());
            ps.setFloat(3, carport.getHeight());
            ps.setString(4, carport.getRoofType());
            ps.setFloat(5, carport.getRoofAngle());
            ps.setInt(6, carport.getMaterial().get(MaterialRole.POST).getItemId());
            ps.setInt(7, carport.getMaterial().get(MaterialRole.BEAM).getItemId());
            ps.setInt(8, carport.getMaterial().get(MaterialRole.RAFTER).getItemId());
            ps.setInt(9, carport.getMaterial().get(MaterialRole.FASCIA).getItemId());
            ps.setFloat(10, carport.getBillOfMaterial().calcTotalPrice());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("carport_id");
                } else {
                    throw new SQLException("Failed to retrieve carport_id");
                }
            }
        }
    }


    public static List<Carport> getCarports(ConnectionPool connectionPool) throws DatabaseException {
        List<Carport> allCarports = new ArrayList<>();

        String sql = "SELECT * FROM \"carports\"";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {

                ResultSet rs = ps.executeQuery();

                while(rs.next()) {
                    int width = rs.getInt("width");
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
            throw new DatabaseException(e, "Error");
        }
        return allCarports;
    }

    public static Carport getCarportById(List<Carport> allCarports, int carportId) {
        for (Carport carport : allCarports) {
            if (carport.getCarportId() == carportId) {
                return carport;
            }
        }
        return null; // or throw exception if not found
    }
}
