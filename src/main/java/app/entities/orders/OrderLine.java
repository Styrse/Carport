package app.entities.orders;

import app.entities.products.Product;

public class OrderLine {
    private int productID;
    private Product product;
    private int quantity;

    public OrderLine(Product product, int quantity) {
        this.productID = product.getProductID();
        this.product = product;
        this.quantity = quantity;
    }

    public int getProductID() {
        return productID;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
}
