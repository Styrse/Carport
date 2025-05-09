package app.entities.users;

import app.entities.orders.Order;

import java.util.ArrayList;
import java.util.List;

public class Staff extends User {
    private List<Order> myWorkOrders;

    public Staff() {
        this.myWorkOrders = new ArrayList<>();
    }

    public Staff(String firstName, String lastName, String phoneNumber, String email, String password, int roleId) {
        super(firstName, lastName, phoneNumber, email, password, roleId);
        this.myWorkOrders = new ArrayList<>();
    }

    public Staff(int userID, String firstName, String lastName, String phoneNumber, String email, String password, int roleId) {
        super(userID, firstName, lastName, phoneNumber, email, password, roleId);
        this.myWorkOrders = new ArrayList<>();
    }

    public List<Order> getMyWorkOrders() {
        return myWorkOrders;
    }

    public void setMyWorkOrders(List<Order> myWorkOrders) {
        this.myWorkOrders = myWorkOrders;
    }

    public String getJobTitle() {
        return "Sælger";
    }
}
