package app.controller;

import app.entities.users.Staff;
import app.entities.users.User;
import io.javalin.Javalin;

public class RoutingController {

    public static void routingController(Javalin app) {

        // 🔐 Access Control for Dashboard
        app.before("/dashboard/*", ctx -> {
            User user = ctx.sessionAttribute("currentUser");
            if (user == null || !(user instanceof Staff)) {
                ctx.redirect("/login");
                return;
            }
        });

        // 🔑 Authentication
        app.get("/login", DashboardController::login);
        app.post("/login", DashboardController::handleLogin);
        app.get("/dashboard/logout", DashboardController::logout);

        // 🏠 Dashboard
        app.get("/dashboard/", DashboardController::dashboard);

        // 👤 Profile
        app.get("/dashboard/profile", DashboardController::showProfile);
        app.post("/dashboard/profile/update", DashboardController::updateProfile);

        // 🛒 Orders
        app.get("/dashboard/orders", DashboardController::showOrders);
        app.get("/dashboard/customers", DashboardController::showCustomers);
        app.post("/dashboard/customer", DashboardController::showCustomerPage);
        app.post("/dashboard/customers/update", DashboardController::updateCustomerInfo);
        app.get("/dashboard/order", DashboardController::showOrderDetails);


        // 🪵 Materials
        app.get("/dashboard/materials", DashboardController::showMaterials);
        app.get("/dashboard/new-material", DashboardController::newMaterial);
        app.get("/dashboard/edit-material", DashboardController::editMaterial);
        app.post("/dashboard/materials/create", DashboardController::createMaterial);
        app.post("/dashboard/update", DashboardController::updateMaterial);
        app.post("/dashboard/materials/delete", DashboardController::deleteMaterial);

        // 🧑‍💼 Staff Management (Managers only)
        app.get("/dashboard/staff", DashboardController::showStaff);
        app.get("/dashboard/staff/create", DashboardController::createStaff);
        app.post("/dashboard/staff/create", DashboardController::handleCreateStaff);

        // 🏗️ Carport Configuration
        app.get("/dashboard/new-carport", DashboardController::newCarport);
    }
}
