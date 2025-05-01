package app.entities.products.materials.planks;

import java.util.List;

public class Rafter extends Plank {
    public Rafter(String name, String description, double costPrice, double salesPrice, List<Integer> preCutsLengths, String unit, int width, int materialId, int height) {
        super(name, description, costPrice, salesPrice, preCutsLengths, unit, width, materialId, height);
    }
}
