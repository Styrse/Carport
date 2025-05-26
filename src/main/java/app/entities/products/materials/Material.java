package app.entities.products.materials;

import app.entities.products.Product;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class Material extends Product {
    private float width;
    private String unit;
    private List<Integer> preCutsLengths;

    public Material(String name, String description, float costPrice, float salesPrice, float width, String unit, List<Integer> preCutsLengths) {
        super(name, description, costPrice, salesPrice);
        this.width = width;
        this.unit = unit;
        this.preCutsLengths = preCutsLengths;
    }

    public Material(int itemId, String name, String description, float costPrice, float salesPrice, String unit, float width) {
        super(itemId, name, description, costPrice, salesPrice);
        this.width = width;
        this.unit = unit;
        this.preCutsLengths = new ArrayList<>();
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public List<Integer> getPreCutLengths() {
        return preCutsLengths;
    }

    public void addToPreCutsLengths(int length) {
        this.preCutsLengths.add(length);
    }

    public void setPreCutsLengths(List<Integer> preCutsLengths) {
        this.preCutsLengths = preCutsLengths;
    }

    @Override
    public String getItemType() {
        return "Material";
    }

    public abstract void prepareStatement(PreparedStatement ps) throws SQLException;
}
