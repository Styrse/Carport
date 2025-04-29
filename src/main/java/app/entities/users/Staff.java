package app.entities.users;

import app.entities.Order;

import java.util.ArrayList;
import java.util.List;

public class Staff extends User {
    private List<Order> myWorkOrders;

    public Staff(String firstName, String lastName, int phoneNumber, String email) {
        super(firstName, lastName, phoneNumber, email);
        this.myWorkOrders = new ArrayList<>();
    }

    public List<Order> getMyWorkOrders() {
        return myWorkOrders;
    }
}
