package app.entities;

import java.util.List;

public class BillOfMaterial {
    private List<BillOfMaterialsLine> materials;

    public BillOfMaterial(List<BillOfMaterialsLine> materials) {
        this.materials = materials;
    }
}
