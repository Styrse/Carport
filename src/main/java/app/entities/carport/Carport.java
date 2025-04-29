package app.entities.carport;

import app.entities.materials.planksAndRoofCovers.planks.*;
import app.entities.materials.planksAndRoofCovers.roof.RoofCover;

public class Carport {
    private int width;
    private int length;
    private int height;
    private String roofType;
    private int roofAngle;

    private Post post;
    private Beam beam;
    private Rafter rafter;
    private Fascia fascia;
    private RoofCover roofCover;

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

    public void setWidth(int width) {
        this.width = width;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setRoofType(String roofType) {
        this.roofType = roofType;
    }

    public void setRoofAngle(int roofAngle) {
        this.roofAngle = roofAngle;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public void setBeam(Beam beam) {
        this.beam = beam;
    }

    public void setRafter(Rafter rafter) {
        this.rafter = rafter;
    }

    public void setFascia(Fascia fascia) {
        this.fascia = fascia;
    }

    public void setRoofCover(RoofCover roofCover) {
        this.roofCover = roofCover;
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
