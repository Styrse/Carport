package app.persistence.mappers;

import app.entities.products.materials.Material;
import app.entities.products.materials.MaterialRole;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.entities.products.carport.Carport;

import static app.Main.connectionPool;

public class CarportMapper {

    //Create: Create a new Carport config in the database
    public static void createCarport(Carport carport) throws SQLException {
        String sql = "INSERT INTO carports (width, length, height, roof_type, roof_angle, " +
                "post_material_id, beam_material_id, rafter_material_id, fascia_material_id, total_price) " +
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
            ps.setDouble(10, carport.getBillOfMaterial().calcTotalPrice());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int itemId = rs.getInt("carport_id");
                    carport.setItemId(itemId);
                } else {
                    throw new SQLException("Failed to retrieve carport_id");
                }
            }
        }
    }

    //Read: Get all carports
    public static Carport getCarportById(int carportId) throws DatabaseException {
        String sql =
                "SELECT * " +
                        "FROM carports " +
                        "WHERE carport_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, carportId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapCarport(rs);
                } else {
                    throw new DatabaseException("Carport not found with Id: " + carportId);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Error retrieving carport by id. Carport id: " + carportId);
        }
    }

    public static List<Carport> getAllCarports() throws DatabaseException {
        List<Carport> allCarports = new ArrayList<>();

        String sql = "SELECT * FROM \"carports\"";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    allCarports.add(mapCarport(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e, "Error getting all carports");
        }
        return allCarports;
    }

    //Update
    public static void updateCarport(Connection connection, Carport carport) throws DatabaseException {
        String sql = "UPDATE carports " +
                "SET width = ?, length = ?, height = ?, " +
                "roof_type = ?, roof_angle = ?, post_material_id = ?, beam_material_id = ?, " +
                "rafter_material_id = ?, fascia_material_id = ?, total_price = ? " +
                "WHERE carport_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            // Extract material IDs safely
            int postId = getMaterialId(carport, MaterialRole.POST);
            int beamId = getMaterialId(carport, MaterialRole.BEAM);
            int rafterId = getMaterialId(carport, MaterialRole.RAFTER);
            int fasciaId = getMaterialId(carport, MaterialRole.FASCIA);

            double totalPrice = carport.getBillOfMaterial().calcTotalPrice();

            ps.setInt(1, carport.getWidth());
            ps.setInt(2, carport.getLength());
            ps.setInt(3, carport.getHeight());
            ps.setString(4, carport.getRoofType());
            ps.setInt(5, carport.getRoofAngle());
            ps.setInt(6, postId);
            ps.setInt(7, beamId);
            ps.setInt(8, rafterId);
            ps.setInt(9, fasciaId);
            ps.setDouble(10, totalPrice);
            ps.setInt(11, carport.getItemId());

            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated == 0) {
                throw new DatabaseException("Carport update affected 0 rows (check ID " + carport.getItemId() + ")");
            }

        } catch (SQLException e) {
            throw new DatabaseException(e, "Error updating carport (ID: " + carport.getItemId() + ")");
        }
    }

    private static int getMaterialId(Carport carport, MaterialRole role) throws DatabaseException {
        Material material = carport.getMaterial().get(role);
        if (material == null) {
            throw new DatabaseException("Missing material for role: " + role);
        }
        return material.getItemId();
    }



    //Delete
    //Could implement a soft delete for accounting purposes later
    public static void deleteCarportById(int carportId) throws DatabaseException {
        String sql = "DELETE FROM carports WHERE carport_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, carportId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException(e, "Error deleting carport. Carport id: " + carportId);
        }
    }

    //Helper: Carport mapper
    public static Carport mapCarport(ResultSet rs) throws SQLException, DatabaseException {
        int carportId = rs.getInt("carport_id");
        int width = rs.getInt("width");
        int length = rs.getInt("length");
        int height = rs.getInt("height");
        String roofType = rs.getString("roof_type");
        int roofAngle = rs.getInt("roof_angle");

        int postMaterialId = rs.getInt("post_material_id");
        int beamMaterialId = rs.getInt("beam_material_id");
        int rafterMaterialId = rs.getInt("rafter_material_id");
        int fasciaMaterialId = rs.getInt("fascia_material_id");
        int roofCoverMaterialId = rs.getInt("roof_cover_material_id");

        Map<MaterialRole, Material> materials = new HashMap<>();

        materials.put(MaterialRole.POST, MaterialMapper.getMaterialById(postMaterialId));
        materials.put(MaterialRole.BEAM, MaterialMapper.getMaterialById(beamMaterialId));
        materials.put(MaterialRole.RAFTER, MaterialMapper.getMaterialById(rafterMaterialId));
        materials.put(MaterialRole.FASCIA, MaterialMapper.getMaterialById(fasciaMaterialId));
        materials.put(MaterialRole.ROOF_COVER, MaterialMapper.getMaterialById(roofCoverMaterialId));

        return switch (roofType) {
            case "flat" -> new Carport(carportId, "Carport", width + " x " + length, width, length, height, roofType, materials);
            case "pitched" -> new Carport(carportId, "Carport", width + " x " + length, width, length, height, roofType, roofAngle, materials);
            default -> throw new IllegalStateException("Unexpected value: " + roofType);
        };
    }

}
