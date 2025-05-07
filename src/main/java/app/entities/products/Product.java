package app.entities.products;

public abstract class Product {
    private int itemId;
    private String name;
    private String description;
    private double costPrice;
    private double salesPrice;

    public Product() {
    }

    public Product(int itemId, String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Product(String name, String description, double costPrice, double salesPrice) {
        this.name = name;
        this.description = description;
        this.costPrice = costPrice;
        this.salesPrice = salesPrice;
    }

    public Product(int itemId, String name, String description, double costPrice, double salesPrice) {
        this.itemId = itemId;
        this.name = name;
        this.description = description;
        this.costPrice = costPrice;
        this.salesPrice = salesPrice;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public abstract String getItemType();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public double getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(double salesPrice) {
        this.salesPrice = salesPrice;
    }
}
