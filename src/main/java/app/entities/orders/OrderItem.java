package app.entities.orders;

public class OrderItem {
    private int carportId;
    private int buildingMaterialId;
    private String itemName;
    private int quantity;

    public OrderItem(int carportId, int buildingMaterialId, String itemName, int quantity) {
        this.carportId = carportId;
        this.buildingMaterialId = buildingMaterialId;
        this.itemName = itemName;
        this.quantity = quantity;
    }

    public int getCarportId() {
        return carportId;
    }

    public int getBuildingMaterialId() {
        return buildingMaterialId;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }
}
