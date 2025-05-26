package app.controller.customer;

import app.entities.orders.Order;
import app.entities.orders.OrderItem;
import app.entities.products.Product;
import app.entities.products.carport.Carport;
import app.entities.products.materials.Material;
import app.entities.products.materials.MaterialRole;
import app.entities.users.Customer;
import app.entities.users.User;
import app.exceptions.DatabaseException;
import app.persistence.mappers.OrderMapper;
import app.persistence.mappers.UserMapper;
import app.utils.PasswordUtil;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerDashboardController {

    public static void showDashboard(Context ctx) {
        User user = ctx.sessionAttribute("currentUser");

        if (user == null || !(user instanceof Customer customer)) {
            ctx.redirect("/customer/login");
            return;
        }

        try {
            List<Order> orders = OrderMapper.getOrdersByCustomerId(customer.getUserId());
            Map<String, Object> model = new HashMap<>();
            model.put("customer", customer);
            model.put("orders", orders);

            ctx.render("public/dashboard", model);
        } catch (DatabaseException e) {
            ctx.attribute("errorMessage", "Der opstod en fejl – prøv igen senere.");
            ctx.render("public/public-error.html");
        }
    }

    public static void showEditPage(Context ctx) {
        Customer customer = ctx.sessionAttribute("currentUser");

        if (customer == null) {
            ctx.redirect("/customer/login");
            return;
        }

        ctx.render("public/edit-profile", Map.of("customer", customer));
    }

    public static void handleEdit(Context ctx) {
        Customer customer = ctx.sessionAttribute("currentUser");
        if (customer == null) {
            ctx.redirect("/customer/login");
            return;
        }

        // Get updated info
        String firstName = ctx.formParam("firstName");
        String lastName = ctx.formParam("lastName");
        String phone = ctx.formParam("phone");
        String address = ctx.formParam("address");
        String postcode = ctx.formParam("postcode");
        String city = ctx.formParam("city");

        // Update customer details
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setPhone(phone);
        customer.setAddress(address);
        customer.setPostcode(postcode);
        customer.setCity(city);

        // Check for password update
        String currentPw = ctx.formParam("currentPassword");
        String newPassword = ctx.formParam("newPassword");
        String repeatPassword = ctx.formParam("repeatPassword");

        try {
            if (currentPw != null && !currentPw.isBlank() &&
                    newPassword != null && !newPassword.isBlank() &&
                    repeatPassword != null && !repeatPassword.isBlank()) {

                if (!PasswordUtil.checkPassword(currentPw, customer.getPassword())) {
                    ctx.attribute("editError", "Nuværende kodeord er forkert.");
                    ctx.render("public/edit-profile", Map.of("customer", customer));
                    return;
                }

                if (!newPassword.equals(repeatPassword)) {
                    ctx.attribute("editError", "De nye kodeord matcher ikke.");
                    ctx.render("public/edit-profile", Map.of("customer", customer));
                    return;
                }

                String hashedPassword = PasswordUtil.hashPassword(newPassword);
                customer.setPassword(hashedPassword);
            }

            // Save updates
            UserMapper.updateUser(customer);
            ctx.sessionAttribute("currentUser", customer);

            ctx.redirect("/customer/dashboard");

        } catch (DatabaseException e) {
            ctx.attribute("errorMessage", "Der opstod en fejl – prøv igen senere.");
            ctx.render("public/public-error.html");
        }
    }

    public static void showOrderDetails(Context ctx) {
        User user = ctx.sessionAttribute("currentUser");

        if (user == null || !(user instanceof Customer customer)) {
            ctx.redirect("/login");
            return;
        }

        int orderId = Integer.parseInt(ctx.pathParam("orderId"));

        try {
            Order order = OrderMapper.getOrderByOrderId(orderId);

            if (order.getCustomer().getUserId() != customer.getUserId()) {
                ctx.status(403).result("Du har ikke adgang til denne ordre.");
                return;
            }

            List<Map<String, Object>> itemModels = new ArrayList<>();

            for (OrderItem item : order.getOrderItems()) {
                Map<String, Object> entry = new HashMap<>();
                entry.put("item", item);

                Product product = item.getProduct();
                if (product instanceof Carport carport) {
                    entry.put("type", "carport");
                    entry.put("carport", carport);

                    entry.put("selectedPost", carport.getMaterialMap().get(MaterialRole.POST));
                    entry.put("selectedBeam", carport.getMaterialMap().get(MaterialRole.BEAM));
                    entry.put("selectedRafter", carport.getMaterialMap().get(MaterialRole.RAFTER));
                    entry.put("selectedFascia", carport.getMaterialMap().get(MaterialRole.FASCIA));
                    entry.put("selectedRoofCover", carport.getMaterialMap().get(MaterialRole.ROOF_COVER));
                } else if (product instanceof Material material) {
                    entry.put("type", "material");
                    entry.put("material", material);
                }

                itemModels.add(entry);
            }

            Map<String, Object> model = new HashMap<>();
            model.put("order", order);
            model.put("items", itemModels);

            ctx.render("public/order-details", model);

        } catch (DatabaseException e) {
            ctx.attribute("errorMessage", "Der opstod en fejl – prøv igen senere.");
            ctx.render("public/public-error.html");
        }
    }
}
