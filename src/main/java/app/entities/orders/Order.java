package app.entities.orders;

import app.entities.products.carport.Carport;
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
    private List<OrderItem> orderItems = new ArrayList<>();
    private float totalPrice;

    //TODO: Do
    public static int SHIPPING_DK_LESS_5000 = 2000;
    public static int SHIPPING_DK_REST = 3000;
    public static int SHIPPING_INTERNATIONALLY = 6000;

    public Order(Customer customer) {
        this.customer = customer;
    }

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

    public float getTotalPrice() {
        float total = 0;

        for (OrderItem item : orderItems) {
            total += item.getProduct().getSalesPrice() * item.getQuantity();
        }

        return total;
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

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
    }

    public Carport getCarport() {
        for (OrderItem item : orderItems) {
            if (item.getProduct() instanceof Carport carport) {
                return carport;
            }
        }
        return null;
    }

}
