package app.controller;

import app.entities.orders.Order;
import app.entities.products.materials.Material;
import app.entities.products.materials.planks.Beam;
import app.entities.products.materials.planks.Fascia;
import app.entities.products.materials.planks.Post;
import app.entities.products.materials.planks.Rafter;
import app.entities.products.materials.roof.RoofCover;
import app.entities.users.Customer;
import app.entities.users.Staff;
import app.entities.users.StaffManager;
import app.entities.users.User;
import app.exceptions.DatabaseException;
import app.persistence.mappers.MaterialMapper;
import app.persistence.mappers.OrderMapper;
import app.persistence.mappers.UserMapper;
import app.persistence.service.MaterialService;
import app.persistence.service.UserService;
import app.utils.PasswordUtil;
import io.javalin.http.Context;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

import static app.Main.connectionPool;

public class DashboardController {

    public static void login(Context ctx) {
        ctx.render("dashboard/dashboard-login.html");
    }

    public static void handleLogin(Context ctx) {
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        try {
            boolean valid = UserMapper.verifyUser(email, password);
            if (!valid) {
                ctx.attribute("loginError", "Forkert email eller adgangskode");
                ctx.render("dashboard/dashboard-login.html");
                return;
            }

            User user = UserMapper.getUserByEmail(email);

            if (!(user instanceof Staff staff)) {
                ctx.render("index.html");
                return;
            }

            boolean isManager = staff instanceof StaffManager;

            ctx.sessionAttribute("currentUser", staff);
            ctx.sessionAttribute("isManager", isManager);

            ctx.redirect("/dashboard");
        } catch (DatabaseException e) {
            e.printStackTrace();
            ctx.status(500).result("Loginfejl – prøv igen senere.");
        }
    }

    public static void dashboard(Context ctx) {
        Map<String, Object> model = createBaseModel(ctx);

        ctx.render("dashboard/dashboard.html", model);
    }

    public static void newCarport(Context ctx) {
        Map<String, Object> model = createBaseModel(ctx);

        ctx.render("dashboard/dashboard-new-carport.html", model);
    }

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

    //TODO: Add search box. Fix routing for "Se" and "Slet" buttons

    public static void showCustomers(Context ctx) {
        try {
            List<User> allUsers = UserMapper.getAllUsers();

            List<Customer> customers = allUsers.stream()
                    .filter(user -> user instanceof Customer)
                    .map(user -> (Customer) user)
                    .toList();

            Map<String, Object> model = createBaseModel(ctx);
            model.put("customers", customers);
            ctx.render("dashboard/dashboard-customers", model);
        } catch (DatabaseException e) {
            e.printStackTrace();
            ctx.status(500).result("Kunne ikke hente kundelisten");
        }
    }
    //TODO: Add search box and fix buttons. Add "Send email" button

    public static void showMaterials(Context ctx) throws DatabaseException {
        MaterialService.refreshMaterials();

        List<Post> posts = MaterialService.getAllPosts();
        List<Beam> beams = MaterialService.getAllBeams();
        List<Rafter> rafters = MaterialService.getAllRafters();
        List<Fascia> fascias = MaterialService.getAllFascias();
        List<RoofCover> roofCovers = MaterialService.getAllRoofCovers();

        float minBuckling = parseFloat(ctx.queryParam("minBuckling"), 0);
        float minGapPost = parseFloat(ctx.queryParam("minGapPost"), 0);
        float minGapRafter = parseFloat(ctx.queryParam("minGapRafter"), 0);

        List<Post> filteredPosts = posts.stream()
                .filter(p -> p.getBucklingCapacity() >= minBuckling)
                .toList();

        List<Beam> filteredBeams = beams.stream()
                .filter(b -> b.getPostGap() >= minGapPost)
                .toList();

        List<RoofCover> filteredRoofCovers = roofCovers.stream()
                .filter(r -> r.getGapRafters() >= minGapRafter)
                .toList();

        Map<String, Object> model = createBaseModel(ctx);

        model.put("posts", filteredPosts);
        model.put("beams", filteredBeams);
        model.put("rafters", rafters);
        model.put("fascias", fascias);
        model.put("roofCovers", filteredRoofCovers);

        model.put("minBuckling", ctx.queryParam("minBuckling"));
        model.put("minGapPost", ctx.queryParam("minGapPost"));
        model.put("minGapRafter", ctx.queryParam("minGapRafter"));

        ctx.render("dashboard/dashboard-materials.html", model);
    }

    public static void showProfile(Context ctx) {
        Map<String, Object> model = createBaseModel(ctx);

        ctx.render("dashboard/dashboard-profile.html", model);
    }

    public static void logout(Context ctx) {
        //Destroys the session and removes all attributes
        ctx.req().getSession().invalidate();
        ctx.redirect("/login");
    }


    public static void newMaterial(Context ctx) {
        Map<String, Object> model = createBaseModel(ctx);

        ctx.render("dashboard/dashboard-new-material.html", model);
    }

