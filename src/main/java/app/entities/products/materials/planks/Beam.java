package app.entities.products.materials.planks;

import java.util.List;

public class Beam extends Plank {
    private int postGap;

    public Beam(int itemId, String name, String description, double costPrice, double salesPrice, List<Integer> preCutsLengths, String unit, int width, int height, int postGap) {
        super(itemId, name, description, costPrice, salesPrice, preCutsLengths, unit, width, height);
        this.postGap = postGap;
    }

    public int getPostGap() {
        return postGap;
    }

    public void setPostGap(int postGap) {
        this.postGap = postGap;
    }
}
