package app.entities.products.materials.planks;

import java.util.List;

public class Post extends Plank {
    private float bucklingCapacity;

    public Post(String name, String description, double costPrice, double salesPrice, List<Integer> preCutsLengths, String unit, int width, int materialId, int height, float bucklingCapacity) {
        super(name, description, costPrice, salesPrice, preCutsLengths, unit, width, materialId, height);
        this.bucklingCapacity = bucklingCapacity;
    }

    public float getBucklingCapacity() {
        return bucklingCapacity;
    }

    public void setBucklingCapacity(float bucklingCapacity) {
        this.bucklingCapacity = bucklingCapacity;
    }
}