    public static void createMaterial(Context ctx) {
        Material material = MaterialService.extractMaterialFromForm(ctx);

        if (material == null) {
            ctx.status(400).result("Invalid input: could not create material.");
            return;
        }

        try {
            MaterialMapper.createMaterial(material);
            MaterialService.refreshMaterials();
            ctx.redirect("/dashboard/materials");
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500).result("Server error while saving material.");
        }
    }

    public static void editMaterial(Context ctx) {
        int itemId = Integer.parseInt(ctx.queryParam("id"));

        try {
            Material material = MaterialMapper.getMaterialById(itemId);

            Map<String, Object> model = createBaseModel(ctx);
            model.put("material", material);

            ctx.render("dashboard/dashboard-edit-material.html", model);
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(404).result("Materiale ikke fundet");
        }
    }

    public static void updateMaterial(Context ctx) throws SQLException, DatabaseException {
        Material material = MaterialService.extractMaterialFromForm(ctx);
        material.setItemId(Integer.parseInt(ctx.formParam("materialId")));

        MaterialMapper.updateMaterial(material);
        MaterialService.refreshMaterials();
        ctx.redirect("/dashboard/materials");
    }

    public static void deleteMaterial(Context ctx) throws SQLException, DatabaseException {
        int itemId = Integer.parseInt(ctx.formParam("materialId"));
        MaterialMapper.deleteMaterialById(itemId);
        ctx.redirect("/dashboard/materials");
    }

    private static float parseFloat(String input, float fallback) {
        if (input == null || input.isBlank()) return fallback;
        try {
            return Float.parseFloat(input);
        } catch (NumberFormatException e) {
            return fallback;
        }
    }

    public static void updateProfile(Context ctx) {
        User user = ctx.sessionAttribute("currentUser");
        if (user == null) {
            ctx.redirect("/login");
            return;
        }

        String firstName = ctx.formParam("firstName");
        String lastName = ctx.formParam("lastName");
        String phone = ctx.formParam("phoneNumber");
        String email = ctx.formParam("email");
        String currentPassword = ctx.formParam("currentPassword");

        String newPassword = ctx.formParam("newPassword");
        String repeatPassword = ctx.formParam("newPasswordRepeat");

        try {
            // Verify current password
            if (!PasswordUtil.checkPassword(currentPassword, user.getPassword())) {
                ctx.status(400).result("Forkert nuværende adgangskode.");
                return;
            }

            // Update fields
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPhone(phone);
            user.setEmail(email);

            String newHashedPassword = null;
            if (newPassword != null && !newPassword.isBlank()) {
                if (!newPassword.equals(repeatPassword)) {
                    ctx.status(400).result("De nye adgangskoder matcher ikke.");
                    return;
                }
                newHashedPassword = PasswordUtil.hashPassword(newPassword);
            }

            user.setPassword(newHashedPassword);

            UserMapper.updateUser(user);

            // Refresh session
            ctx.sessionAttribute("currentUser", UserMapper.getUserByEmail(user.getEmail()));
            ctx.redirect("/dashboard/profile");

        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500).result("Kunne ikke opdatere profil.");
        }
    }

    public static void updatePasswordQuickly(Context ctx) {
        User user = ctx.sessionAttribute("currentUser");
        if (user == null) {
            ctx.redirect("/login");
            return;
        }

        String newPassword = ctx.formParam("newPassword");

        if (newPassword == null || newPassword.isBlank()) {
            ctx.status(400).result("Adgangskode må ikke være tom.");
            return;
        }

        try (Connection connection = connectionPool.getConnection()) {
            String sql = "UPDATE users SET password = ? WHERE user_id = ?";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, PasswordUtil.hashPassword(newPassword));
                ps.setInt(2, user.getUserId());
                ps.executeUpdate();
            }

            ctx.redirect("/dashboard/");
        } catch (SQLException e) {
            e.printStackTrace();
            ctx.status(500).result("Fejl ved opdatering af adgangskode.");
        }
    }

    public static void createStaff(Context ctx) {
        Map<String, Object> model = createBaseModel(ctx);

        ctx.render("dashboard/dashboard-create-staff.html", model);
    }

    public static void handleCreateStaff(Context ctx) throws SQLException, DatabaseException {
        String password = ctx.formParam("password");
        String passwordRepeat = ctx.formParam("passwordRepeat");

        //Password mismatch check
        if (!password.equals(passwordRepeat)) {
            ctx.status(400).result("Adgangskoderne matcher ikke.");
            return;
        }

        User staff = UserService.mapUserService(ctx);

        UserMapper.createUser(staff);
        ctx.redirect("/dashboard/");
    }

    public static Map<String, Object> createBaseModel(Context ctx) {
        Staff staff = ctx.sessionAttribute("currentUser");
        boolean isManager = staff instanceof StaffManager;

        Map<String, Object> model = new HashMap<>();
        model.put("staff", staff);
        model.put("isManager", isManager);
        return model;
    }

    public static void showStaff(Context ctx) {
        try {
            List<User> allUsers = UserMapper.getAllUsers();

            List<Staff> staffList = allUsers.stream()
                    .filter(user -> user instanceof Staff)
                    .map(user -> (Staff) user)
                    .toList();

            Map<String, Object> model = createBaseModel(ctx);
            model.put("staffList", staffList);

            ctx.render("dashboard/dashboard-staff.html", model);
        } catch (DatabaseException e) {
            e.printStackTrace();
            ctx.status(500).result("Kunne ikke hente medarbejderlisten.");
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

            Map<String, Object> model = DashboardController.createBaseModel(ctx);
            model.put("customer", customer);
            model.put("orders", orders);

            ctx.render("dashboard/dashboard-customer.html", model);
        } catch (DatabaseException e) {
            e.printStackTrace();
            ctx.status(500).result("Kunne ikke hente kundeinformation");
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

            UserMapper.updateUser(customer);
            ctx.redirect("/dashboard/customers"); // Or back to the customer page if needed
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500).result("Fejl ved opdatering af kundeinfo");
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
