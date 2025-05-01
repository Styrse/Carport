package app.entities.products.materials.planks;

public class Fascia extends Plank {
    private final boolean supportsGutters;

    public static int MIN_THICKNESS_FOR_GUTTERS = 22;

    public Fascia(int productID, int subProductID, String name, String description, double costPrice, double salesPrice, int length, int width, int maxLength, String unit, int height) {
        super(productID, subProductID, name, description, costPrice, salesPrice, length, width, maxLength, unit, height);
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
