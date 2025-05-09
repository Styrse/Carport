package app.controller;

import app.entities.orders.Order;
import app.entities.users.Staff;
import app.entities.users.StaffManager;
import app.entities.users.User;
import app.exceptions.DatabaseException;
import app.persistence.mappers.OrderMapper;
import app.persistence.mappers.UserMapper;
import app.service.UserService;
import io.javalin.http.Context;

public class AuthController {
    public static void login(Context ctx) {
        ctx.render("dashboard/dashboard-login.html");
    }

    public static void handleLogin(Context ctx) {
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        try {
            boolean valid = UserMapper.verifyUser(email, password);
            if (!valid) {
                ctx.attribute("loginError", "Forkert email eller adgangskode");
                ctx.render("dashboard/dashboard-login.html");
                return;
            }

            User user = UserMapper.getUserByEmail(email);

            if (!(user instanceof Staff staff)) {
                ctx.render("index.html");
                return;
            }

            UserService.getOrdersByStaffId(staff);

            boolean isManager = staff instanceof StaffManager;

            ctx.sessionAttribute("currentUser", staff);
            ctx.sessionAttribute("isManager", isManager);

            ctx.redirect("/dashboard");
        } catch (DatabaseException e) {
            e.printStackTrace();
            ctx.status(500).result("Loginfejl – prøv igen senere.");
        }
    }

    public static void logout(Context ctx) {
        //Destroys the session and removes all attributes
        ctx.req().getSession().invalidate();
        ctx.redirect("/login");
    }
}
