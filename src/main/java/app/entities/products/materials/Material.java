package app.entities.products.materials;

import app.entities.products.Product;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class Material extends Product {
    private int width;
    private String unit;
    private List<Integer> preCutsLengths;

    public Material(int itemId, String name, String description, double costPrice, double salesPrice, String unit, int width) {
        super(itemId, name, description, costPrice, salesPrice);
        this.width = width;
        this.unit = unit;
        this.preCutsLengths = new ArrayList<>();
    }

    @Override
    public String getItemType() {
        return "material";
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public List<Integer> getPreCutsLengths() {
        return preCutsLengths;
    }

    public void addToPreCutsLengths(int length) {
        this.preCutsLengths.add(length);
    }

    public abstract void prepareStatement(PreparedStatement ps) throws SQLException;
}
