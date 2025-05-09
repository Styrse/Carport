package app.service;

import app.entities.products.materials.Material;
import app.entities.products.materials.planks.Beam;
import app.entities.products.materials.planks.Fascia;
import app.entities.products.materials.planks.Post;
import app.entities.products.materials.planks.Rafter;
import app.entities.products.materials.roof.RoofCover;
import app.exceptions.DatabaseException;
import app.persistence.mappers.MaterialMapper;
import io.javalin.http.Context;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MaterialService {
    private static List<Material> allMaterials = null;

    private static List<Material> materialService() {
        try {
            if (allMaterials == null) {
                allMaterials = MaterialMapper.getAllMaterials();
            }
            return allMaterials;
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void refreshMaterials() {
        try {
            allMaterials = MaterialMapper.getAllMaterials();
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    public static List<Post> getAllPosts() {
        return materialService().stream().filter(Post.class::isInstance).map(Post.class::cast).toList();
    }

    public static List<Beam> getAllBeams() {
        return materialService().stream().filter(Beam.class::isInstance).map(Beam.class::cast).toList();
    }

    public static List<Rafter> getAllRafters() {
        return materialService().stream().filter(Rafter.class::isInstance).map(Rafter.class::cast).toList();
    }

    public static List<Fascia> getAllFascias() {
        return materialService().stream().filter(Fascia.class::isInstance).map(Fascia.class::cast).toList();
    }

    public static List<RoofCover> getAllRoofCovers() {
        return materialService().stream().filter(RoofCover.class::isInstance).map(RoofCover.class::cast).toList();
    }

    public static Material extractMaterialFromForm(Context ctx) {
        String type = ctx.formParam("type");
        String name = ctx.formParam("name");
        String description = ctx.formParam("description");
        float costPrice = Float.parseFloat(ctx.formParam("costPrice"));
        float salesPrice = Float.parseFloat(ctx.formParam("salesPrice"));
        String unit = ctx.formParam("unit");
        float width = Float.parseFloat(ctx.formParam("width"));
        int height = (int) parseOrDefault(ctx.formParam("height"), 0);
        int overlapLength = (int) parseOrDefault(ctx.formParam("overlapLength"), 0);
        int overlapWidth = (int) parseOrDefault(ctx.formParam("overlapWidth"), 0);
        int gapRafters = (int) parseOrDefault(ctx.formParam("gapRafters"), 0);
        float bucklingCapacity = parseOrDefault(ctx.formParam("bucklingCapacity"), 0);
        int postGap = (int) parseOrDefault(ctx.formParam("postGap"), 0);

        List<Integer> preCuts = Arrays.stream(ctx.formParam("preCuts").trim().split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        return switch (type) {
            case "Post" -> new Post(name, description, costPrice, salesPrice, width, unit, preCuts, height, bucklingCapacity);
            case "Beam" -> new Beam(name, description, costPrice, salesPrice, width, unit, preCuts, height, postGap);
            case "Rafter" -> new Rafter(name, description, costPrice, salesPrice, width, unit, preCuts, height);
            case "Fascia" -> new Fascia(name, description, costPrice, salesPrice, width, unit, preCuts, height);
            case "RoofCover" -> new RoofCover(name, description, costPrice, salesPrice, width, unit, preCuts, overlapLength, overlapWidth, gapRafters);
            default -> throw new IllegalArgumentException("Unknown material type: " + type);
        };
    }

    public static float parseOrDefault(String value, int defaultValue) {
        return (value == null || value.isBlank()) ? defaultValue : Float.parseFloat(value);
    }
}
