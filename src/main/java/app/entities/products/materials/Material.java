package app.entities.products.materials;

import app.entities.products.Product;

import java.util.List;

public abstract class Material extends Product {
    private int materialId;
    private int width;
    private String unit;
    private final List<Integer> preCutsLengths;

    public Material(String name, String description, double costPrice, double salesPrice, List<Integer> preCutsLengths, String unit, int width, int materialId) {
        super(name, description, costPrice, salesPrice);
        this.preCutsLengths = preCutsLengths;
        this.unit = unit;
        this.width = width;
        this.materialId = materialId;
    }

    public int getMaterialId() {
        return materialId;
    }

    public int getWidth() {
        return width;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
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
