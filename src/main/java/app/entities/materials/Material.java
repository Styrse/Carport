package app.entities.materials;

public abstract class Material {
    private final String name;
    private final String description;
    private double costPrice;
    private double salesPrice;
    private final String unit;

    public Material(String name, String description, double costPrice, double salesPrice, String unit) {
        this.name = name;
        this.description = description;
        this.costPrice = costPrice;
        this.salesPrice = salesPrice;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public double getSalesPrice() {
        return salesPrice;
    }

    public String getUnit() {
        return unit;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public void setSalesPrice(double salesPrice) {
        this.salesPrice = salesPrice;
    }
}
