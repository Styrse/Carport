package app.entities.products.carport;

public class BillOfMaterialsItem {
    private final String name;
    private final int length;
    private final int quantity;
    private final String unit;
    private final String description;
    private final double salesPrice;

    public BillOfMaterialsItem(String name, int length, int quantity, String unit, String description, double salesPrice) {
        this.name = name;
        this.length = length;
        this.quantity = quantity;
        this.unit = unit;
        this.description = description;
        this.salesPrice = salesPrice;
    }

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

    public String getDescription() {
        return description;
    }

    public double getSalesPrice() {
        return salesPrice;
    }

    @Override
    public String toString() {
        return "BillOfMaterialsLine{" +
                "name='" + name + '\'' +
                ", length=" + length +
                ", amount=" + quantity +
                ", unit='" + unit + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
