package app.controller;

import app.entities.products.materials.planks.Beam;
import app.entities.products.materials.planks.Fascia;
import app.entities.products.materials.planks.Post;
import app.entities.products.materials.planks.Rafter;
import app.entities.products.materials.roof.RoofCover;
import app.service.MaterialService;
import io.javalin.http.Context;

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

        ctx.render("dashboard/dashboard-new-carport.html", model);
    }

    public static void handleNewCarport(Context ctx) {
        int width = Integer.parseInt(ctx.formParam("width"));
        int length = Integer.parseInt(ctx.formParam("length"));
        int height = Integer.parseInt(ctx.formParam("height"));
        String roofType = ctx.formParam("roofType");
        int slope = Integer.parseInt(ctx.formParam("slope"));

        int postId = Integer.parseInt(ctx.formParam("postId"));
        int beamId = Integer.parseInt(ctx.formParam("beamId"));
        int rafterId = Integer.parseInt(ctx.formParam("rafterId"));
        int fasciaId = Integer.parseInt(ctx.formParam("fasciaId"));
        int roofCoverId = Integer.parseInt(ctx.formParam("roofCoverId"));

        String firstName = ctx.formParam("firstName");
        String lastName = ctx.formParam("lastName");
        String phone = ctx.formParam("phone");
        String email = ctx.formParam("email");
        String address = ctx.formParam("address");
        String postcode = ctx.formParam("postcode");
        String city = ctx.formParam("city");

        ctx.redirect("/dashboard/orders");
    }
}
