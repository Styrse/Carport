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
    private static final int frontOverhang = 0;
    private static final int backOverhang = 30;
    private static final int sideOverHang = 70;
    //TODO: add side overhang to calc for posts
    private static final int maxLengthBetweenPost = 340;
    private static final int maxPlankLength = 600;
    private static final int carportHeight = 210;
    private static final int maxLengthBetweenRafters = 55;
    private static final int roofingOverlapLength = 20;
    private static final float roofingOverlapWidth = 22.8F;

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

    // All materials of the carport broken down into smaller methods
    private List<BillOfMaterialsLine> getMaterials() {
        List<BillOfMaterialsLine> BOMLine = new ArrayList<>();
        BOMLine.addAll(getStructure());
        BOMLine.addAll(getRoof());
        return BOMLine;
    }

    // Structure broken down into smaller methods
    private List<BillOfMaterialsLine> getStructure() {
        List<BillOfMaterialsLine> structureBOM = new ArrayList<>();
        int posts = calcTotalPosts();
        int beams = calcTotalBeams();
        int rafters = calcTotalRafters();
        int fascias = calcTotalFascias();
        return structureBOM;
    }

    // Method for getting total number of posts
    int calcTotalPosts() {
        return calcPostCountLength() * calcPostCountWidth();
    }

    // Method for calculating number of post rows across the width
    int calcPostCountWidth() {
        int posts = 2;
        if (getWidth() > maxPlankLength) {
            for (int i = maxPlankLength; i < getWidth(); i += maxPlankLength) {
                posts++;
            }
        }
        return posts;
    }

    // Method for calculating number of post rows along the length
    int calcPostCountLength() {
        int posts = 2;
        if (getLength() > maxLengthBetweenPost + backOverhang + frontOverhang) {
            for (int i = maxLengthBetweenPost + backOverhang + frontOverhang; i < getLength(); i += maxLengthBetweenPost) {
                posts++;
            }
        }
        return posts;
    }

    // Method for getting total number of beams
    int calcTotalBeams() {
        return calcBeamAndFasciaCountLength() * calcPostCountWidth();
    }

    // Method for calculating beam count along the length
    int calcBeamAndFasciaCountLength() {
        int beams = 1;
        if (getLength() > maxPlankLength) {
            for (int i = maxPlankLength; i < getLength(); i += maxPlankLength) {
                beams++;
            }
        }
        return beams;
    }

    // Method for getting total number of rafters
    int calcTotalRafters() {
        return calcRafterCountLength() * calcRafterAndFasciaCountWidth();
    }

    // Method for calculating rafter count across the width
    int calcRafterAndFasciaCountWidth() {
        int rafters = 1;
        if (getWidth() > maxPlankLength) {
            for (int i = maxPlankLength; i < getWidth(); i += maxPlankLength) {
                rafters++;
            }
        }
        return rafters;
    }

    // Method for calculating rafter count along the length
    int calcRafterCountLength() {
        int rafter = 2;
        if (getLength() > maxLengthBetweenRafters) {
            for (int i = maxLengthBetweenRafters; i < getLength(); i += maxLengthBetweenRafters) {
                rafter++;
            }
        }
        return rafter;
    }

    // Method for getting total number of fascias
    int calcTotalFascias() {
        return (calcBeamAndFasciaCountLength() + calcRafterAndFasciaCountWidth()) * 2;
    }

    private List<BillOfMaterialsLine> getRoof() {
        List<BillOfMaterialsLine> roofBOM = new ArrayList<>();
        int roofCovers = getRoofing();
        return roofBOM;
    }

    private int getRoofing() {
        /*
        - Need sqaureMeter of roof
        - Need width and length of roofCover
        - Overlap 200 (20cm) in the "length" and do overlap on a rafter
         */

        return calcRoofCountLength() * calcRoofCountWidth();

    }

    private int calcRoofCountWidth() {
        return 1;
    }

    int calcRoofCountLength() {
        int covers = 1;
        if (getLength() > maxPlankLength) {
            for (int i = maxPlankLength; i < getLength(); i += maxPlankLength - roofingOverlapLength) {
                covers++;
            }
        }
        return covers;
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
