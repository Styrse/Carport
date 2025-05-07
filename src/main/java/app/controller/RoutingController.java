package app.controller;

import io.javalin.Javalin;

public class RoutingController {
    public static void routingController(Javalin app) {
        app.get("/", ctx -> ctx.render("dashboard/dashboard.html"));



        //Dashboard routing
        app.get("/dashboard/login", DashboardController::login);
        app.get("/dashboard/", DashboardController::dashboard);
        app.get("/dashboard/new-carport", DashboardController::newCarport);
        app.get("/dashboard/orders", DashboardController::showOrders);
        app.get("/dashboard/customers", DashboardController::showCustomers);
        app.get("/dashboard/materials", DashboardController::showMaterials);
        app.get("/dashboard/profile", DashboardController::showProfile);
        app.get("/dashboard/logout", DashboardController::logout);
        app.get("/dashboard/new-material", DashboardController::newMaterial);
        app.post("/dashboard/materials/create", DashboardController::createMaterial);
        app.get("/dashboard/edit-material", DashboardController::editMaterial);
        app.post("/dashboard/update", DashboardController::updateMaterial);
        app.post("/dashboard/materials/delete", DashboardController::deleteMaterial);

    }
}
