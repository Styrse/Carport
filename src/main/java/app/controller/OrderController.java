package app.controller;

import app.entities.orders.Order;
import app.entities.orders.OrderItem;
import app.entities.products.carport.Carport;
import app.entities.users.Staff;
import app.exceptions.DatabaseException;
import app.persistence.mappers.OrderMapper;
import app.service.OrderService;
import io.javalin.http.Context;

import java.util.List;
import java.util.Map;

import static app.controller.ControllerHelper.createBaseModel;

public class OrderController {
    public static void showOrders(Context ctx) {
        String statusFilter = ctx.queryParam("status");
        if (statusFilter == null || statusFilter.isBlank()) {
            statusFilter = "Forespørgsel";
        }

        try {
            List<Order> allOrders = OrderMapper.getAllOrders();

            String finalStatusFilter = statusFilter;
            List<Order> filteredOrders = allOrders.stream()
                    .filter(order -> "Alle".equalsIgnoreCase(finalStatusFilter) ||
                            finalStatusFilter.equalsIgnoreCase(order.getOrderStatus()))
                    .toList();

            Map<String, Object> model = createBaseModel(ctx);
            model.put("orders", filteredOrders);
            model.put("selectedStatus", statusFilter);
            model.put("activeTab", "orders");

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
            model.put("activeTab", "orders");

            ctx.render("dashboard/dashboard-order.html", model);
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500).result("Kunne ikke hente ordreinformation");
        }
    }

    public static void cancelOrder(Context ctx) {
        try {
            int orderId = Integer.parseInt(ctx.formParam("orderId"));
            OrderService.cancelOrder(orderId);
            ctx.redirect("/dashboard/orders");
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500).result("Fejl ved annullering af ordre.");
        }
    }

    public static void assignOrderToStaff(Context ctx) {
        try {
            Staff staff = ctx.sessionAttribute("currentUser");
            int orderId = Integer.parseInt(ctx.formParam("orderId"));

            OrderService.assignOrderToStaff(orderId, staff.getUserId());

            staff.getMyWorkOrders().add(OrderMapper.getOrderByOrderId(orderId));

            ctx.redirect("/dashboard");
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500).result("Kunne ikke tildele ordre.");
        }
    }

    public static void removeStaffFromOrder(Context ctx) {
        try {
            Staff staff = ctx.sessionAttribute("currentUser");
            int orderId = Integer.parseInt(ctx.formParam("orderId"));

            OrderService.unassignOrder(orderId, staff.getUserId());

            List<Order> updatedOrders = OrderMapper.getOrdersByStaffId(staff.getUserId());
            staff.setMyWorkOrders(updatedOrders);

            ctx.redirect("/dashboard");
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500).result("Kunne ikke fjerne ordre.");
        }
    }
}
