package app.entities.products.subProducts;

import app.entities.products.Product;

public abstract class SubProduct extends Product {
    private int subProductID;

    public SubProduct() {
    }

    public SubProduct(int productID, String name, String description, double costPrice, double salesPrice, int subProductID) {
        super(productID, name, description, costPrice, salesPrice);
        this.subProductID = subProductID;
    }

    public int getSubProductID() {
        return subProductID;
    }

    public void setSubProductID(int subProductID) {
        this.subProductID = subProductID;
    }
}
