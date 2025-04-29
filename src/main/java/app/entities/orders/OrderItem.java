package app.entities.orders;

import app.entities.products.Product;

public class OrderItem {
    private int productId;
    private int subProductId;
    private String itemName;
    private int quantity;

    public OrderItem(int productId, int subProductId, String itemName, int quantity) {
        this.productId = productId;
        this.subProductId = subProductId;
        this.itemName = itemName;
        this.quantity = quantity;
    }

    public OrderItem(Product product, int quantity) {
        this.productId = product.getSubProductID();
        this.subProductId = product.getProductID();
        this.itemName = product.getName();
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
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
