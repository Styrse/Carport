package app.service;

import app.entities.orders.Order;
import app.exceptions.DatabaseException;
import app.persistence.mappers.OrderMapper;

public class OrderService {
        public static void saveOrder(Order order) throws DatabaseException {
            OrderMapper.createOrder(order);
        }
}
