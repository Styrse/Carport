package app.controller;

import app.entities.users.User;
import app.persistence.mappers.UserMapper;
import app.utils.PasswordUtil;
import io.javalin.http.Context;

import java.util.Map;

import static app.controller.ControllerHelper.createBaseModel;

public class ProfileController {
    public static void showProfile(Context ctx) {
        Map<String, Object> model = createBaseModel(ctx);

        model.put("activeTab", "profile");

        ctx.render("dashboard/dashboard-profile.html", model);
    }

    public static void updateProfile(Context ctx) {
        User user = ctx.sessionAttribute("currentUser");
        if (user == null) {
            ctx.redirect("/login");
            return;
        }

        String firstName = ctx.formParam("firstName");
        String lastName = ctx.formParam("lastName");
        String phone = ctx.formParam("phoneNumber");
        String email = ctx.formParam("email");
        String currentPassword = ctx.formParam("currentPassword");

        String newPassword = ctx.formParam("newPassword");
        String repeatPassword = ctx.formParam("newPasswordRepeat");

        try {
            // Verify current password
            if (!PasswordUtil.checkPassword(currentPassword, user.getPassword())) {
                ctx.status(400).result("Forkert nuværende adgangskode.");
                return;
            }

            // Update fields
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPhone(phone);
            user.setEmail(email);

            String newHashedPassword = null;
            if (newPassword != null && !newPassword.isBlank()) {
                if (!newPassword.equals(repeatPassword)) {
                    ctx.status(400).result("De nye adgangskoder matcher ikke.");
                    return;
                }
                newHashedPassword = PasswordUtil.hashPassword(newPassword);
            }

            user.setPassword(newHashedPassword);

            UserMapper.updateUser(user);

            // Refresh session
            ctx.sessionAttribute("currentUser", UserMapper.getUserByEmail(user.getEmail()));
            ctx.redirect("/dashboard/profile");

        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500).result("Kunne ikke opdatere profil.");
        }
    }
}
