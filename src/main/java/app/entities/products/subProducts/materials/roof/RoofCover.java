package app.entities.products.subProducts.materials.roof;

import app.entities.products.subProducts.materials.Material;

public class RoofCover extends Material {
    private int lengthOverlap;
    private float sideOverlap;
    private int maxLengthBetweenRafters;

    public RoofCover(int productID, String name, String description, double costPrice, double salesPrice, int subProductID, int length, int width, int maxLength, String unit, int lengthOverlap, float sideOverlap, int maxLengthBetweenRafters) {
        super(productID, name, description, costPrice, salesPrice, subProductID, length, width, maxLength, unit);
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
