package app.entities.materials;

public class Plank extends PlankAndRoof {
    private final int height;
    private final int maxLengthBetweenPosts;

    public Plank(String name, String description, double costPrice, double salesPrice, String unit, int length, int width, int maxLength, int height, int maxLengthBetweenPosts) {
        super(name, description, costPrice, salesPrice, unit, length, width, maxLength);
        this.height = height;
        this.maxLengthBetweenPosts = maxLengthBetweenPosts;
    }

    public int getHeight() {
        return height;
    }

    public int getMaxLengthBetweenPosts() {
        return maxLengthBetweenPosts;
    }
}
