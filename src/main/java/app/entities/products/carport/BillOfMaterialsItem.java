package app.entities.products.carport;

public class BillOfMaterialsItem {
    private final String name;
    private int length;
    private final int amount;
    private final String unit;
    private final String description;

    public BillOfMaterialsItem(String name, int amount, String unit, String desc) {
        this.name = name;
        this.amount = amount;
        this.unit = unit;
        this.description = desc;
    }

    public BillOfMaterialsItem(String name, int length, int amount, String unit, String desc) {
        this.name = name;
        this.length = length;
        this.amount = amount;
        this.unit = unit;
        this.description = desc;
    }

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

    public int getAmount() {
        return amount;
    }

    public String getUnit() {
        return unit;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "BillOfMaterialsLine{" +
                "name='" + name + '\'' +
                ", length=" + length +
                ", amount=" + amount +
                ", unit='" + unit + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
