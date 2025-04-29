package app.entities.products.subProducts.materials.planks;

public class Fascia extends Plank {
    private final boolean supportsGutters;

    public static int MIN_THICKNESS_FOR_GUTTERS = 22;

    public Fascia(int productID, String name, String description, double costPrice, double salesPrice, int subProductID, int length, int width, int maxLength, String unit, int height, boolean supportsGutters) {
        super(productID, name, description, costPrice, salesPrice, subProductID, length, width, maxLength, unit, height);
        this.supportsGutters = supportsGutters;
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
