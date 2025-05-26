package app.controller.dashboard;

import app.entities.users.Staff;
import app.entities.users.StaffManager;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.Map;

public class ControllerHelper {

    /**
     * Builds a common model map for dashboard views, including the logged-in staff user
     * and a boolean flag indicating if the user is a manager.
     */

    public static Map<String, Object> createBaseModel(Context ctx) {
        Staff staff = ctx.sessionAttribute("currentUser");
        boolean isManager = staff instanceof StaffManager;

        Map<String, Object> model = new HashMap<>();
        model.put("staff", staff);
        model.put("isManager", isManager);
        return model;
    }
}
