package app.entities.orders;

import app.entities.products.carport.Carport;
import app.entities.users.Customer;
import app.entities.users.Staff;
import app.entities.users.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderId;
    private LocalDate orderDate;
    private String orderStatus;
    private String paymentStatus;
    private LocalDate paymentDate;
    private Customer customer;
    private Staff staff;
    private final List<OrderItem> orderItems = new ArrayList<>();

    public Order(int orderId, LocalDate orderDate, String orderStatus, String paymentStatus, LocalDate paymentDate, Customer customer, Staff staff) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.paymentStatus = paymentStatus;
        this.paymentDate = paymentDate;
        this.customer = customer;
        this.staff = staff;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
}
