package app.service;

import app.entities.users.Customer;
import app.entities.users.Staff;
import app.entities.users.StaffManager;
import app.entities.users.User;
import app.exceptions.DatabaseException;
import app.persistence.mappers.UserMapper;
import io.javalin.http.Context;

import java.sql.SQLException;

public class UserService {
    public static void saveCustomer(Customer customer) throws DatabaseException {
        UserMapper.createUser(customer);
    }

    public static User mapUserService(Context ctx) throws SQLException, DatabaseException {
        String firstname = ctx.formParam("firstName");
        String lastname = ctx.formParam("lastName");
        String phone = ctx.formParam("phone");
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");
        String role = ctx.formParam("role");

        return switch (role) {
            case "Customer" -> new Customer(firstname, lastname, phone, email, password, 1);
            case "Staff" -> new Staff(firstname, lastname, phone, email, password, 2);
            case "StaffManager" -> new StaffManager(firstname, lastname, phone, email, password, 3);
            default -> throw new DatabaseException("Unknown user type: " + role);
        };
    }
}
