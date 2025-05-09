package app.controller;

import app.entities.users.Staff;
import app.entities.users.User;
import app.exceptions.DatabaseException;
import app.persistence.mappers.UserMapper;
import app.service.UserService;
import io.javalin.http.Context;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static app.controller.ControllerHelper.createBaseModel;

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
            e.printStackTrace();
            ctx.status(500).result("Kunne ikke hente medarbejderlisten.");
        }
    }

    public static void createStaff(Context ctx) {
        Map<String, Object> model = createBaseModel(ctx);

        model.put("activeTab", "staff");

        ctx.render("dashboard/dashboard-create-staff.html", model);
    }

    public static void handleCreateStaff(Context ctx) throws SQLException, DatabaseException {
        String password = ctx.formParam("password");
        String passwordRepeat = ctx.formParam("passwordRepeat");

        //Password mismatch check
        if (!password.equals(passwordRepeat)) {
            ctx.status(400).result("Adgangskoderne matcher ikke.");
            return;
        }

        User staff = UserService.mapUserService(ctx);


        UserService.createUser(staff);
        ctx.redirect("/dashboard/staff");
    }
}
