package app.entities.orders;

import app.entities.products.Product;

public class OrderItem {
    private int productID;
    private int subProductId;
    private String itemName;
    private int quantity;

    public OrderItem(Product product, int quantity) {
        this.productID = product.getSubProductID();
        this.subProductId = product.getProductID();
        this.itemName = product.getName();
        this.quantity = quantity;
    }

    public int getProductID() {
        return productID;
    }

    public int getSubProductId() {
        return subProductId;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }
}
