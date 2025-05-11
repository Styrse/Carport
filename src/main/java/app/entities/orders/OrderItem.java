package app.entities.orders;

import app.entities.products.Product;

public class OrderItem {
    private Product product;
    private String itemName;
    private int quantity;

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.itemName = product.getName();
        this.quantity = quantity;
    }



    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getTotalPrice() {
        return product.getSalesPrice() * quantity;
    }
}
