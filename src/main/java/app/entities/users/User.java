package app.entities.users;

public abstract class User {
    private int userId;
    private String firstName;
    private String lastName;
    private String address;
    private String postcode; //String for foreign addresses
    private String city;
    private String phone;    //Stored as String for future-proof for international phone numbers
    private String email;
    private String password;
    private int userRole;

    public User() {
    }



    public User(String firstName, String lastName, String address, String postcode, String city, String phone, String email, String password, int userRole) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.postcode = postcode;
        this.city = city;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
    }

    public User(String firstName, String lastName, String phone, String email, String password, int userRole) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
    }

    public User(int userId, String firstName, String lastName, String phone, String email, String password, int userRole) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
    }

    public User(String firstName, String lastName, String address, String postcode, String city, String phone, String email, int userRole) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.postcode = postcode;
        this.city = city;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
    }

    public User(int userId, String firstName, String lastName, String address, String postcode, String city, String phone, String email, String password, int userRole) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.postcode = postcode;
        this.city = city;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
    }

    public int getUserId() {
        return userId;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setUserId(int userID) {
        this.userId = userID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserRole() {
        return userRole;
    }

    public void setUserRole(int userRole) {
        this.userRole = userRole;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
