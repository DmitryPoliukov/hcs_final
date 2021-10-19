package by.epamtc.poliukov.service;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncryption {
    private PasswordEncryption() {
    }

    public static String encrypt (String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(5));
    }


    public static Boolean isMatch(String plainPassword, String hashedPassword) {
        return (BCrypt.checkpw(plainPassword, hashedPassword));
    }
}
