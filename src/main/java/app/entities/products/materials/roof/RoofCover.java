package app.entities.products.materials.roof;

import app.entities.products.materials.Material;

public class RoofCover extends Material {
    private int lengthOverlap;
    private float sideOverlap;
    private int maxLengthBetweenRafters;

    public RoofCover(String name, String description, double costPrice, double salesPrice, int materialId, int length, int width, int maxLength, String unit, int lengthOverlap, float sideOverlap, int maxLengthBetweenRafters) {
        super(name, description, costPrice, salesPrice, materialId, length, width, maxLength, unit);
        this.lengthOverlap = lengthOverlap;
        this.sideOverlap = sideOverlap;
        this.maxLengthBetweenRafters = maxLengthBetweenRafters;
    }

    public int getLengthOverlap() {
        return lengthOverlap;
    }

    public void setLengthOverlap(int lengthOverlap) {
        this.lengthOverlap = lengthOverlap;
    }

    public float getSideOverlap() {
        return sideOverlap;
    }

    public void setSideOverlap(float sideOverlap) {
        this.sideOverlap = sideOverlap;
    }

    public int getMaxLengthBetweenRafters() {
        return maxLengthBetweenRafters;
    }

    public void setMaxLengthBetweenRafters(int maxLengthBetweenRafters) {
        this.maxLengthBetweenRafters = maxLengthBetweenRafters;
    }
}
