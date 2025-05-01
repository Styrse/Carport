package app.entities.products;

public abstract class Product {
    private int productID;
    private int subProductID;
    private String name;
    private String description;
    private double costPrice;
    private double salesPrice;

    public Product() {
    }

    public Product(int productID, int subProductID, String name, String description, double costPrice, double salesPrice) {
        this.productID = productID;
        this.subProductID = subProductID;
        this.name = name;
        this.description = description;
        this.costPrice = costPrice;
        this.salesPrice = salesPrice;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getSubProductID() {
        return subProductID;
    }

    public void setSubProductID(int subProductID) {
        this.subProductID = subProductID;
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
