package app.entities.orders;

import app.entities.users.Customer;
import app.entities.users.Staff;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderId;
    private LocalDate orderDate;
    private String orderStatus;
    private Customer customer;
    private Staff staff;
    private List<OrderItem> orderItems;

    public Order(LocalDate orderDate, String orderStatus, Customer customer) {
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.customer = customer;
        this.orderItems = new ArrayList<>();
    }

    public Order(LocalDate orderDate, String orderStatus, Customer customer, Staff staff) {
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.customer = customer;
        this.staff = staff;
        this.orderItems = new ArrayList<>();
    }

    public Order(int orderId, LocalDate orderDate, String orderStatus, Customer customer, Staff staff) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.customer = customer;
        this.staff = staff;
        this.orderItems = new ArrayList<>();
    }

    public Order(int orderId, LocalDate orderDate, String orderStatus, Customer customer) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.customer = customer;
        this.orderItems = new ArrayList<>();
    }

    public float getTotalPrice(){
        float totalPrice = 0;
        //TODO: do
        return totalPrice;
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

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
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

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
