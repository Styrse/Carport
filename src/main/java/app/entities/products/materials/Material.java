package app.entities.products.materials;

import app.entities.products.Product;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class Material extends Product {
    private int length;
    private int width;
    private int maxLength;
    private String unit;

    //TODO: Make it specific to each material - DB table(predefined_lengths)
    public static final List<Integer> PREDEFINED_LENGTHS = Arrays.asList(300, 330, 360, 390, 420, 450, 480, 510, 540, 570, 600);

    public Material(int productID, int subProductID, String name, String description, double costPrice, double salesPrice, int length, int width, int maxLength, String unit) {
        super(productID, subProductID, name, description, costPrice, salesPrice);
        this.length = length;
        this.width = width;
        this.maxLength = maxLength;
        this.unit = unit;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
