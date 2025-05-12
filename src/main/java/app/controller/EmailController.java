package app.controller;

import app.entities.users.User;
import app.persistence.mappers.UserMapper;
import app.utils.SendGrid;
import io.javalin.http.Context;

import java.util.Map;

public class EmailController {

    public static void showEmailForm(Context ctx) {
        try {
            String email = ctx.queryParam("email");

            User user = UserMapper.getUserByEmail(email);
            User staff = ctx.sessionAttribute("currentUser");

            Map<String, Object> model = ControllerHelper.createBaseModel(ctx);
            model.put("email", email);
            model.put("customerName", user.getFirstName() + " " + user.getLastName());
            model.put("staffName", staff.getFirstName());
            model.put("staffEmail", staff.getEmail());
            model.put("staffPhone", staff.getPhone());
            model.put("activeTab", "customers");

            ctx.render("dashboard/dashboard-send-email.html", model);
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(400).result("Ugyldige parametre.");
        }
    }

    public static void sendEmail(Context ctx) {
        try {
            String email = ctx.formParam("email");
            int orderId = Integer.parseInt(ctx.formParam("orderId"));
            String subject = ctx.formParam("subject");
            String message = ctx.formParam("message");

            SendGrid.sendMessage(email, subject, message);

            ctx.redirect("/dashboard/order?orderId=" + orderId + "&success=1");
        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500).result("Kunne ikke sende email.");
        }
    }
}

