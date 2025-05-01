package app.entities.products.materials.roof;

import app.entities.products.materials.Material;

import java.util.List;

public class RoofCover extends Material {
    private int lengthOverlap;
    private float sideOverlap;
    private int gapRafters;

    public RoofCover(int itemId, String name, String description, double costPrice, double salesPrice, List<Integer> preCutsLengths, String unit, int width, int lengthOverlap, float sideOverlap, int gapRafters) {
        super(itemId, name, description, costPrice, salesPrice, preCutsLengths, unit, width);
        this.lengthOverlap = lengthOverlap;
        this.sideOverlap = sideOverlap;
        this.gapRafters = gapRafters;
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

    public int getGapRafters() {
        return gapRafters;
    }

    public void setGapRafters(int gapRafters) {
        this.gapRafters = gapRafters;
    }
}
