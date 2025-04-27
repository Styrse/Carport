package app.entities;

import java.util.ArrayList;
import java.util.List;

public class Carport {
    private int width;
    private int length;
    private int height;
    private String roofType;
    private int roofAngle;
    private BillOfMaterial billOfMaterials;

    //Info from guide provided by FOG
    //Max distance according to video timestamp: 2:45 is 340 cm for the beams
    public static final int frontOverhang = 0;
    public static final int backOverhang = 30;
    public static final int sideOverHang = 70;
    public static final int maxLengthBetweenPost = 340;
    public static final int maxPlankLength = 600;
    public static final int carportHeight = 210;
    public static final int maxLengthBetweenRafters = 55;

    //Angle flat roof 1.28% or 10 cm?

    //Constructor for carport with flat roof
    public Carport(int width, int length, String roofType) {
        this.width = width;
        this.length = length;
        this.height = carportHeight;
        this.roofType = roofType;
        this.billOfMaterials = new BillOfMaterial(getMaterials());
    }

    //Constructor for carport with pitched roof
    public Carport(int width, int length, int height, String roofType, int roofAngle) {
        this.width = width;
        this.length = length;
        this.height = height;
        this.roofType = roofType;
        this.roofAngle = roofAngle;
        this.billOfMaterials = new BillOfMaterial(getMaterials());
    }

    //All materials of the carport broken down into smaller methods
    private List<BillOfMaterialsLine> getMaterials() {
        List<BillOfMaterialsLine> BOMLine = new ArrayList<>();
        BOMLine.addAll(getStructure());
        BOMLine.addAll(getRoof());
        return BOMLine;
    }

    //Structure broken down into smaller methods
    private List<BillOfMaterialsLine> getStructure() {
        List<BillOfMaterialsLine> structureBOM = new ArrayList<>();
        int posts = calcTotalPosts();
        int beams = calcTotalBeams();
        int rafters = calcRafters();
//        int fascia = calcFascia();
        return structureBOM;
    }

    //Method for getting posts
    int calcTotalPosts() {
        return calcPostAmountLength() * calcPostAmountWidth();
    }

    int calcPostAmountWidth() {
        int posts = 2;
        if (getWidth() > maxPlankLength) {
            for (int i = maxPlankLength; i < getWidth(); i += maxPlankLength) {
                posts++;
            }
        }
        return posts;
    }

    int calcPostAmountLength() {
        int posts = 2;
        if (getLength() > maxLengthBetweenPost + backOverhang + frontOverhang) {
            for (int i = maxLengthBetweenPost + backOverhang + frontOverhang; i < getLength(); i += maxLengthBetweenPost) {
                posts++;
            }
        }
        return posts;
    }

    //Method for getting beams
    int calcTotalBeams() {
        return calcBeamAmountLength() * calcPostAmountWidth();
    }

    int calcBeamAmountLength() {
        int beams = 1;
        if (getLength() > maxPlankLength) {
            for (int i = maxPlankLength; i < getLength(); i += maxPlankLength) {
                beams++;
            }
        }
        return beams;
    }

    //Method for getting rafters
    int calcRafters() {
        return calcRaftersLength() * calcRaftersWidth();
    }

    int calcRaftersWidth() {
        int rafters = 1;
        if (getWidth() > maxPlankLength) {
            for (int i = maxPlankLength; i < getWidth(); i += maxPlankLength) {
                rafters++;
            }
        }
        return rafters;
    }

    int calcRaftersLength() {
        int rafter = 2;
        if (getLength() > maxLengthBetweenRafters) {
            for (int i = maxLengthBetweenRafters; i < getLength(); i += maxLengthBetweenRafters) {
                rafter++;
            }
        }
        return rafter;
    }


    private List<BillOfMaterialsLine> getRoof() {
        List<BillOfMaterialsLine> roofBOM = new ArrayList<>();
        roofBOM.addAll(getRoofing());
        return roofBOM;
    }

    private List<BillOfMaterialsLine> getRoofing() {
        /*
        - Need sqaureMeter of roof
        - Need width and length of roofCover
        - Overlap 200 (20cm) in the "length" and do overlap on a rafter

         */

        return new ArrayList<>();
    }

    //Getters
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

    public BillOfMaterial getBillOfMaterials() {
        return billOfMaterials;
    }
}
