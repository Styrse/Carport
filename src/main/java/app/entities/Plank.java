package app.entities;

public class Plank extends PlankAndRoof {
    private int height;

    public Plank(String name, String description, double costPrice, double salesPrice, String unit, int length, int width, int height) {
        super(name, description, costPrice, salesPrice, unit, length, width);
        this.height = height;
    }
}
