package app.entities;

public abstract class Material {
    private String name;
    private String description;
    private double costPrice;
    private double salesPrice;
    private String unit;

    public Material(String name, String description, double costPrice, double salesPrice, String unit) {
        this.name = name;
        this.description = description;
        this.costPrice = costPrice;
        this.salesPrice = salesPrice;
        this.unit = unit;
    }
}
