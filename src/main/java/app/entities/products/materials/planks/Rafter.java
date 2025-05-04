package app.entities.products.materials.planks;

import java.util.List;

public class Rafter extends Plank {
    public Rafter(int itemId, String name, String description, double costPrice, double salesPrice, List<Integer> preCutsLengths, String unit, int width, int height) {
        super(itemId, name, description, costPrice, salesPrice, preCutsLengths, unit, width, height);
    }
}
