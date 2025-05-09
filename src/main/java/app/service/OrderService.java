package app.service;

import app.entities.orders.Order;
import app.exceptions.DatabaseException;
import app.persistence.mappers.OrderMapper;

public class OrderService {
    public static void saveOrder(Order order) throws DatabaseException {
        OrderMapper.createOrder(order);
    }

    public static void cancelOrder(int orderId) throws DatabaseException {
        OrderMapper.addStatus(orderId, "Annulleret");
    }

    /*public static List<Order> getUnassignedRequests() throws DatabaseException {
        return OrderMapper.getOrdersByStatusAndNoStaff("Forespørgsel");
    }*/
}
