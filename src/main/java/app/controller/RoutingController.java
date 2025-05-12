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
        app.get("/login", AuthController::login);
        app.post("/login", AuthController::handleLogin);
        app.get("/dashboard/logout", AuthController::logout);

        // 🏠 Dashboard
        app.get("/dashboard/", DashboardController::dashboard);

        // 👤 Profile
        app.get("/dashboard/profile", ProfileController::showProfile);
        app.post("/dashboard/profile/update", ProfileController::updateProfile);

        // 🛒 Orders
        app.get("/dashboard/orders", OrderController::showOrders);
        app.get("/dashboard/order", OrderController::showOrderDetails);
        app.post("/orders/cancel", OrderController::cancelOrder);
        app.post("/orders/assign", OrderController::assignOrderToStaff);
        app.post("/orders/remove", OrderController::removeStaffFromOrder);

        // 💵 Customers
        app.get("/dashboard/customers", CustomerController::showCustomers);
        app.post("/dashboard/customer", CustomerController::showCustomerPage);
        app.post("/dashboard/customers/update", CustomerController::updateCustomerInfo);

        // 🪵 Materials
        app.get("/dashboard/materials", MaterialController::showMaterials);
        app.get("/dashboard/new-material", MaterialController::newMaterial);
        app.get("/dashboard/edit-material", MaterialController::editMaterial);
        app.post("/dashboard/materials/create", MaterialController::createMaterial);
        app.post("/dashboard/update", MaterialController::updateMaterial);
        app.post("/dashboard/materials/delete", MaterialController::deleteMaterial);

        // 🧑‍💼 Staff Management (Managers only)
        app.get("/dashboard/staff", StaffController::showStaff);
        app.get("/dashboard/staff/create", StaffController::createStaff);
        app.post("/dashboard/staff/create", StaffController::handleCreateStaff);
        app.get("/dashboard/staff/edit", StaffController::editStaff);
        app.post("/dashboard/staff/update", StaffController::updateStaff);
        app.post("/dashboard/staff/delete", StaffController::deleteStaff);

        // 🏗️ Carport Configuration
        app.get("/dashboard/new-carport", CarportController::newCarport);
        app.post("/dashboard/configure-carport", CarportController::handleNewCarport);
        app.get("/dashboard/carport", CarportController::showCarportConfiguration);
        app.post("/dashboard/carport/update", CarportController::updateCarport);
        app.get("/dashboard/carport/bom", CarportController::showBillOfMaterials);

        // 📧 Email
        app.get("/dashboard/email-form", EmailController::showEmailForm);
        app.post("/dashboard/send-email", EmailController::sendEmail);
    }
}
