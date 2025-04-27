package app.entities.carport;

import app.entities.materials.planksAndRoofCovers.planks.*;
import app.entities.materials.planksAndRoofCovers.roof.RoofCover;

public class Carport {
    private final int width;
    private final int length;
    private final int height;
    private final String roofType;
    private int roofAngle;

    private final Post postMaterial;
    private final Beam beamMaterial;
    private final Rafter rafterMaterial;
    private final Fascia fasciaMaterial;
    private final RoofCover roofingMaterial;

    public Carport(int width, int length, int height, String roofType,
                   Post postMaterial, Beam beamMaterial, Rafter rafterMaterial,
                   Fascia fasciaMaterial, RoofCover roofingMaterial) {
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
                   Post postMaterial, Beam beamMaterial, Rafter rafterMaterial,
                   Fascia fasciaMaterial, RoofCover roofingMaterial, int roofAngle) {
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

    public Post getPostMaterial() {
        return postMaterial;
    }

    public Beam getBeamMaterial() {
        return beamMaterial;
    }

    public Rafter getRafterMaterial() {
        return rafterMaterial;
    }

    public Fascia getFasciaMaterial() {
        return fasciaMaterial;
    }

    public RoofCover getRoofingMaterial() {
        return roofingMaterial;
    }
}
