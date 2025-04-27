package app.entities;

public class PlankAndRoof extends Material {
    private int length;
    private int width;

    public PlankAndRoof(String name, String description, double costPrice, double salesPrice, String unit, int length, int width) {
        super(name, description, costPrice, salesPrice, unit);
        this.length = length;
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }
}
