package app.entities.orders;

import app.entities.products.Product;

public class Shipping extends Product {

    public static int SHIPPING_DK_POSTCODE_LESS_5000 = 2000;
    public static int SHIPPING_DK_REST = 3000;
    public static int SHIPPING_INTERNATIONALLY = 6000;

    public Shipping(String name, float salesPrice) {
        super(name, salesPrice);
    }

    @Override
    public String getItemType() {
        return "Fragt";
    }
}
