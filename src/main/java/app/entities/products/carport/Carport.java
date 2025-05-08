package app.entities.products.carport;

import app.Main;
import app.entities.products.materials.Material;
import app.entities.products.materials.MaterialRole;
import app.entities.products.Product;

import java.util.HashMap;
import java.util.Map;

/**
 * A map of materials used in this carport, categorized by their role (e.g., POST, BEAM, RAFTER),
 * where each key corresponds to a single selected material of that type.
 *
 * <p><strong>Design decisions:</strong></p>
 * <ul>
 *   <li><strong>Map over List:</strong> A {@code Map<MaterialRole, Material>} is used instead of a {@code List<Material>}
 *       to provide fast, descriptive access to specific materials by their role.
 *       This approach avoids the need for iteration and type checks when retrieving materials,
 *       and reflects that each role is uniquely represented in a carport (e.g., one POST type, one BEAM type, etc.).</li>
 * </ul>
 *
 * <p>This design provides a good balance between flexibility and simplicity.</p>
 */

public class Carport extends Product {
    private int width;
    private int length;
    private int height;
    private String roofType;
    private int roofAngle;
    private float totalPrice;

    private Map<MaterialRole, Material> materialMap;

    public Carport() {
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
        return "carport";
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

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
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
}
