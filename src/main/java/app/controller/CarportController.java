package app.controller;

import app.entities.orders.Order;
import app.entities.orders.OrderItem;
import app.entities.products.carport.Carport;
import app.entities.products.materials.MaterialRole;
import app.entities.products.materials.planks.Beam;
import app.entities.products.materials.planks.Fascia;
import app.entities.products.materials.planks.Post;
import app.entities.products.materials.planks.Rafter;
import app.entities.products.materials.roof.RoofCover;
import app.entities.users.Customer;
import app.service.CarportService;
import app.service.MaterialService;
import app.service.OrderService;
import app.service.UserService;
import io.javalin.http.Context;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static app.controller.ControllerHelper.createBaseModel;

public class CarportController {
    public static void newCarport(Context ctx) {
        // Fetch from service
        MaterialService.refreshMaterials();

        List<Post> posts = MaterialService.getAllPosts();
        List<Beam> beams = MaterialService.getAllBeams();
        List<Rafter> rafters = MaterialService.getAllRafters();
        List<Fascia> fascias = MaterialService.getAllFascias();
        List<RoofCover> roofCovers = MaterialService.getAllRoofCovers();

        Map<String, Object> model = createBaseModel(ctx);
        model.put("posts", posts);
        model.put("beams", beams);
        model.put("rafters", rafters);
        model.put("fascias", fascias);
        model.put("roofCovers", roofCovers);

        model.put("activeTab", "new-carport");

        ctx.render("dashboard/dashboard-new-carport.html", model);
    }

    public static void handleNewCarport(Context ctx) {
        try {
            // 1. Parse carport dimensions
            int width = Integer.parseInt(ctx.formParam("width"));
            int length = Integer.parseInt(ctx.formParam("length"));
            int height = Integer.parseInt(ctx.formParam("height"));
            String roofType = ctx.formParam("roofType");
            int roofAngle = Integer.parseInt(ctx.formParam("roofAngle"));

            // 2. Parse selected material IDs
            int postId = Integer.parseInt(ctx.formParam("postId"));
            int beamId = Integer.parseInt(ctx.formParam("beamId"));
            int rafterId = Integer.parseInt(ctx.formParam("rafterId"));
            int fasciaId = Integer.parseInt(ctx.formParam("fasciaId"));
            int roofCoverId = Integer.parseInt(ctx.formParam("roofCoverId"));

            // 3. Parse customer info
            String firstName = ctx.formParam("firstName");
            String lastName = ctx.formParam("lastName");
            String phone = ctx.formParam("phone");
            String email = ctx.formParam("email");
            String address = ctx.formParam("address");
            String postcode = ctx.formParam("postcode");
            String city = ctx.formParam("city");

            // 4. Create customer
            Customer customer = new Customer(firstName, lastName, address, postcode, city, phone, email, 1);
            UserService.createUser(customer);

            // 5. Create carport object
            Carport carport = new Carport(width, length, height, roofType, roofAngle);
            Map<MaterialRole, Integer> materialId = new HashMap<>();
            materialId.put(MaterialRole.POST, postId);
            materialId.put(MaterialRole.BEAM, beamId);
            materialId.put(MaterialRole.RAFTER, rafterId);
            materialId.put(MaterialRole.FASCIA, fasciaId);
            materialId.put(MaterialRole.ROOF_COVER, roofCoverId);
            CarportService.saveCarport(carport, materialId);

            // 6. Create order object (status = NEW, or DRAFT, etc.)
            Order order = new Order(customer);
            OrderItem orderItem = new OrderItem(carport, 1);
            order.addOrderItem(orderItem);
            order.setOrderStatus("Forespørgsel");
            order.setOrderDate(LocalDate.now());
            OrderService.saveOrder(order);

            // 7. Redirect or show confirmation
            ctx.redirect("/dashboard/orders");

        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500).result("Der opstod en fejl ved oprettelsen af carporten.");
        }
    }
}
