package app.controller;

import app.entities.users.Staff;
import app.entities.users.User;
import io.javalin.Javalin;

public class RoutingController {

    public static void routingController(Javalin app) {

        app.before("/dashboard/*", ctx -> {
            User user = ctx.sessionAttribute("currentUser");
            if (user == null || !(user instanceof Staff)) {
                ctx.redirect("/login");
                return;
            }
        });


        // Login
        app.get("/login", DashboardController::login);
        app.post("/login", DashboardController::handleLogin);
        app.get("/dashboard/logout", DashboardController::logout);

        // Dashboard protected routes
        app.get("/dashboard/", DashboardController::dashboard);
        app.get("/dashboard/new-carport", DashboardController::newCarport);
        app.get("/dashboard/orders", DashboardController::showOrders);
        app.get("/dashboard/customers", DashboardController::showCustomers);
        app.get("/dashboard/materials", DashboardController::showMaterials);
        app.get("/dashboard/profile", DashboardController::showProfile);
        app.get("/dashboard/new-material", DashboardController::newMaterial);
        app.get("/dashboard/edit-material", DashboardController::editMaterial);
        app.post("/dashboard/materials/create", DashboardController::createMaterial);
        app.post("/dashboard/update", DashboardController::updateMaterial);
        app.post("/dashboard/materials/delete", DashboardController::deleteMaterial);
        app.post("/dashboard/profile/update", DashboardController::updateUserProfile);
    }
}
