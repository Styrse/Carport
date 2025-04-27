package app.entities.materials;

public class RoofCover extends PlankAndRoof{
    private final int sideOverlap;
    private final int maxLengthBetweenRafters;

    public RoofCover(String name, String description, double costPrice, double salesPrice, String unit, int length, int width, int maxLength, int sideOverlap, int maxLengthBetweenRafters) {
        super(name, description, costPrice, salesPrice, unit, length, width, maxLength);
        this.sideOverlap = sideOverlap;
        this.maxLengthBetweenRafters = maxLengthBetweenRafters;
    }

    public int getSideOverlap() {
        return sideOverlap;
    }

    public int getMaxLengthBetweenRafters() {
        return maxLengthBetweenRafters;
    }
}
