package app.entities.orders;

import app.entities.products.carport.Carport;
import app.entities.users.Customer;
import app.entities.users.Staff;
import app.entities.users.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderID;
    private LocalDate orderDate;
    private String orderStatus;
    private String paymentStatus;
    private Carport carport;
    private Customer customer;
    private Staff staff;
    private final List<OrderLine> orderLines = new ArrayList<>();

    public Order(LocalDate orderDate, String orderStatus, String paymentStatus, Carport carport, Customer customer) {
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.paymentStatus = paymentStatus;
        this.carport = carport;
        this.customer = customer;
    }

    public Order(int orderID, LocalDate orderDate, String orderStatus, String paymentStatus, Carport carport, Customer customer) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.paymentStatus = paymentStatus;
        this.carport = carport;
        this.customer = customer;
    }

    public Order(LocalDate orderDate, String orderStatus, String paymentStatus, Carport carport, Customer customer, Staff staff) {
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.paymentStatus = paymentStatus;
        this.carport = carport;
        this.customer = customer;
        this.staff = staff;
    }

    public Order(LocalDate orderDate, int orderID, String orderStatus, String paymentStatus, Carport carport, Customer customer, Staff staff) {
        this.orderDate = orderDate;
        this.orderID = orderID;
        this.orderStatus = orderStatus;
        this.paymentStatus = paymentStatus;
        this.carport = carport;
        this.customer = customer;
        this.staff = staff;
    }

    public Order(int orderId, LocalDate localDate, String orderStatus, String paymentStatus, Carport carport, User customer, User staff) {
    }

    public int getOrderID() {
        return orderID;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public Carport getCarport() {
        return carport;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }
}
