package app.service;

import app.entities.users.Customer;
import app.entities.users.Staff;
import app.entities.users.StaffManager;
import app.entities.users.User;
import app.exceptions.DatabaseException;
import app.persistence.mappers.OrderMapper;
import app.persistence.mappers.UserMapper;
import app.utils.SendGrid;
import io.javalin.http.Context;

import java.io.IOException;
import java.sql.SQLException;

public class UserService {
    public static void createUser(User user) throws DatabaseException {
        if (user.getPostcode() != null) {
            UserMapper.insertPostcode(user.getPostcode(), user.getCity());
        }
        UserMapper.createUser(user);
    }

    public static void getOrdersByStaffId(Staff user) throws DatabaseException {
        user.setMyWorkOrders(OrderMapper.getOrdersByStaffId(user.getUserId()));
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

    public static Customer getOrCreateCustomer(String firstName, String lastName, String phone,
                                               String email, String address, String postcode,
                                               String city) throws DatabaseException, IOException {

        User user = UserMapper.getUserByEmail(email);

        if (user != null) {
            if (user instanceof Customer existingCustomer) {
                existingCustomer.setFirstName(firstName);
                existingCustomer.setLastName(lastName);
                existingCustomer.setPhone(phone);
                existingCustomer.setAddress(address);
                existingCustomer.setPostcode(postcode);
                existingCustomer.setCity(city);

                UserMapper.updateUser(existingCustomer);
                return existingCustomer;

            } else {
                throw new IllegalArgumentException("Emailen tilhører en bruger som ikke er en kunde.");
            }

        } else {
            String password = firstName + postcode;
            Customer newCustomer = new Customer(firstName, lastName, address, postcode, city, phone, email, password, 1);

            UserService.createUser(newCustomer);
            SendGrid.sendConfirmationEmail(newCustomer);

            return newCustomer;
        }
    }
}
