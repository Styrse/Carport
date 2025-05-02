package app.entities.products.materials.planks;

import java.util.List;

public class Post extends Plank {
    private float bucklingCapacity;

    public Post(int itemId, String name, String description, double costPrice, double salesPrice, List<Integer> preCutsLengths, String unit, int width, int height, float bucklingCapacity) {
        super(itemId, name, description, costPrice, salesPrice, preCutsLengths, unit, width, height);
        this.bucklingCapacity = bucklingCapacity;
    }

    public float getBucklingCapacity() {
        return bucklingCapacity;
    }

    public void setBucklingCapacity(float bucklingCapacity) {
        this.bucklingCapacity = bucklingCapacity;
    }
}
