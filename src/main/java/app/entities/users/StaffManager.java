package app.entities.users;

public class StaffManager extends Staff {

    public StaffManager() {
    }

    public StaffManager(String firstName, String lastName, String phoneNumber, String email, String password, int roleId) {
        super(firstName, lastName, phoneNumber, email, password, roleId);
    }

    public StaffManager(int userID, String firstName, String lastName, String phoneNumber, String email, String password, int roleId) {
        super(userID, firstName, lastName, phoneNumber, email, password, roleId);
    }
}
