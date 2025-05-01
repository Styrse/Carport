package app.entities.products.materials.planks;

public class Post extends Plank {
    private float bucklingCapacity;

    public Post(String name, String description, double costPrice, double salesPrice, int materialId, int length, int width, int maxLength, String unit, int height, float bucklingCapacity) {
        super(name, description, costPrice, salesPrice, materialId, length, width, maxLength, unit, height);
        this.bucklingCapacity = bucklingCapacity;
    }

    public float getBucklingCapacity() {
        return bucklingCapacity;
    }

    public void setBucklingCapacity(float bucklingCapacity) {
        this.bucklingCapacity = bucklingCapacity;
    }
}
