package app.entities.products;

public abstract class Product {
    private int itemId;
    private String name;
    private String description;
    private float costPrice;
    private float salesPrice;

    public Product() {
    }

    public Product(int itemId) {
        this.itemId = itemId;
    }

    public Product(String name, float salesPrice) {
        this.name = name;
        this.salesPrice = salesPrice;
    }

    public Product(int itemId, String name, String description) {
        this.itemId = itemId;
        this.name = name;
        this.description = description;
    }

    public Product(String name, String description, float costPrice, float salesPrice) {
        this.name = name;
        this.description = description;
        this.costPrice = costPrice;
        this.salesPrice = salesPrice;
    }

    public Product(int itemId, String name, String description, float costPrice, float salesPrice) {
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

    public float getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(float costPrice) {
        this.costPrice = costPrice;
    }

    public float getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(float salesPrice) {
        this.salesPrice = salesPrice;
    }

    public abstract String getItemType();

}
