package app.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    public static boolean checkPassword(String plainPassword, String hashedPasswordFromDB) {
        boolean result = BCrypt.checkpw(plainPassword, hashedPasswordFromDB);
        return result;
    }

}