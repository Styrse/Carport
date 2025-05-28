package app.entities.products.carport;

import app.entities.products.materials.Material;
import app.entities.products.materials.MaterialRole;
import app.entities.products.Product;

import java.util.HashMap;
import java.util.Map;

public class Carport extends Product {
    private int width;
    private int length;
    private int height;
    private String roofType;
    private int roofAngle;
    private float totalPrice;

    private Map<MaterialRole, Material> materialMap;

    private Shed shed;

    public Carport() {
    }

    public Carport(int itemId, int width, int length, int height, String roofType, int roofAngle) {
        super(itemId);
        this.width = width;
        this.length = length;
        this.height = height;
        this.roofType = roofType;
        this.roofAngle = roofAngle;
    }

    public Carport(int width, int length, int height, String roofType, int roofAngle) {
        this.width = width;
        this.length = length;
        this.height = height;
        this.roofType = roofType;
        this.roofAngle = roofAngle;
        this.materialMap = new HashMap<>();
    }

    //Carport with flat roof
    public Carport(int itemId, String name, String description, int width, int length, int height, String roofType, Map<MaterialRole, Material> materialMap) {
        super(itemId, name, description);
        this.width = width;
        this.length = length;
        this.height = height;
        this.roofType = roofType;
        this.materialMap = materialMap;
    }

    //Carport with pitched roof
    public Carport(int itemId, String name, String description, int width, int length, int height, String roofType, int roofAngle, Map<MaterialRole, Material> materialMap) {
        super(itemId, name, description);
        this.width = width;
        this.length = length;
        this.height = height;
        this.roofType = roofType;
        this.roofAngle = roofAngle;
        this.materialMap = materialMap;
    }

    @Override
    public String getItemType() {
        return "Carport";
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

    public Map<MaterialRole, Material> getMaterial() {
        return materialMap;
    }

    public float getSalesPrice() {
        return totalPrice;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setMaterialMap(Map<MaterialRole, Material> materialMap) {
        this.materialMap = materialMap;
    }

    public Map<MaterialRole, Material> getMaterialMap() {
        return materialMap;
    }

    @Override
    public String toString() {
        return "Carport{" +
                "width=" + width +
                ", length=" + length +
                ", height=" + height +
                ", roofType='" + roofType + '\'' +
                '}';
    }

    public Shed getShed() {
        return shed;
    }

    public void setShed(Shed shed) {
        this.shed = shed;
    }

    public static class Shed {
        private int width;
        private int length;

        public Shed(int width, int length) {
            this.width = width;
            this.length = length;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }
    }
}
