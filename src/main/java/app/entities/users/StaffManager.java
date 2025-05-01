package app.entities.users;

public class StaffManager extends Staff {

    public StaffManager() {
    }

    public StaffManager(String firstName, String lastName, int phoneNumber, String email, String password) {
        super(firstName, lastName, phoneNumber, email, password);
    }

    public StaffManager(int userID, String firstName, String lastName, int phoneNumber, String email, String password) {
        super(userID, firstName, lastName, phoneNumber, email, password);
    }
}
