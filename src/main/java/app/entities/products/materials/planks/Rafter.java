package app.entities.products.materials.planks;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

public class Rafter extends Plank {

    public Rafter(String name, String description, float costPrice, float salesPrice, float width, String unit, List<Integer> preCutsLengths, int height) {
        super(name, description, costPrice, salesPrice, width, unit, preCutsLengths, height);
    }

    public Rafter(int itemId, String name, String description, float costPrice, float salesPrice, String unit, float width, int height) {
        super(itemId, name, description, costPrice, salesPrice, unit, width, height);
    }

    @Override
    public void prepareStatement(PreparedStatement ps) throws SQLException {
        ps.setString(1, this.getName());
        ps.setString(2, this.getDescription());
        ps.setString(3, this.getUnit());
        ps.setFloat(4, this.getWidth());
        ps.setInt( 5, this.getHeight());
        ps.setString(6, this.getClass().getSimpleName());
        ps.setNull(7, Types.NUMERIC);
        ps.setNull(8, Types.NUMERIC);
        ps.setNull(9, Types.NUMERIC);
        ps.setNull(10, Types.NUMERIC);
        ps.setNull(11, Types.NUMERIC);
    }
}
