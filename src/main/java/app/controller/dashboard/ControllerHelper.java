package app.controller.dashboard;

import app.entities.users.Staff;
import app.entities.users.StaffManager;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.Map;

public class ControllerHelper {

    //Standard model for map in dashboard. Add staff and access level
    public static Map<String, Object> createBaseModel(Context ctx) {
        Staff staff = ctx.sessionAttribute("currentUser");
        boolean isManager = staff instanceof StaffManager;

        Map<String, Object> model = new HashMap<>();
        model.put("staff", staff);
        model.put("isManager", isManager);
        return model;
    }
}
