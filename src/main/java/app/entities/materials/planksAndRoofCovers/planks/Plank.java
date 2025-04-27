package app.entities.materials.planksAndRoofCovers.planks;

import app.entities.materials.planksAndRoofCovers.PlankAndRoof;


public abstract class Plank extends PlankAndRoof {
    private final int height;

    public Plank(String name, String description, double costPrice, double salesPrice, String unit, int length, int width, int maxLength, int height) {
        super(name, description, costPrice, salesPrice, unit, length, width, maxLength);
        this.height = height;
    }

    public int getHeight() {
        return height;
    }
}
