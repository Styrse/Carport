package app.entities.carport;

import app.entities.materials.Plank;
import app.entities.materials.RoofCover;

public class Carport {
    private final int width;
    private final int length;
    private final int height;
    private final String roofType;
    private int roofAngle;

    private final Plank postMaterial;
    private final Plank beamMaterial;
    private final Plank rafterMaterial;
    private final Plank fasciaMaterial;
    private final RoofCover roofingMaterial;

    public Carport(int width, int length, int height, String roofType,
                   Plank postMaterial, Plank beamMaterial, Plank rafterMaterial,
                   Plank fasciaMaterial, RoofCover roofingMaterial) {
        this.width = width;
        this.length = length;
        this.height = height;
        this.roofType = roofType;
        this.postMaterial = postMaterial;
        this.beamMaterial = beamMaterial;
        this.rafterMaterial = rafterMaterial;
        this.fasciaMaterial = fasciaMaterial;
        this.roofingMaterial = roofingMaterial;
    }

    public Carport(int width, int length, int height, String roofType,
                   Plank postMaterial, Plank beamMaterial, Plank rafterMaterial,
                   Plank fasciaMaterial, RoofCover roofingMaterial, int roofAngle) {
        this(width, length, height, roofType, postMaterial, beamMaterial, rafterMaterial, fasciaMaterial, roofingMaterial);
        this.roofAngle = roofAngle;
    }

    // Generate fresh BillOfMaterial every time
    public BillOfMaterial getBillOfMaterial() {
        return new BillOfMaterial(this);
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }

    public int getHeight() {
        return height;
    }

    public String getRoofType() {
        return roofType;
    }

    public int getRoofAngle() {
        return roofAngle;
    }

    public Plank getPostMaterial() {
        return postMaterial;
    }

    public Plank getBeamMaterial() {
        return beamMaterial;
    }

    public Plank getRafterMaterial() {
        return rafterMaterial;
    }

    public Plank getFasciaMaterial() {
        return fasciaMaterial;
    }

    public RoofCover getRoofingMaterial() {
        return roofingMaterial;
    }
}
