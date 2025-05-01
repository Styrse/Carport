package app.entities.products.carport;

import app.entities.products.materials.planks.Beam;
import app.entities.products.materials.planks.Fascia;
import app.entities.products.materials.planks.Post;
import app.entities.products.materials.planks.Rafter;
import app.entities.products.materials.roof.RoofCover;
import app.entities.products.Product;

public class Carport extends Product {
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

    public Carport() {
    }

    public Carport(int productID, int subProductID, String name, String description, double costPrice, double salesPrice, int width, int length, int height, String roofType, Post post, Beam beam, Rafter rafter, Fascia fascia, RoofCover roofCover) {
        super(productID, subProductID, name, description, costPrice, salesPrice);
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

    public Carport(int productID, int subProductID, String name, String description, double costPrice, double salesPrice, int width, int length, int height, String roofType, int roofAngle, Post post, Beam beam, Rafter rafter, Fascia fascia, RoofCover roofCover) {
        super(productID, subProductID, name, description, costPrice, salesPrice);
        this.width = width;
        this.length = length;
        this.height = height;
        this.roofType = roofType;
        this.roofAngle = roofAngle;
        this.post = post;
        this.beam = beam;
        this.rafter = rafter;
        this.fascia = fascia;
        this.roofCover = roofCover;
    }

    //Generate fresh BillOfMaterial every time
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
