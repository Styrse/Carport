package app.entities.products.materials.planks;

import java.util.List;

public class Fascia extends Plank {
    private final boolean supportsGutters;

    public static int MIN_THICKNESS_FOR_GUTTERS = 22;

    public Fascia(int itemId, String name, String description, double costPrice, double salesPrice, List<Integer> preCutsLengths, String unit, int width, int height) {
        super(itemId, name, description, costPrice, salesPrice, preCutsLengths, unit, width, height);
        this.supportsGutters = determineSupportsGutters();
    }

    private boolean determineSupportsGutters(){
        return getWidth() >= MIN_THICKNESS_FOR_GUTTERS;
    }

    public boolean isSupportsGutters() {
        return supportsGutters;
    }

    public void setMinThicknessForGutters(int minThickness) {
        MIN_THICKNESS_FOR_GUTTERS = minThickness;
        this.determineSupportsGutters();
    }
}
