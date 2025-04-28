package app.entities.materials.planksAndRoofCovers.planks;

public class Beam extends Plank {
    private final int maxLengthBetweenPosts;

    public Beam(String name, String description, double costPrice, double salesPrice, String unit, int length, int width, int height, int maxLengthBetweenPosts) {
        super(name, description, costPrice, salesPrice, unit, length, width, height);
        this.maxLengthBetweenPosts = maxLengthBetweenPosts;
    }

    public int getMaxLengthBetweenPosts() {
        return maxLengthBetweenPosts;
    }
}
