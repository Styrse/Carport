package app.controller;

import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DashboardController {

    public static void newCarport(Context ctx) {
        ctx.render("dashboard/dashboard-new-carport.html");
    }

    public static void showOrders(Context ctx) {
        String statusFilter = ctx.queryParam("status");
        if (statusFilter == null || statusFilter.isBlank()) {
            statusFilter = "Forespørgsel";
        }

        List<Map<String, Object>> orders = new ArrayList<>();

        Map<String, Object> order1 = new HashMap<>();
        order1.put("orderId", 1);
        order1.put("orderStatus", "Forespørgsel");
        order1.put("customerName", "John");
        order1.put("customerPhone", "12345678");
        order1.put("totalPrice", 1500);

        Map<String, Object> order2 = new HashMap<>();
        order2.put("orderId", 2);
        order2.put("orderStatus", "Betalt");
        order2.put("customerName", "Anna");
        order2.put("customerPhone", "87654321");
        order2.put("totalPrice", 2200);

        Map<String, Object> order3 = new HashMap<>();
        order3.put("orderId", 3);
        order3.put("orderStatus", "Afsendt");
        order3.put("customerName", "Mike");
        order3.put("customerPhone", "45612378");
        order3.put("totalPrice", 1800);

        Map<String, Object> order4 = new HashMap<>();
        order4.put("orderId", 4);
        order4.put("orderStatus", "Leveret");
        order4.put("customerName", "Sophia");
        order4.put("customerPhone", "32165487");
        order4.put("totalPrice", 3000);

        Map<String, Object> order5 = new HashMap<>();
        order5.put("orderId", 5);
        order5.put("orderStatus", "Annuleret");
        order5.put("customerName", "Liam");
        order5.put("customerPhone", "78945612");
        order5.put("totalPrice", 0);

        // Add all orders to list
        orders.add(order1);
        orders.add(order2);
        orders.add(order3);
        orders.add(order4);
        orders.add(order5);

        String finalStatusFilter = statusFilter;
        List<Map<String, Object>> filteredOrders = orders.stream()
                .filter(order -> "Alle".equalsIgnoreCase(finalStatusFilter) ||
                        finalStatusFilter.equalsIgnoreCase(
                                String.valueOf(order.get("orderStatus"))
                        ))
                .collect(Collectors.toList());

        Map<String, Object> model = new HashMap<>();
        model.put("orders", filteredOrders);
        model.put("selectedStatus", statusFilter);
        ctx.render("dashboard/dashboard-orders.html", model);
        //TODO: Add search box. Fix routing for "Se" and "Slet" buttons
    }

    public static void showCustomers(Context ctx) {
        List<Map<String, Object>> customers = new ArrayList<>();

        Map<String, Object> customer1 = new HashMap<>();
        customer1.put("customerId", 101);
        customer1.put("customerEmail", "emma@example.com");
        customer1.put("customerPhone", "22334455");

        Map<String, Object> customer2 = new HashMap<>();
        customer2.put("customerId", 102);
        customer2.put("customerEmail", "oliver@example.com");
        customer2.put("customerPhone", "33445566");

        Map<String, Object> customer3 = new HashMap<>();
        customer3.put("customerId", 103);
        customer3.put("customerEmail", "freja@example.com");
        customer3.put("customerPhone", "44556677");

        Map<String, Object> customer4 = new HashMap<>();
        customer4.put("customerId", 104);
        customer4.put("customerEmail", "mads@example.com");
        customer4.put("customerPhone", "55667788");

        Map<String, Object> customer5 = new HashMap<>();
        customer5.put("customerId", 105);
        customer5.put("customerEmail", "sofia@example.com");
        customer5.put("customerPhone", "66778899");

        customers.add(customer1);
        customers.add(customer2);
        customers.add(customer3);
        customers.add(customer4);
        customers.add(customer5);

        Map<String, Object> model = new HashMap<>();
        model.put("customers", customers);
        ctx.render("dashboard/dashboard-customers", model);
        //TODO: Add search box and fix buttons. Add "Send email" button
    }

    public static void showMaterials(Context ctx) {
    }

    public static void showProfile(Context ctx) {
    }
}
