package app.controller;

import app.entities.products.materials.Material;
import app.entities.products.materials.planks.Beam;
import app.entities.products.materials.planks.Fascia;
import app.entities.products.materials.planks.Post;
import app.entities.products.materials.planks.Rafter;
import app.entities.products.materials.roof.RoofCover;
import app.exceptions.DatabaseException;
import app.persistence.mappers.MaterialMapper;
import app.service.MaterialService;
import app.utils.NumberUtil;
import io.javalin.http.Context;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static app.controller.ControllerHelper.createBaseModel;
import static app.utils.NumberUtil.parseFloat;

public class MaterialController {
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
}
