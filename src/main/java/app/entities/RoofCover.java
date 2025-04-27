package app.entities;

public class RoofCover extends PlankAndRoof{
    private int sideOverlap;

    public RoofCover(String name, String description, double costPrice, double salesPrice, String unit, int length, int width, int sideOverlap) {
        super(name, description, costPrice, salesPrice, unit, length, width);
        this.sideOverlap = sideOverlap;
    }
}
