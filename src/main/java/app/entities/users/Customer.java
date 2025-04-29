package app.entities.users;

import app.entities.orders.Order;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User {
    private List<Order> myOrders;

    public Customer() {
        this.myOrders = new ArrayList<>();
    }

    public Customer(String firstName, String lastName, int phoneNumber, String email, String password) {
        super(firstName, lastName, phoneNumber, email, password);
        this.myOrders = new ArrayList<>();
    }

    public Customer(int userID, String firstName, String lastName, int phoneNumber, String email, String password) {
        super(userID, firstName, lastName, phoneNumber, email, password);
        this.myOrders = new ArrayList<>();
    }

    public List<Order> getMyOrders() {
        return myOrders;
    }
}
