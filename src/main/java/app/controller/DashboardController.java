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
import app.persistence.mappers.UserMapper;
import app.persistence.service.MaterialService;
import io.javalin.http.Context;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

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
        Staff staff = ctx.sessionAttribute("currentUser");

        Map<String, Object> model = new HashMap<>();
        model.put("staff", staff);

        ctx.render("dashboard/dashboard.html", model);
    }

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
    }
    //TODO: Add search box. Fix routing for "Se" and "Slet" buttons

    public static void showCustomers(Context ctx) {
        try {
            List<User> allUsers = UserMapper.getAllUsers();

            List<Customer> customers = allUsers.stream()
                    .filter(user -> user instanceof Customer)
                    .map(user -> (Customer) user)
                    .toList();

            Map<String, Object> model = new HashMap<>();
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

        Staff staff = ctx.sessionAttribute("currentUser");
        boolean isManager = staff instanceof StaffManager;

        Map<String, Object> model = new HashMap<>();
        model.put("isManager", isManager);

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
        Staff dummyStaff = new Staff(
                "John",
                "Doe",
                "22553344",
                "johndoe@fog.dk",
                "securepassword",
                2
        );

        ctx.sessionAttribute("currentUser", dummyStaff);

        Staff staff = ctx.sessionAttribute("currentUser");
        Map<String, Object> model = new HashMap<>();
        model.put("staff", staff);

        ctx.render("dashboard/dashboard-profile.html", model);
    }

    public static void logout(Context ctx) {
        //Destroys the session and removes all attributes
        ctx.req().getSession().invalidate();
        ctx.redirect("/login");
    }


    public static void newMaterial(Context ctx) {
        ctx.render("dashboard/dashboard-new-material.html");
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

            Map<String, Object> model = new HashMap<>();
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
}
