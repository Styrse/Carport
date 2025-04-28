package app.entities.carport;

import app.entities.materials.planksAndRoofCovers.planks.*;
import app.entities.materials.planksAndRoofCovers.roof.RoofCover;

public class Carport {
    private final int width;
    private final int length;
    private final int height;
    private final String roofType;
    private int roofAngle;

    private final Post post;
    private final Beam beam;
    private final Rafter rafter;
    private final Fascia fascia;
    private final RoofCover roofCover;

    public Carport(int width, int length, int height, String roofType,
                   Post post, Beam beam, Rafter rafter,
                   Fascia fascia, RoofCover roofCover) {
        this.width = width;
        this.length = length;
        this.height = height;
        this.roofType = roofType;
        this.post = post;
        this.beam = beam;
        this.rafter = rafter;
        this.fascia = fascia;
        this.roofCover = roofCover;
    }

    public Carport(int width, int length, int height, String roofType,
                   Post post, Beam beam, Rafter rafter,
                   Fascia fascia, RoofCover roofCover, int roofAngle) {
        this(width, length, height, roofType, post, beam, rafter, fascia, roofCover);
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

    public Post getPost() {
        return post;
    }

    public Beam getBeam() {
        return beam;
    }

    public Rafter getRafter() {
        return rafter;
    }

    public Fascia getFascia() {
        return fascia;
    }

    public RoofCover getRoofCover() {
        return roofCover;
    }

    @Override
    public String toString() {
        return "Carport{" +
                "width=" + width +
                ", length=" + length +
                ", height=" + height +
                ", roofType='" + roofType + '\'' +
                ", post=" + (post != null ? post.getName() : "None") +
                ", beam=" + (beam != null ? beam.getName() : "None") +
                ", rafter=" + (rafter != null ? rafter.getName() : "None") +
                ", fascia=" + (fascia != null ? fascia.getName() : "None") +
                ", roofCover=" + (roofCover != null ? roofCover.getName() : "None") +
                '}';
    }
}
