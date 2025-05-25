package app.controller;

import app.controller.customer.*;
import app.controller.dashboard.*;
import app.entities.users.Staff;
import app.entities.users.User;
import io.javalin.Javalin;

public class RoutingController {

    public static void routingController(Javalin app) {

        // 🌐 Public Pages (accessible without login)
        app.get("/", ctx -> ctx.render("index.html"));
        app.get("/fog", PublicController::showHomepage);

        // 🚧 Carport Order Flow (multi-step form)
        app.get("/carport/step-1", CarportOrderController::showSizePage);
        app.post("/carport/step-1", CarportOrderController::handleRoofSelection);
        app.post("/carport/step-1-handle", CarportOrderController::handleStep1);
        app.get("/carport/step-1.1", CarportOrderController::showShedSizePage);
        app.post("/carport/step-1.1-handle", CarportOrderController::handleShedSize);
        app.get("/carport/step-2", CarportOrderController::showMaterialsPage);
        app.post("/carport/step-2", CarportOrderController::handleStep2);
        app.get("/carport/step-3", CarportOrderController::showContactInfoPage);
        app.post("/carport/step-3", CarportOrderController::handleContactInfo);
        app.get("/carport/step-4", CarportOrderController::showConfirmationPage);
        app.post("/carport/submit", CarportOrderController::submitOrder);
        app.get("/carport/thank-you", CarportOrderController::showThankYouPage);

        // 🔐 Customer Login
        app.get("/customer/login", CustomerAuthController::showLoginPage);
        app.post("/customer/login", CustomerAuthController::handleLogin);

        // 👤 Customer Dashboard & Account
        app.get("/customer/dashboard", CustomerDashboardController::showDashboard);
        app.get("/customer/edit", CustomerDashboardController::showEditPage);
        app.post("/customer/edit", CustomerDashboardController::handleEdit);
        app.get("/order/{orderId}", CustomerDashboardController::showOrderDetails);

        // 🛡️ Access Control for Staff Dashboard
        // Redirects non-logged-in or non-staff users away from /dashboard/*
        app.before("/dashboard/*", ctx -> {
            User user = ctx.sessionAttribute("currentUser");
            if (user == null || !(user instanceof Staff)) {
                ctx.redirect("/login");
                return;
            }
        });

        // 🔑 Staff Authentication
        app.get("/login", AuthController::login);
        app.post("/login", AuthController::handleLogin);
        app.get("/dashboard/logout", AuthController::logout);

        // 🏠 Staff Dashboard Home
        app.get("/dashboard/", DashboardController::dashboard);

        // 👤 Staff Profile Page
        app.get("/dashboard/profile", ProfileController::showProfile);
        app.post("/dashboard/profile/update", ProfileController::updateProfile);

        // 📦 Order Management
        app.get("/dashboard/orders", OrderController::showOrders);
        app.get("/dashboard/order", OrderController::showOrderDetails);
        app.post("/dashboard/orders/cancel", OrderController::cancelOrder);
        app.post("/dashboard/orders/assign", OrderController::assignOrderToStaff);
        app.post("/dashboard/orders/remove", OrderController::removeStaffFromOrder);

        // 🧾 Customer Management
        app.get("/dashboard/customers", CustomerController::showCustomers);
        app.post("/dashboard/customer", CustomerController::showCustomerPage);
        app.post("/dashboard/customers/update", CustomerController::updateCustomerInfo);

        // 🪚 Material Management
        app.get("/dashboard/materials", MaterialController::showMaterials);
        app.get("/dashboard/new-material", MaterialController::newMaterial);
        app.get("/dashboard/edit-material", MaterialController::editMaterial);
        app.post("/dashboard/materials/create", MaterialController::createMaterial);
        app.post("/dashboard/update", MaterialController::updateMaterial);
        app.post("/dashboard/materials/delete", MaterialController::deleteMaterial);

        // 🧑‍💼 Staff Management (for managers)
        app.get("/dashboard/staff", StaffController::showStaff);
        app.get("/dashboard/staff/create", StaffController::createStaff);
        app.post("/dashboard/staff/create", StaffController::handleCreateStaff);
        app.get("/dashboard/staff/edit", StaffController::editStaff);
        app.post("/dashboard/staff/update", StaffController::updateStaff);
        app.post("/dashboard/staff/delete", StaffController::deleteStaff);

        // 🏗️ Carport Management (backend)
        app.get("/dashboard/new-carport", CarportController::newCarport);
        app.post("/dashboard/configure-carport", CarportController::handleNewCarport);
        app.get("/dashboard/carport", CarportController::showCarportConfiguration);
        app.post("/dashboard/carport/update", CarportController::updateCarport);
        app.get("/dashboard/carport/bom", CarportController::showBillOfMaterials);
        app.get("/dashboard/carport/drawing", CarportController::showSVG);

        // 📧 Email Features
        app.get("/dashboard/email-form", EmailController::showEmailForm);
        app.post("/dashboard/send-email", EmailController::sendEmail);
        app.post("/dashboard/carport/payment-link", EmailController::sendPaymentLinkEmail);

        // 💳 Payment Flow
        app.get("/payment", PaymentController::showPaymentPage);
        app.post("/payment/confirm", PaymentController::confirmPayment);
    }
}
