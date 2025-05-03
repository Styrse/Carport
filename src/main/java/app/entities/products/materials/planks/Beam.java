package app.entities.products.materials.planks;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

public class Beam extends Plank {
    private int postGap;

    public Beam(int itemId, String name, String description, double costPrice, double salesPrice, List<Integer> preCutsLengths, String unit, int width, int height, int postGap) {
        super(itemId, name, description, costPrice, salesPrice, preCutsLengths, unit, width, height);
        this.postGap = postGap;
    }

    public int getPostGap() {
        return postGap;
    }

    public void setPostGap(int postGap) {
        this.postGap = postGap;
    }

    @Override
    public void prepareStatement(PreparedStatement ps) throws SQLException {
        ps.setString(1, this.getName());
        ps.setString(2, this.getDescription());
        ps.setString(3, this.getUnit());
        ps.setInt(4, this.getWidth());
        ps.setInt( 5, this.getHeight());
        ps.setString(6, this.getClass().getSimpleName());
        ps.setNull(7, Types.NUMERIC);
        ps.setInt(8, this.getPostGap());
        ps.setNull(9, Types.NUMERIC);
        ps.setNull(10, Types.NUMERIC);
        ps.setNull(11, Types.NUMERIC);
    }
}
