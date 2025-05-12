package app.controller;

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
import app.entities.users.User;
import app.exceptions.DatabaseException;
import app.persistence.mappers.MaterialMapper;
import app.service.CarportService;
import app.service.MaterialService;
import io.javalin.http.Context;

import java.sql.SQLException;
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

        Carport carport = ctx.sessionAttribute("carport");

        // Simple validation
        if (firstName == null || lastName == null || email == null || phone == null) {
            ctx.status(400).result("Alle felter skal udfyldes.");
            return;
        }

        Customer customer = new Customer(firstName, lastName, address, postcode, city, phone, email, 1);

        Order order = new Order(customer);
        order.setOrderStatus("Ny");
        order.addOrderItem(new OrderItem(carport, 1));

        ctx.sessionAttribute("order", order);

        Map<String, Object> model = new HashMap<>();
        model.put("order", order);

        ctx.render("/carport/step-4", model);
    }
}
