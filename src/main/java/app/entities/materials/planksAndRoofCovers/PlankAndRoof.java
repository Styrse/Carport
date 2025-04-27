package app.entities.materials.planksAndRoofCovers;

import app.entities.materials.Material;

public abstract class PlankAndRoof extends Material {
    private final int length;
    private final int width;
    private final int maxLength;

    public PlankAndRoof(String name, String description, double costPrice, double salesPrice, String unit, int length, int width, int maxLength) {
        super(name, description, costPrice, salesPrice, unit);
        this.length = length;
        this.width = width;
        this.maxLength = maxLength;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public int getMaxLength() {
        return maxLength;
    }
}
