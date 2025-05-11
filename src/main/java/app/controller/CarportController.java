package app.controller;

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
import app.entities.users.User;
import app.exceptions.DatabaseException;
import app.persistence.mappers.CarportMapper;
import app.persistence.mappers.MaterialMapper;
import app.persistence.mappers.OrderMapper;
import app.persistence.mappers.UserMapper;
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

            // 4. Check if Customer exists or create new
            Customer customer;

            User user = null;
            try {
                user = UserMapper.getUserByEmail(email);
            } catch (DatabaseException e) {
                e.printStackTrace();
                ctx.status(500).result("Database fejl ved hentning af bruger.");
                return;
            }

            if (user != null) {
                if (user instanceof Customer existingCustomer) {
                    customer = existingCustomer;
                    customer.setFirstName(firstName);
                    customer.setLastName(lastName);
                    customer.setPhone(phone);
                    customer.setAddress(address);
                    customer.setPostcode(postcode);
                    customer.setCity(city);

                    UserMapper.updateUser(customer);
                } else {
                    ctx.status(400).result("Emailen tilhører en bruger som ikke er en kunde.");
                    return;
                }
            } else {
                customer = new Customer(firstName, lastName, address, postcode, city, phone, email, 1);
                UserService.createUser(customer);
            }

            // 5. Create carport object
            Carport carport = new Carport(width, length, height, roofType, roofAngle);
            Map<MaterialRole, Integer> materialId = new HashMap<>();
            materialId.put(MaterialRole.POST, postId);
            materialId.put(MaterialRole.BEAM, beamId);
            materialId.put(MaterialRole.RAFTER, rafterId);
            materialId.put(MaterialRole.FASCIA, fasciaId);
            materialId.put(MaterialRole.ROOF_COVER, roofCoverId);
            CarportService.saveCarport(carport, materialId);

            // 6. Get Staff
            Staff currentStaff = ctx.sessionAttribute("currentUser");

            // 7. Create order
            Order order = new Order(customer);
            order.addOrderItem(new OrderItem(carport, 1));
            order.setOrderStatus("Forespørgsel");
            order.setOrderDate(LocalDate.now());
            order.setStaff(currentStaff);
            OrderService.saveOrder(order);

            // 8. Assign to staff
            currentStaff.getMyWorkOrders().add(order);

            // 9. Redirect
            ctx.redirect("/dashboard/order?orderId=" + order.getOrderId());

        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500).result("Der opstod en fejl ved oprettelsen af carporten.");
        }
    }

    public static void showCarportConfiguration(Context ctx) throws DatabaseException {
        int orderId = Integer.parseInt(ctx.queryParam("orderId"));
        int index = Integer.parseInt(ctx.queryParam("index"));

        Order order = OrderMapper.getOrderByOrderId(orderId);
        Carport carport = (Carport) order.getOrderItems().get(index).getProduct();

        // Load material options
        MaterialService.refreshMaterials();
        List<Post> posts = MaterialService.getAllPosts();
        List<Beam> beams = MaterialService.getAllBeams();
        List<Rafter> rafters = MaterialService.getAllRafters();
        List<Fascia> fascias = MaterialService.getAllFascias();
        List<RoofCover> roofCovers = MaterialService.getAllRoofCovers();

        // Get Carport Materials for model
        Material selectedPost = carport.getMaterialMap().get(MaterialRole.POST);
        Material selectedBeam = carport.getMaterialMap().get(MaterialRole.BEAM);
        Material selectedRafter = carport.getMaterialMap().get(MaterialRole.RAFTER);
        Material selectedFascia = carport.getMaterialMap().get(MaterialRole.FASCIA);
        Material selectedRoofCover = carport.getMaterialMap().get(MaterialRole.ROOF_COVER);

        // Build model
        Map<String, Object> model = createBaseModel(ctx);
        model.put("order", order);
        model.put("carport", carport);

        model.put("posts", posts);
        model.put("beams", beams);
        model.put("rafters", rafters);
        model.put("fascias", fascias);
        model.put("roofCovers", roofCovers);

        model.put("selectedPost", selectedPost);
        model.put("selectedBeam", selectedBeam);
        model.put("selectedRafter", selectedRafter);
        model.put("selectedFascia", selectedFascia);
        model.put("selectedRoofCover", selectedRoofCover);

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

        // Rebuild the Carport object
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

            model.put("activeTab", "orders");

            double totalPrice = bom.calcTotalPrice();

            model.put("totalPrice", totalPrice);

            ctx.render("dashboard/dashboard-carport-bom.html", model);
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500).result("Kunne ikke hente stykliste.");
        }
    }
}
