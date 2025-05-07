package app.entities.users;

import app.entities.orders.Order;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User {
    private List<Order> myOrders;

    public Customer() {
        this.myOrders = new ArrayList<>();
    }

    public Customer(String firstName, String lastName, String phoneNumber, String email, String password, int roleId) {
        super(firstName, lastName, phoneNumber, email, password, roleId);
        this.myOrders = new ArrayList<>();
    }

    public Customer(int userID, String firstName, String lastName, String phoneNumber, String email, String password, int roleId) {
        super(userID, firstName, lastName, phoneNumber, email, password, roleId);
        this.myOrders = new ArrayList<>();
    }

    public Customer(String firstName, String lastName, String address, int postcode, String city, String phoneNumber, String email, String password, int userRole) {
        super(firstName, lastName, address, postcode, city, phoneNumber, email, password, userRole);
        this.myOrders = new ArrayList<>();
    }

    public Customer(int userId, String firstName, String lastName, String address, int postcode, String city, String phoneNumber, String email, String password, int userRole) {
        super(userId, firstName, lastName, address, postcode, city, phoneNumber, email, password, userRole);
        this.myOrders = new ArrayList<>();
    }

    public List<Order> getMyOrders() {
        return myOrders;
    }
}
