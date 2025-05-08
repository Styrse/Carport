package app.controller;

import app.entities.orders.Order;
import app.exceptions.DatabaseException;
import app.persistence.mappers.OrderMapper;
import io.javalin.http.Context;

import java.util.List;
import java.util.Map;

import static app.controller.ControllerHelper.createBaseModel;

public class OrderController {
    public static void showOrders(Context ctx) {
        String statusFilter = ctx.queryParam("status");
        if (statusFilter == null || statusFilter.isBlank()) {
            statusFilter = "Inquiry";
        }

        try {
            List<Order> allOrders = OrderMapper.getAllOrders();

            String finalStatusFilter = statusFilter;
            List<Order> filteredOrders = allOrders.stream()
                    .filter(order -> "All".equalsIgnoreCase(finalStatusFilter) ||
                            finalStatusFilter.equalsIgnoreCase(order.getOrderStatus()))
                    .toList();

            Map<String, Object> model = createBaseModel(ctx);
            model.put("orders", filteredOrders);
            model.put("selectedStatus", statusFilter);

            ctx.render("dashboard/dashboard-orders.html", model);
        } catch (DatabaseException e) {
            e.printStackTrace();
            ctx.status(500).result("Kunne ikke hente ordrelisten");
        }
    }

    public static void showOrderDetails(Context ctx) {
        try {
            int orderId = Integer.parseInt(ctx.queryParam("orderId"));

            Order order = OrderMapper.getOrderByOrderId(orderId);
            if (order == null) {
                ctx.status(404).result("Ordre ikke fundet");
                return;
            }

            Map<String, Object> model = createBaseModel(ctx);
            model.put("order", order);

            ctx.render("dashboard/dashboard-order.html", model);
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500).result("Kunne ikke hente ordreinformation");
        }
    }
}
