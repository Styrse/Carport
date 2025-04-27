package app.entities.materials.planksAndRoofCovers.planks;

public class Post extends Plank {
    private final boolean treatedForGroundContact;

    public Post(String name, String description, double costPrice, double salesPrice, String unit, int length, int width, int maxLength, int height, boolean treatedForGroundContact) {
        super(name, description, costPrice, salesPrice, unit, length, width, maxLength, height);
        this.treatedForGroundContact = treatedForGroundContact;
    }

    public boolean isTreatedForGroundContact() {
        return treatedForGroundContact;
    }
}
