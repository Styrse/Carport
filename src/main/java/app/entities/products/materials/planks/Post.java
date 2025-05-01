package app.entities.products.materials.planks;

public class Post extends Plank {
    private boolean treatedForGroundContact;
    private float bucklingCapacity;

    public Post(int productID, int subProductID, String name, String description, double costPrice, double salesPrice, int length, int width, int maxLength, String unit, int height, boolean treatedForGroundContact, float bucklingCapacity) {
        super(productID, subProductID, name, description, costPrice, salesPrice, length, width, maxLength, unit, height);
        this.treatedForGroundContact = treatedForGroundContact;
        this.bucklingCapacity = bucklingCapacity;
    }

    public Post(int productID, int subProductID, String name, String description, double costPrice, double salesPrice, int length, int width, int maxLength, String unit, int height, boolean treatedForGroundContact) {
        super(productID, subProductID, name, description, costPrice, salesPrice, length, width, maxLength, unit, height);
        this.treatedForGroundContact = treatedForGroundContact;
    }

    public boolean isTreatedForGroundContact() {
        return treatedForGroundContact;
    }

    public void setTreatedForGroundContact(boolean treatedForGroundContact) {
        this.treatedForGroundContact = treatedForGroundContact;
    }

    public float getBucklingCapacity() {
        return bucklingCapacity;
    }

    public void setBucklingCapacity(float bucklingCapacity) {
        this.bucklingCapacity = bucklingCapacity;
    }
}
