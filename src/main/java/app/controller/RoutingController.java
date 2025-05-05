package app.controller;

import io.javalin.Javalin;

public class RoutingController {
    public static void routingController(Javalin app) {
        app.get("/", ctx -> ctx.render("dashboard/dashboard.html"));



        //Dashboard routing
        app.get("/dashboard/", ctx -> ctx.render("dashboard/dashboard.html"));
        app.get("/dashboard/new-order", DashboardController::newOrder);
        app.get("/dashboard/orders", DashboardController::showOrders);
        app.get("/dashboard/customers", DashboardController::showCustomers);
        app.get("/dashboard/materials", DashboardController::showMaterials);
        app.get("/dashboard/profile", DashboardController::showProfile);
    }
}
