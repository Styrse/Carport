package app.controller.dashboard;

import app.entities.orders.Order;
import app.entities.orders.OrderItem;
import app.entities.products.carport.BillOfMaterial;
import app.entities.products.carport.BillOfMaterialsItem;
import app.entities.products.carport.Carport;
import app.entities.products.materials.Material;
import app.entities.products.materials.MaterialRole;
import app.entities.products.materials.planks.Beam;
import app.entities.products.materials.planks.Fascia;
import app.entities.products.materials.planks.Post;
import app.entities.products.materials.planks.Rafter;
import app.entities.products.materials.roof.RoofCover;
import app.entities.users.Customer;
import app.entities.users.Staff;
import app.exceptions.DatabaseException;
import app.persistence.mappers.CarportMapper;
import app.persistence.mappers.MaterialMapper;
import app.persistence.mappers.OrderMapper;
import app.service.*;
import io.javalin.http.Context;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static app.controller.dashboard.ControllerHelper.createBaseModel;

public class CarportController {

    public static void newCarport(Context ctx) {
        // Load all available materials
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

    // Handles form submission for creating a new carport order.
    public static void handleNewCarport(Context ctx) {
        try {
            // Parse carport dimensions and materials
            int width = Integer.parseInt(ctx.formParam("width"));
            int length = Integer.parseInt(ctx.formParam("length"));
            int height = Integer.parseInt(ctx.formParam("height"));
            String roofType = ctx.formParam("roofType");
            int roofAngle = Integer.parseInt(ctx.formParam("roofAngle"));

            int postId = Integer.parseInt(ctx.formParam("postId"));
            int beamId = Integer.parseInt(ctx.formParam("beamId"));
            int rafterId = Integer.parseInt(ctx.formParam("rafterId"));
            int fasciaId = Integer.parseInt(ctx.formParam("fasciaId"));
            int roofCoverId = Integer.parseInt(ctx.formParam("roofCoverId"));

            // Parse customer info
            String firstName = ctx.formParam("firstName");
            String lastName = ctx.formParam("lastName");
            String phone = ctx.formParam("phone");
            String email = ctx.formParam("email");
            String address = ctx.formParam("address");
            String postcode = ctx.formParam("postcode");

            // Get or create customer
            Customer customer = UserService.getOrCreateCustomer(
                    firstName, lastName, phone, email.toLowerCase(), address, postcode
            );

            // Create and save Carport
            Carport carport = new Carport(width, length, height, roofType, roofAngle);
            Map<MaterialRole, Integer> materialId = new HashMap<>();
            materialId.put(MaterialRole.POST, postId);
            materialId.put(MaterialRole.BEAM, beamId);
            materialId.put(MaterialRole.RAFTER, rafterId);
            materialId.put(MaterialRole.FASCIA, fasciaId);
            materialId.put(MaterialRole.ROOF_COVER, roofCoverId);

            CarportService.saveCarport(carport, materialId);

            Staff currentStaff = ctx.sessionAttribute("currentUser");

            // Create and save order
            Order order = new Order(customer);
            order.addOrderItem(new OrderItem(carport, 1));
            order.setOrderStatus("Forespørgsel");
            order.setOrderDate(LocalDate.now());
            order.setStaff(currentStaff);

            OrderService.saveOrder(order);

            currentStaff.getMyWorkOrders().add(order);

            ctx.redirect("/dashboard/order?orderId=" + order.getOrderId());

        } catch (IllegalArgumentException e) {
            ctx.status(400).result(e.getMessage());
        } catch (DatabaseException e) {
            ctx.attribute("errorMessage", "Databasefejl ved oprettelse eller opdatering af kunde.");
            ctx.render("dashboard/dashboard-error.html");
        } catch (Exception e) {
            ctx.attribute("errorMessage", "Der opstod en fejl ved oprettelsen af carporten.");
            ctx.render("dashboard/dashboard-error.html");
        }
    }

    public static void showCarportConfiguration(Context ctx) throws DatabaseException {
        int orderId = Integer.parseInt(ctx.queryParam("orderId"));
        int index = Integer.parseInt(ctx.queryParam("index"));

        Order order = OrderMapper.getOrderByOrderId(orderId);
        Carport carport = (Carport) order.getOrderItems().get(index).getProduct();

        MaterialService.refreshMaterials();
        List<Post> posts = MaterialService.getAllPosts();
        List<Beam> beams = MaterialService.getAllBeams();
        List<Rafter> rafters = MaterialService.getAllRafters();
        List<Fascia> fascias = MaterialService.getAllFascias();
        List<RoofCover> roofCovers = MaterialService.getAllRoofCovers();

        // Get selected materials
        Material selectedPost = carport.getMaterialMap().get(MaterialRole.POST);
        Material selectedBeam = carport.getMaterialMap().get(MaterialRole.BEAM);
        Material selectedRafter = carport.getMaterialMap().get(MaterialRole.RAFTER);
        Material selectedFascia = carport.getMaterialMap().get(MaterialRole.FASCIA);
        Material selectedRoofCover = carport.getMaterialMap().get(MaterialRole.ROOF_COVER);

        Map<String, Object> model = createBaseModel(ctx);
        model.put("order", order);
        model.put("carport", carport);

        model.put("posts", posts);
        model.put("beams", beams);
        model.put("rafters", rafters);
        model.put("fascias", fascias);
        model.put("roofCovers", roofCovers);

        model.put("selectedPostId", selectedPost.getItemId());
        model.put("selectedBeamId", selectedBeam.getItemId());
        model.put("selectedRafterId", selectedRafter.getItemId());
        model.put("selectedFasciaId", selectedFascia.getItemId());
        model.put("selectedRoofCoverId", selectedRoofCover.getItemId());

        model.put("activeTab", "orders");

        ctx.render("dashboard/dashboard-edit-carport.html", model);
    }

    public static void updateCarport(Context ctx) throws DatabaseException {
        int orderId = Integer.parseInt(ctx.formParam("orderId"));
        int carportId = Integer.parseInt(ctx.formParam("carportId"));
        int width = Integer.parseInt(ctx.formParam("width"));
        int length = Integer.parseInt(ctx.formParam("length"));
        int height = Integer.parseInt(ctx.formParam("height"));
        String roofType = ctx.formParam("roofType");
        int roofAngle = Integer.parseInt(ctx.formParam("roofAngle"));

        int postId = Integer.parseInt(ctx.formParam("postId"));
        int beamId = Integer.parseInt(ctx.formParam("beamId"));
        int rafterId = Integer.parseInt(ctx.formParam("rafterId"));
        int fasciaId = Integer.parseInt(ctx.formParam("fasciaId"));
        int roofCoverId = Integer.parseInt(ctx.formParam("roofCoverId"));

        // Update carport with selected materials based on form input
        Carport carport = new Carport(carportId, width, length, height, roofType, roofAngle);
        carport.setMaterialMap(Map.of(
                MaterialRole.POST, MaterialMapper.getMaterialById(postId),
                MaterialRole.BEAM, MaterialMapper.getMaterialById(beamId),
                MaterialRole.RAFTER, MaterialMapper.getMaterialById(rafterId),
                MaterialRole.FASCIA, MaterialMapper.getMaterialById(fasciaId),
                MaterialRole.ROOF_COVER, MaterialMapper.getMaterialById(roofCoverId)
        ));

        CarportMapper.updateCarport(carport);

        ctx.redirect("/dashboard/order?orderId=" + orderId);
    }

    public static void showBillOfMaterials(Context ctx) {
        try {
            int orderId = Integer.parseInt(ctx.queryParam("orderId"));
            int index = Integer.parseInt(ctx.queryParam("index"));

            Order order = OrderMapper.getOrderByOrderId(orderId);
            Carport carport = (Carport) order.getOrderItems().get(index).getProduct();

            BillOfMaterial bom = carport.getBillOfMaterial();
            List<BillOfMaterialsItem> items = bom.getLines();

            Map<String, Object> model = createBaseModel(ctx);
            model.put("orderId", orderId);
            model.put("bomItems", items);
            model.put("totalPrice", bom.calcTotalPrice());
            model.put("activeTab", "orders");

            ctx.render("dashboard/dashboard-carport-bom.html", model);
        } catch (Exception e) {
            ctx.attribute("errorMessage", "Kunne ikke hente stykliste.");
            ctx.render("dashboard/dashboard-error.html");
        }
    }

    public static void showSVG(Context ctx) {
        Locale.setDefault(Locale.US);

        int orderId = Integer.parseInt(ctx.queryParam("orderId"));
        int index = Integer.parseInt(ctx.queryParam("index"));

        try {
            Order order = OrderMapper.getOrderByOrderId(orderId);
            OrderItem item = order.getOrderItems().get(index);

            if (!(item.getProduct() instanceof Carport carport)) {
                ctx.status(400).result("Produktet er ikke en carport.");
                return;
            }

            Svg topSvg = SvgDrawingService.generateCarportSvg(carport);
            Svg sideSvg = SvgDrawingService.generateCarportSideSvg(carport);

            ctx.attribute("svg", topSvg.toString());
            ctx.attribute("svgSide", sideSvg.toString());
            ctx.attribute("orderId", orderId);

            ctx.render("showSvg.html");

        } catch (Exception e) {
            ctx.attribute("errorMessage", "Kunne ikke vise tegningen.");
            ctx.render("dashboard/dashboard-error.html");
        }
    }
}
