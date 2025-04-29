package app.entities.products.subProducts.materials.planks;

public class Post extends Plank {
    private boolean treatedForGroundContact;

    public Post(int productID, String name, String description, double costPrice, double salesPrice, int subProductID, int length, int width, int maxLength, String unit, int height, boolean treatedForGroundContact) {
        super(productID, name, description, costPrice, salesPrice, subProductID, length, width, maxLength, unit, height);
        this.treatedForGroundContact = treatedForGroundContact;
    }

    public boolean isTreatedForGroundContact() {
        return treatedForGroundContact;
    }

    public void setTreatedForGroundContact(boolean treatedForGroundContact) {
        this.treatedForGroundContact = treatedForGroundContact;
    }
}
