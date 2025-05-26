package app.controller.dashboard;

import app.entities.orders.Order;
import app.entities.users.Customer;
import app.entities.users.User;
import app.exceptions.DatabaseException;
import app.persistence.mappers.OrderMapper;
import app.persistence.mappers.UserMapper;
import io.javalin.http.Context;

import java.util.List;
import java.util.Map;

import static app.controller.dashboard.ControllerHelper.createBaseModel;

public class CustomerController {

    public static void showCustomers(Context ctx) {
        try {
            List<User> allUsers = UserMapper.getAllUsers();

            List<Customer> customers = allUsers.stream()
                    .filter(user -> user instanceof Customer)
                    .map(user -> (Customer) user)
                    .toList();

            Map<String, Object> model = createBaseModel(ctx);
            model.put("customers", customers);
            model.put("activeTab", "customers");

            ctx.render("dashboard/dashboard-customers", model);
        } catch (DatabaseException e) {
            ctx.attribute("errorMessage", "Fejl ved hentning af kundelisten.");
            ctx.render("dashboard/dashboard-error.html");
        }
    }

    public static void showCustomerPage(Context ctx) {
        int customerId = Integer.parseInt(ctx.formParam("customerId"));

        try {
            User user = UserMapper.getUserById(customerId);
            if (!(user instanceof Customer customer)) {
                ctx.status(400).result("Brugeren blev ikke fundet");
                return;
            }

            List<Order> orders = OrderMapper.getOrdersByCustomerId(customerId);

            Map<String, Object> model = createBaseModel(ctx);
            model.put("customer", customer);
            model.put("orders", orders);
            model.put("activeTab", "customers");

            ctx.render("dashboard/dashboard-customer.html", model);
        } catch (DatabaseException e) {
            ctx.attribute("errorMessage", "Fejl ved hentning af kundeinformation.");
            ctx.render("dashboard/dashboard-error.html");
        }
    }

    public static void updateCustomerInfo(Context ctx) {
        try {
            int customerId = Integer.parseInt(ctx.formParam("customerId"));
            User user = UserMapper.getUserById(customerId);
            if (!(user instanceof Customer customer)) {
                ctx.status(400).result("Brugeren er ikke en kunde");
                return;
            }

            customer.setFirstName(ctx.formParam("firstName"));
            customer.setLastName(ctx.formParam("lastName"));
            customer.setPhone(ctx.formParam("phone"));
            customer.setEmail(ctx.formParam("email"));
            customer.setAddress(ctx.formParam("address"));
            customer.setPostcode(ctx.formParam("postcode"));
            customer.setCity(ctx.formParam("city"));

            UserMapper.updateUser(customer);
            ctx.redirect("/dashboard/customers");
        } catch (Exception e) {
            ctx.attribute("errorMessage", "Fejl ved opdatering af kundeinfo.");
            ctx.render("dashboard/dashboard-error.html");
        }
    }
}
