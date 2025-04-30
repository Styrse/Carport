package app.entities.products.materials.planks;

public class Beam extends Plank {
    private int maxLengthBetweenPosts;

    public Beam(int productID, int subProductID, String name, String description, double costPrice, double salesPrice, int length, int width, int maxLength, String unit, int height, int maxLengthBetweenPosts) {
        super(productID, subProductID, name, description, costPrice, salesPrice, length, width, maxLength, unit, height);
        this.maxLengthBetweenPosts = maxLengthBetweenPosts;
    }

    public int getMaxLengthBetweenPosts() {
        return maxLengthBetweenPosts;
    }

    public void setMaxLengthBetweenPosts(int maxLengthBetweenPosts) {
        this.maxLengthBetweenPosts = maxLengthBetweenPosts;
    }
}
