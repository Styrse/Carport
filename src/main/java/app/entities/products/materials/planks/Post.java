package app.entities.products.materials.planks;

public class Post extends Plank {
    private boolean treatedForGroundContact;

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
}
