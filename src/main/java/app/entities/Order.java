package app.entities;

import app.entities.carport.Carport;
import app.entities.users.Customer;
import app.entities.users.Staff;

import java.time.LocalDate;

public class Order {
    private final LocalDate orderDate;
    private String orderStatus;
    private String paymentStatus;
    private Carport carport;
    private Customer customer;
    private Staff staff;

    public Order(LocalDate orderDate, String orderStatus, String paymentStatus, Carport carport, Customer customer) {
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

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
