package app.controller;

import app.entities.orders.Order;
import app.entities.orders.OrderItem;
import app.entities.products.Product;
import app.entities.products.carport.Carport;
import app.entities.products.materials.Material;
import app.entities.products.materials.MaterialRole;
import app.entities.products.materials.planks.Beam;
import app.entities.products.materials.planks.Fascia;
import app.entities.products.materials.planks.Post;
import app.entities.products.materials.planks.Rafter;
import app.entities.products.materials.roof.RoofCover;
import app.entities.users.Customer;
import app.entities.users.User;
import app.exceptions.DatabaseException;
import app.persistence.mappers.MaterialMapper;
import app.persistence.mappers.OrderMapper;
import app.persistence.mappers.UserMapper;
import app.service.MaterialService;
import app.service.UserService;
import app.utils.PasswordUtil;
import io.javalin.http.Context;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PublicController {
    public static void showHomepage(Context ctx) {
        Map<String, Object> model = new HashMap<>();
        model.put("title", "Velkommen til Fog");
        ctx.render("public/index.html", model);
    }

    public static void showSizePage(Context ctx) {
        ctx.render("public/step-1-size.html");
    }

    public static void handleRoofSelection(Context ctx) {
        String roofType = ctx.formParam("rooftype");

        Carport carport = new Carport();
        carport.setRoofType(roofType);

        ctx.sessionAttribute("carport", carport);

        ctx.render("public/step-1-size.html");
    }

    public static void handleStep1(Context ctx) {
        int width = Integer.parseInt(ctx.formParam("width"));
        int length = Integer.parseInt(ctx.formParam("length"));
        int height = Integer.parseInt(ctx.formParam("height"));
        boolean includeShed = ctx.formParam("includeShed") != null;

        Carport carport = ctx.sessionAttribute("carport");
        carport.setWidth(width);
        carport.setLength(length);
        carport.setHeight(height);

        ctx.sessionAttribute("carport", carport);

        if (includeShed) {
            ctx.redirect("/carport/step-1.1");
        } else {
            ctx.redirect("/carport/step-2");
        }
    }

    /**
     * Displays the form for configuring the shed dimensions (Step 1.1).
     * <p>
     * The maximum allowed shed width and length are calculated by subtracting 30 cm (15 cm overhang on each side)
     * from the total carport width and length. This overhang requirement is specified by Fog's design constraints
     * to ensure structural clearance and aesthetic spacing within the carport.
     * </p>
     */
    public static void showShedSizePage(Context ctx) {
        Carport carport = ctx.sessionAttribute("carport");

        if (carport == null) {
            ctx.redirect("/carport/step-1");
            return;
        }

        Map<String, Object> model = new HashMap<>();
        model.put("carport", carport);
        model.put("maxShedWidth", carport.getWidth() - 30);
        model.put("maxShedLength", carport.getLength() - 30);
        ctx.render("public/step-1.1-shed-size.html", model);
    }

    /**
     * Handles the form submission for specifying shed dimensions in step 1.1 of the carport configuration.
     * <p>
     * Retrieves the shed width and length from the form, validates that the dimensions
     * do not exceed the carport dimensions minus a 30 cm buffer (15 cm on each side), and
     * stores the shed as part of the Carport object in session.
     * </p>
     * <p>
     * If validation fails, the request is rejected with status 400 and an appropriate error message.
     * On success, the flow continues to step 2 of the configuration.
     * </p>
     *
     * @param ctx the Javalin HTTP context containing form parameters and session attributes
     */
    public static void handleShedSize(Context ctx) {
        Carport carport = ctx.sessionAttribute("carport");

        if (carport == null) {
            ctx.redirect("/carport/step-1");
            return;
        }

        int shedWidth = Integer.parseInt(ctx.formParam("shedWidth"));
        int shedLength = Integer.parseInt(ctx.formParam("shedLength"));

        if (shedWidth > carport.getWidth() - 30 || shedLength > carport.getLength() - 30) {
            ctx.status(400).result("Redskabsskuret må maksimalt være 30 cm mindre end carportens bredde og længde.");
            return;
        }

        carport.setShed(new Carport.Shed(shedWidth, shedLength));
        ctx.sessionAttribute("carport", carport);

        ctx.redirect("/carport/step-2");
    }

    public static void showMaterialsPage(Context ctx) {
        Carport carport = ctx.sessionAttribute("carport");

        if (carport == null) {
            ctx.redirect("/carport/step-1");
            return;
        }

        Map<String, Object> model = new HashMap<>();
        model.put("carport", carport);

        // Optional: fetch available materials and pass to view
        List<Post> posts = MaterialService.getAllPosts();
        List<Beam> beams = MaterialService.getAllBeams();
        List<Rafter> rafters = MaterialService.getAllRafters();
        List<Fascia> fascias = MaterialService.getAllFascias();
        List<RoofCover> roofCovers = MaterialService.getAllRoofCovers();

        model.put("posts", posts);
        model.put("beams", beams);
        model.put("rafters", rafters);
        model.put("fascias", fascias);
        model.put("roofCovers", roofCovers);

        ctx.render("public/step-2-materials.html", model);
    }

    public static void handleStep2(Context ctx) throws SQLException, DatabaseException {
        Carport carport = ctx.sessionAttribute("carport");

        if (carport == null) {
            ctx.redirect("/carport/step-1");
            return;
        }

        int postId = Integer.parseInt(ctx.formParam("postId"));
        int beamId = Integer.parseInt(ctx.formParam("beamId"));
        int rafterId = Integer.parseInt(ctx.formParam("rafterId"));
        int fasciaId = Integer.parseInt(ctx.formParam("fasciaId"));
        int roofCoverId = Integer.parseInt(ctx.formParam("roofCoverId"));

        Map<MaterialRole, Material> materialMap = new HashMap<>();
        materialMap.put(MaterialRole.POST, MaterialMapper.getMaterialById(postId));
        materialMap.put(MaterialRole.BEAM, MaterialMapper.getMaterialById(beamId));
        materialMap.put(MaterialRole.RAFTER, MaterialMapper.getMaterialById(rafterId));
        materialMap.put(MaterialRole.FASCIA, MaterialMapper.getMaterialById(fasciaId));
        materialMap.put(MaterialRole.ROOF_COVER, MaterialMapper.getMaterialById(roofCoverId));
        carport.setMaterialMap(materialMap);

        ctx.sessionAttribute("carport", carport);

        ctx.redirect("/carport/step-3");
    }

    public static void showContactInfoPage(Context ctx) {
        Carport carport = ctx.sessionAttribute("carport");

        if (carport == null) {
            ctx.redirect("/carport/step-1");
            return;
        }

        Map<String, Object> model = new HashMap<>();
        model.put("carport", carport);
        ctx.render("public/step-3-contact-info.html", model);
    }

    public static void handleContactInfo(Context ctx) {
        String firstName = ctx.formParam("first-name");
        String lastName = ctx.formParam("last-name");
        String email = ctx.formParam("email");
        String phone = ctx.formParam("phone");
        String address = ctx.formParam("address");
        String postcode = ctx.formParam("postcode");
        String city = ctx.formParam("city");


        // Simple validation
        if (firstName == null || lastName == null || email == null || phone == null) {
            ctx.status(400).result("Alle felter skal udfyldes.");
            return;
        }

        Customer customer = new Customer(firstName, lastName, address, postcode, city, phone, email, 1);

        ctx.sessionAttribute("customer", customer);

        ctx.redirect("/carport/step-4");
    }

    public static void showConfirmationPage(Context ctx) {
        Carport carport = ctx.sessionAttribute("carport");
        Customer customer = ctx.sessionAttribute("customer");

        if (carport == null || customer == null) {
            ctx.redirect("/carport/step-1");
            return;
        }

        Material post = carport.getMaterialMap().get(MaterialRole.POST);
        Material beam = carport.getMaterialMap().get(MaterialRole.BEAM);
        Material rafter = carport.getMaterialMap().get(MaterialRole.RAFTER);
        Material fascia = carport.getMaterialMap().get(MaterialRole.FASCIA);
        Material roofCover = carport.getMaterialMap().get(MaterialRole.ROOF_COVER);

        Map<String, Object> model = new HashMap<>();
        model.put("customer", customer);
        model.put("carport", carport);

        model.put("post", post.getName());
        model.put("beam", beam.getName());
        model.put("rafter", rafter.getName());
        model.put("fascia", fascia.getName());
        model.put("roofCover", roofCover.getName());

        ctx.render("public/step-4-summary.html", model);
    }

    public static void submitOrder(Context ctx) {
        try {
            // Step 1: Get form data or session data
            Customer sessionCustomer = ctx.sessionAttribute("customer");
            Carport carport = ctx.sessionAttribute("carport");

            if (carport == null || sessionCustomer == null) {
                ctx.redirect("/carport/step-1");
                return;
            }

            // Step 2: Make sure the customer exists in the database (or create)
            Customer customer = UserService.getOrCreateCustomer(
                    sessionCustomer.getFirstName(),
                    sessionCustomer.getLastName(),
                    sessionCustomer.getPhone(),
                    sessionCustomer.getEmail(),
                    sessionCustomer.getAddress(),
                    sessionCustomer.getPostcode(),
                    sessionCustomer.getCity()
            );

            // Step 3: Create the order with the carport as an order item
            OrderItem orderItem = new OrderItem(carport, 1);
            Order order = new Order(customer);
            order.setOrderItems(List.of(orderItem));

            // Step 4: Save the order to the database
            OrderMapper.createOrder(order);

            // Step 5: Clear session
            ctx.req().getSession().invalidate();

            // Step 6: Redirect to thank-you page
            ctx.redirect("/carport/thank-you");

        } catch (IllegalArgumentException e) {
            ctx.status(400).result(e.getMessage());
        } catch (DatabaseException e) {
            e.printStackTrace();
            ctx.status(500).result("Fejl i databasen. Ordren blev ikke gennemført.");
        } catch (IOException e) {
            e.printStackTrace();
            ctx.status(500).result("Fejl ved afsendelse af bekræftelsesmail.");
        }
    }

    public static void showThankYouPage(Context ctx) {
        ctx.render("public/thank-you.html");
    }

    public static void showLoginPage(Context ctx) {
        ctx.render("public/login");
    }

    public static void handleLogin(Context ctx) {
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        try {
            // 1. Check if user exists and credentials match
            boolean valid = UserMapper.verifyUser(email, password);
            if (!valid) {
                ctx.attribute("loginError", "Forkert email eller adgangskode");
                ctx.render("public/login");
                return;
            }

            // 2. Get full user
            User user = UserMapper.getUserByEmail(email);

            // 3. Ensure it's a Customer (not Staff)
            if (!(user instanceof Customer customer)) {
                ctx.render("index.html");
                return;
            }

            // 4. Store in session
            ctx.sessionAttribute("currentUser", customer);

            // 5. Redirect to dashboard
            ctx.redirect("/customer/dashboard");

        } catch (DatabaseException e) {
            e.printStackTrace();
            ctx.status(500).result("Loginfejl – prøv igen senere.");
        }
    }

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
            e.printStackTrace();
            ctx.status(500).result("Fejl ved hentning af ordrer.");
        }
    }

    public static void showEditForm(Context ctx) {
        Customer customer = ctx.sessionAttribute("currentUser");

        if (customer == null) {
            ctx.redirect("/customer/login");
            return;
        }

        ctx.render("public/edit-profile", Map.of("customer", customer));
    }

    public static void handleEditForm(Context ctx) {
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
            // Check fields are entered
            if (currentPw != null && !currentPw.isBlank() &&
                    newPassword != null && !newPassword.isBlank() &&
                    repeatPassword != null && !repeatPassword.isBlank()) {

                if (!PasswordUtil.checkPassword(currentPw, customer.getPassword())) {
                    ctx.attribute("editError", "Nuværende kodeord er forkert.");
                    ctx.render("public/customer/edit-profile", Map.of("customer", customer));
                    return;
                }

                if (!newPassword.equals(repeatPassword)) {
                    ctx.attribute("editError", "De nye kodeord matcher ikke.");
                    ctx.render("public/customer/edit-profile", Map.of("customer", customer));
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
            e.printStackTrace();
            ctx.status(500).result("Kunne ikke opdatere oplysninger.");
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
            e.printStackTrace();
            ctx.status(500).result("Kunne ikke hente ordren.");
        }
    }
}
