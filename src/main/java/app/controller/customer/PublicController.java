package app.controller.customer;

import io.javalin.http.Context;

import java.util.HashMap;
import java.util.Map;

public class PublicController {
    public static void showHomepage(Context ctx) {
        Map<String, Object> model = new HashMap<>();
        model.put("title", "Velkommen til Fog");
        ctx.render("public/index.html", model);
    }
}
