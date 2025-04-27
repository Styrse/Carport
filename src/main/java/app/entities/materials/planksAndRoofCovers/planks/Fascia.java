package app.entities.materials.planksAndRoofCovers.planks;

public class Fascia extends Plank {
    private final boolean supportsGutters;

    public static final int MIN_THICKNESS_FOR_GUTTERS = 22;

    public Fascia(String name, String description, double costPrice, double salesPrice, String unit, int length, int width, int maxLength, int height) {
        super(name, description, costPrice, salesPrice, unit, length, width, maxLength, height);
        this.supportsGutters = determineSupportsGutters();
    }

    private boolean determineSupportsGutters(){
        return getWidth() >= MIN_THICKNESS_FOR_GUTTERS;
    }

    public boolean isSupportsGutters() {
        return supportsGutters;
    }
}
