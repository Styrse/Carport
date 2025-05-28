package app.controller.dashboard;

import app.entities.users.Staff;
import app.entities.users.User;
import app.exceptions.DatabaseException;
import app.persistence.mappers.UserMapper;
import app.service.UserService;
import app.utils.PasswordUtil;
import io.javalin.http.Context;

import java.util.List;
import java.util.Map;

import static app.controller.dashboard.ControllerHelper.createBaseModel;

public class StaffController {

    public static void showStaff(Context ctx) {
        try {
            List<User> allUsers = UserMapper.getAllUsers();

            List<Staff> staffList = allUsers.stream()
                    .filter(user -> user instanceof Staff)
                    .map(user -> (Staff) user)
                    .toList();

            Map<String, Object> model = createBaseModel(ctx);
            model.put("staffList", staffList);
            model.put("activeTab", "staff");

            ctx.render("dashboard/dashboard-staff.html", model);
        } catch (DatabaseException e) {
            ctx.attribute("errorMessage", "Kunne ikke hente medarbejderlisten.");
            ctx.render("dashboard/dashboard-error.html");
        }
    }

    public static void createStaff(Context ctx) {
        Map<String, Object> model = createBaseModel(ctx);
        model.put("activeTab", "staff");
        ctx.render("dashboard/dashboard-create-staff.html", model);
    }

    public static void handleCreateStaff(Context ctx) {
        try {
            String password = ctx.formParam("password");
            String passwordRepeat = ctx.formParam("passwordRepeat");

            if (!password.equals(passwordRepeat)) {
                ctx.attribute("errorMessage", "Adgangskoderne matcher ikke.");
                ctx.render("dashboard/dashboard-error.html");
                return;
            }

            String hashedPassword = PasswordUtil.hashPassword(password);

            User staff = UserService.mapUserService(ctx);
            staff.setPassword(hashedPassword);

            UserService.createUser(staff);

            ctx.redirect("/dashboard/staff");

        } catch (Exception e) {
            ctx.attribute("errorMessage", "Kunne ikke oprette medarbejder.");
            ctx.render("dashboard/dashboard-error.html");
        }
    }

    public static void editStaff(Context ctx) {
        try {
            int staffId = Integer.parseInt(ctx.queryParam("staffId"));
            Staff staff = (Staff) UserMapper.getUserById(staffId);

            Map<String, Object> model = createBaseModel(ctx);
            model.put("staff", staff);
            model.put("activeTab", "staff");

            ctx.render("dashboard/dashboard-edit-staff.html", model);

        } catch (Exception e) {
            ctx.attribute("errorMessage", "Kunne ikke hente medarbejderdata.");
            ctx.render("dashboard/dashboard-error.html");
        }
    }

    public static void updateStaff(Context ctx) {
        try {
            int userId = Integer.parseInt(ctx.formParam("userId"));
            String firstName = ctx.formParam("firstName");
            String lastName = ctx.formParam("lastName");
            String phone = ctx.formParam("phoneNumber");
            String email = ctx.formParam("email");

            // Get existing user
            User existing = UserMapper.getUserById(userId);
            if (existing == null || !(existing instanceof Staff)) {
                ctx.attribute("errorMessage", "Brugeren blev ikke fundet eller er ikke en medarbejder.");
                ctx.render("dashboard/dashboard-error.html");
                return;
            }

            Staff staff = (Staff) existing;

            // Update attributes
            staff.setFirstName(firstName);
            staff.setLastName(lastName);
            staff.setPhone(phone);
            staff.setEmail(email);

            // Save changes
            UserMapper.updateUser(staff);
            ctx.redirect("/dashboard/staff");

        } catch (Exception e) {
            ctx.attribute("errorMessage", "Fejl ved opdatering af medarbejder.");
            ctx.render("dashboard/dashboard-error.html");
        }
    }

    public static void deleteStaff(Context ctx) {
        try {
            int staffId = Integer.parseInt(ctx.formParam("userId"));
            UserMapper.deleteUserByUserId(staffId);
            ctx.redirect("/dashboard/staff");
        } catch (Exception e) {
            ctx.attribute("errorMessage", "Fejl ved sletning af medarbejder.");
            ctx.render("dashboard/dashboard-error.html");
        }
    }
}
