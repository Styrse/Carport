package app.entities;

public class Plank extends Material{
    private int length;
    private int width;
    private int height;

    public Plank(String name, String description, double costPrice, double salesPrice, String unit, int length, int width, int height) {
        super(name, description, costPrice, salesPrice, unit);
        this.length = length;
        this.width = width;
        this.height = height;
    }
}
