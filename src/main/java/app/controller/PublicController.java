package app.controller;

import app.entities.products.carport.Carport;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.Map;

public class PublicController {
    public static void showHomepage(Context ctx) {
        Map<String, Object> model = new HashMap<>();
        model.put("title", "Velkommen til Fog");
        ctx.render("public/index.html", model);
    }

    public static void handleRoofSelection(Context ctx) {
        String roofType = ctx.formParam("rooftype");

        // Save to session or pass forward
        ctx.sessionAttribute("selectedRoofType", roofType);

        Map<String, Object> model = new HashMap<>();
        model.put("roofType", roofType);
        ctx.render("public/step-1-size.html", model);
    }

    public static void handleStep1(Context ctx) {
        int width = Integer.parseInt(ctx.formParam("width"));
        int length = Integer.parseInt(ctx.formParam("length"));
        int height = Integer.parseInt(ctx.formParam("height"));
        boolean includeShed = ctx.formParam("includeShed") != null;

        Carport carport = new Carport();
        carport.setWidth(width);
        carport.setLength(length);
        carport.setHeight(height);

        //config.setIncludeShed(includeShed);
        //ctx.sessionAttribute("carportConfig", config);

        if (includeShed) {
            ctx.redirect("/carport/step-1.1");
        } else {
            ctx.redirect("/carport/step-2");
        }
    }
}
