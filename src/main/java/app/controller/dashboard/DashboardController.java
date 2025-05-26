package app.controller.dashboard;

import app.entities.orders.Order;
import app.entities.users.Staff;
import app.exceptions.DatabaseException;
import app.service.OrderService;
import io.javalin.http.Context;

import java.util.*;

import static app.controller.dashboard.ControllerHelper.createBaseModel;

public class DashboardController {

    public static void dashboard(Context ctx) {
        try {
            Map<String, Object> model = createBaseModel(ctx);
            Staff staff = ctx.sessionAttribute("currentUser");

            // Only show assigned orders that are still in 'Forespørgsel' state
            List<Order> assignedOrders = staff.getMyWorkOrders();
            List<Order> filteredAssigned = assignedOrders.stream()
                    .filter(order -> "Forespørgsel".equals(order.getOrderStatus()))
                    .toList();

            // Load all unassigned orders currently in 'Forespørgsel'
            List<Order> unassignedOrders = OrderService.getUnassignedRequests();

            model.put("assignedOrders", filteredAssigned);
            model.put("unassignedOrders", unassignedOrders);

            ctx.render("dashboard/dashboard.html", model);

        } catch (DatabaseException e) {
            ctx.attribute("errorMessage", "Dashboard kunne ikke indlæses – prøv igen senere.");
            ctx.render("dashboard/dashboard-error.html");
        }
    }
}
