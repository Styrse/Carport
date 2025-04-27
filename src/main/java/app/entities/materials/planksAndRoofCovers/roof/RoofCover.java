package app.entities.materials.planksAndRoofCovers.roof;

import app.entities.materials.planksAndRoofCovers.PlankAndRoof;

public class RoofCover extends PlankAndRoof {
    private final int lengthOverlap;
    private final float sideOverlap;
    private final int maxLengthBetweenRafters;

    public RoofCover(String name, String description, double costPrice, double salesPrice, String unit, int length, int width, int lengthOverlap, float sideOverlap, int maxLengthBetweenRafters) {
        super(name, description, costPrice, salesPrice, unit, length, width);
        this.lengthOverlap = lengthOverlap;
        this.sideOverlap = sideOverlap;
        this.maxLengthBetweenRafters = maxLengthBetweenRafters;
    }

    public int getLengthOverlap() {
        return lengthOverlap;
    }

    public float getSideOverlap() {
        return sideOverlap;
    }

    public int getMaxLengthBetweenRafters() {
        return maxLengthBetweenRafters;
    }


}
