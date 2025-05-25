package app.controller.dashboard;

import app.entities.products.materials.Material;
import app.entities.products.materials.planks.Beam;
import app.entities.products.materials.planks.Fascia;
import app.entities.products.materials.planks.Post;
import app.entities.products.materials.planks.Rafter;
import app.entities.products.materials.roof.RoofCover;
import app.persistence.mappers.MaterialMapper;
import app.service.MaterialService;
import io.javalin.http.Context;

import java.util.List;
import java.util.Map;

import static app.controller.dashboard.ControllerHelper.createBaseModel;
import static app.utils.NumberUtil.parseFloat;

public class MaterialController {

    public static void showMaterials(Context ctx) {
        try {
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
            model.put("activeTab", "materials");

            ctx.render("dashboard/dashboard-materials.html", model);

        } catch (Exception e) {
            ctx.attribute("errorMessage", "Kunne ikke indlæse materialer.");
            ctx.render("dashboard/dashboard-error.html");
        }
    }

    public static void newMaterial(Context ctx) {
        Map<String, Object> model = createBaseModel(ctx);
        model.put("activeTab", "materials");
        ctx.render("dashboard/dashboard-new-material.html", model);
    }

    public static void createMaterial(Context ctx) {
        Material material = MaterialService.extractMaterialFromForm(ctx);

        if (material == null) {
            ctx.status(400).result("Ugyldigt input.");
            return;
        }

        try {
            MaterialMapper.createMaterial(material);
            MaterialService.refreshMaterials();
            ctx.redirect("/dashboard/materials");
        } catch (Exception e) {
            ctx.attribute("errorMessage", "Fejl under oprettelse af materiale.");
            ctx.render("dashboard/dashboard-error.html");
        }
    }

    public static void editMaterial(Context ctx) {
        try {
            int itemId = Integer.parseInt(ctx.queryParam("id"));
            Material material = MaterialMapper.getMaterialById(itemId);

            Map<String, Object> model = createBaseModel(ctx);
            model.put("material", material);
            model.put("activeTab", "materials");

            ctx.render("dashboard/dashboard-edit-material.html", model);
        } catch (Exception e) {
            ctx.attribute("errorMessage", "Materialet blev ikke fundet.");
            ctx.render("dashboard/dashboard-error.html");
        }
    }

    public static void updateMaterial(Context ctx) {
        try {
            Material material = MaterialService.extractMaterialFromForm(ctx);
            material.setItemId(Integer.parseInt(ctx.formParam("materialId")));

            MaterialMapper.updateMaterial(material);
            MaterialService.refreshMaterials();
            ctx.redirect("/dashboard/materials");
        } catch (Exception e) {
            ctx.attribute("errorMessage", "Fejl under opdatering af materiale.");
            ctx.render("dashboard/dashboard-error.html");
        }
    }

    public static void deleteMaterial(Context ctx) {
        try {
            int itemId = Integer.parseInt(ctx.formParam("materialId"));
            MaterialMapper.deleteMaterialById(itemId);
            ctx.redirect("/dashboard/materials");
        } catch (Exception e) {
            ctx.attribute("errorMessage", "Fejl under sletning af materiale.");
            ctx.render("dashboard/dashboard-error.html");
        }
    }
}
