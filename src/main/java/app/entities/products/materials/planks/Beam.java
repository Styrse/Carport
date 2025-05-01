package app.entities.products.materials.planks;

public class Beam extends Plank {
    private int maxLengthBetweenPosts;

    public Beam(String name, String description, double costPrice, double salesPrice, int materialId, int length, int width, int maxLength, String unit, int height, int maxLengthBetweenPosts) {
        super(name, description, costPrice, salesPrice, materialId, length, width, maxLength, unit, height);
        this.maxLengthBetweenPosts = maxLengthBetweenPosts;
    }

    public int getMaxLengthBetweenPosts() {
        return maxLengthBetweenPosts;
    }

    public void setMaxLengthBetweenPosts(int maxLengthBetweenPosts) {
        this.maxLengthBetweenPosts = maxLengthBetweenPosts;
    }
}
