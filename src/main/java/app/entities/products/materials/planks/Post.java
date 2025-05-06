package app.entities.products.materials.planks;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

public class Post extends Plank {
    private float bucklingCapacity;

    public Post(int itemId, String name, String description, double costPrice, double salesPrice, String unit, int width, int height, float bucklingCapacity) {
        super(itemId, name, description, costPrice, salesPrice, unit, width, height);
        this.bucklingCapacity = bucklingCapacity;
    }

    public float getBucklingCapacity() {
        return bucklingCapacity;
    }

    public void setBucklingCapacity(float bucklingCapacity) {
        this.bucklingCapacity = bucklingCapacity;
    }

    @Override
    public void prepareStatement(PreparedStatement ps) throws SQLException {
        ps.setString(1, this.getName());
        ps.setString(2, this.getDescription());
        ps.setString(3, this.getUnit());
        ps.setInt(4, this.getWidth());
        ps.setInt( 5, this.getHeight());
        ps.setString(6, this.getClass().getSimpleName());
        ps.setFloat(7, this.getBucklingCapacity());
        ps.setNull(8, Types.NUMERIC);
        ps.setNull(9, Types.NUMERIC);
        ps.setNull(10, Types.NUMERIC);
        ps.setNull(11, Types.NUMERIC);
    }
}
