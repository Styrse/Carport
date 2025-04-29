package app.entities.products.subProducts.materials.planks;

public class Beam extends Plank {
    private int maxLengthBetweenPosts;

    public Beam(int productID, String name, String description, double costPrice, double salesPrice, int subProductID, int length, int width, int maxLength, String unit, int height, int maxLengthBetweenPosts) {
        super(productID, name, description, costPrice, salesPrice, subProductID, length, width, maxLength, unit, height);
        this.maxLengthBetweenPosts = maxLengthBetweenPosts;
    }

    public int getMaxLengthBetweenPosts() {
        return maxLengthBetweenPosts;
    }

    public void setMaxLengthBetweenPosts(int maxLengthBetweenPosts) {
        this.maxLengthBetweenPosts = maxLengthBetweenPosts;
    }
}
