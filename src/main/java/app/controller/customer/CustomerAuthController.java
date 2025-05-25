package app.controller.customer;

import app.entities.users.Customer;
import app.entities.users.User;
import app.exceptions.DatabaseException;
import app.persistence.mappers.UserMapper;
import io.javalin.http.Context;

public class CustomerAuthController {
    public static void showLoginPage(Context ctx) {
        ctx.render("public/login");
    }

    public static void handleLogin(Context ctx) {
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        try {
            // 1. Check if user exists and credentials match
            boolean valid = UserMapper.verifyUser(email, password);
            if (!valid) {
                ctx.attribute("loginError", "Forkert email eller adgangskode");
                ctx.render("public/login");
                return;
            }

            // 2. Get full user
            User user = UserMapper.getUserByEmail(email);

            // 3. Ensure it's a Customer (not Staff)
            if (!(user instanceof Customer customer)) {
                ctx.render("index.html");
                return;
            }

            // 4. Store in session
            ctx.sessionAttribute("currentUser", customer);

            // 5. Redirect to dashboard
            ctx.redirect("/customer/dashboard");

        } catch (DatabaseException e) {
            ctx.attribute("errorMessage", "Der opstod en fejl under login – prøv igen senere.");
            ctx.render("public/public-error.html");
        }
    }
}
