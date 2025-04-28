package app.entities.materials.planksAndRoofCovers;

import app.entities.materials.Material;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class PlankAndRoof extends Material {
    private final int length;
    private final int width;
    private int maxLength;

    //TODO: Make it specific to each material
    public static final List<Integer> PREDEFINED_LENGTHS = Arrays.asList(300, 330, 360, 390, 420, 450, 480, 510, 540, 570, 600);

    public PlankAndRoof(String name, String description, double costPrice, double salesPrice, String unit, int length, int width) {
        super(name, description, costPrice, salesPrice, unit);
        this.length = length;
        this.width = width;
        this.maxLength = Collections.max(PREDEFINED_LENGTHS); //Using the Collections class and its built-in max() method
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }
}
