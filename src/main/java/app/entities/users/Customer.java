package app.entities.users;

import app.entities.Order;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User {
    private List<Order> myOrders;

    public Customer(String firstName, String lastName, int phoneNumber, String email) {
        super(firstName, lastName, phoneNumber, email);
        this.myOrders = new ArrayList<>();
    }

    public List<Order> getMyOrders() {
        return myOrders;
    }
}
