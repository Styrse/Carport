package app.controller;
import app.entities.orders.Order;
import app.entities.users.Staff;
import app.exceptions.DatabaseException;
import app.persistence.mappers.OrderMapper;
import app.service.OrderService;
import io.javalin.http.Context;

import java.util.*;

import static app.controller.ControllerHelper.createBaseModel;

public class DashboardController {
    public static void dashboard(Context ctx) throws DatabaseException {
        Map<String, Object> model = createBaseModel(ctx);

        Staff staff = ctx.sessionAttribute("currentUser");

        List<Order> assignedOrders = staff.getMyWorkOrders();
        List<Order> filteredAssigned = assignedOrders.stream()
                .filter(order -> "Forespørgsel".equals(order.getOrderStatus()))
                .toList();

        List<Order> unassignedOrders = OrderService.getUnassignedRequests();

        model.put("assignedOrders", filteredAssigned);
        model.put("unassignedOrders", unassignedOrders);

        ctx.render("dashboard/dashboard.html", model);
    }
}
