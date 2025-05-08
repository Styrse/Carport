package app.controller;
import io.javalin.http.Context;

import java.util.*;

import static app.controller.ControllerHelper.createBaseModel;

public class DashboardController {
    public static void dashboard(Context ctx) {
        Map<String, Object> model = createBaseModel(ctx);

        ctx.render("dashboard/dashboard.html", model);
    }
}
