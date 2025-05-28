package app.entities.products.materials.planks;

import app.entities.products.materials.Material;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public abstract class Plank extends Material {
    private float height;

    public Plank(String name, String description, float costPrice, float salesPrice, float width, String unit, List<Integer> preCutsLengths, float height) {
        super(name, description, costPrice, salesPrice, width, unit, preCutsLengths);
        this.height = height;
    }

    public Plank(int itemId, String name, String description, float costPrice, float salesPrice, String unit, float width, float height) {
        super(itemId, name, description, costPrice, salesPrice, unit, width);
        this.height = height;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    @Override
    public abstract void prepareStatement(PreparedStatement ps) throws SQLException;
}
