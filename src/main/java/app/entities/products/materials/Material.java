package app.entities.products.materials;

import app.entities.products.Product;

import java.util.List;

public abstract class Material extends Product {
    private int width;
    private String unit;
    private final List<Integer> preCutsLengths;

    public Material(int itemId, String name, String description, double costPrice, double salesPrice, List<Integer> preCutsLengths, String unit, int width) {
        super(itemId, name, description, costPrice, salesPrice);
        this.preCutsLengths = preCutsLengths;
        this.unit = unit;
        this.width = width;
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
}
