package app.controller.customer;

import app.entities.orders.Order;
import app.entities.orders.OrderItem;
import app.entities.products.carport.Carport;
import app.entities.products.materials.Material;
import app.entities.products.materials.MaterialRole;
import app.entities.products.materials.planks.Beam;
import app.entities.products.materials.planks.Fascia;
import app.entities.products.materials.planks.Post;
import app.entities.products.materials.planks.Rafter;
import app.entities.products.materials.roof.RoofCover;
import app.entities.users.Customer;
import app.exceptions.DatabaseException;
import app.persistence.mappers.MaterialMapper;
import app.persistence.mappers.OrderMapper;
import app.service.MaterialService;
import app.service.UserService;
import io.javalin.http.Context;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarportOrderController {
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
            // Step 1: Retrieve session data
            Customer sessionCustomer = ctx.sessionAttribute("customer");
            Carport carport = ctx.sessionAttribute("carport");

            if (carport == null || sessionCustomer == null) {
                ctx.redirect("/carport/step-1");
                return;
            }

            // Step 2: Ensure the customer exists (or create new)
            Customer customer = UserService.getOrCreateCustomer(
                    sessionCustomer.getFirstName(),
                    sessionCustomer.getLastName(),
                    sessionCustomer.getPhone(),
                    sessionCustomer.getEmail(),
                    sessionCustomer.getAddress(),
                    sessionCustomer.getPostcode()
            );

            // Step 3: Create order with carport as order item
            OrderItem orderItem = new OrderItem(carport, 1);
            Order order = new Order(customer);
            order.setOrderItems(List.of(orderItem));

            // Step 4: Save the order
            OrderMapper.createOrder(order);

            // Step 5: Clear session
            ctx.req().getSession().invalidate();

            // Step 6: Redirect to thank-you page
            ctx.redirect("/carport/thank-you");

        } catch (IllegalArgumentException e) {
            ctx.status(400).result(e.getMessage());
        } catch (DatabaseException e) {
            ctx.attribute("errorMessage", "Der opstod en fejl i databasen. Ordren blev ikke gennemført.");
            ctx.render("public/public-error.html");
        } catch (IOException e) {
            ctx.attribute("errorMessage", "Bekræftelsesmail kunne ikke sendes. Kontakt os venligst.");
            ctx.render("public/public-error.html");
        } catch (Exception e) {
            ctx.attribute("errorMessage", "Der opstod en ukendt fejl. Prøv igen senere.");
            ctx.render("public/public-error.html");
        }
    }

    public static void showThankYouPage(Context ctx) {
        ctx.render("public/thank-you.html");
    }
}
