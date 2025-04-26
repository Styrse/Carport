package app.entities;

public class BillOfMaterialsLine {
    private String name;
    private int amount;
    private String unit;
    private String desc;

    public BillOfMaterialsLine(String name, int amount, String unit, String desc) {
        this.name = name;
        this.amount = amount;
        this.unit = unit;
        this.desc = desc;
    }
}
