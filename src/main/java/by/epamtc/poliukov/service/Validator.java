package by.epamtc.poliukov.service;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Validator {
    private static final String REGEXP = "[\\w\\W]{1,4000}";
    private static final Pattern PATTERN_REGEXP = Pattern.compile(REGEXP);
    private static final String NUMBER = "[\\d]+";
    private static final Pattern PATTERN_NUMBER = Pattern.compile(NUMBER);
    private static final String LOGIN = "[a-zA-Z_0-9]{3,16}";
    private static final Pattern PATTERN_LOGIN = Pattern.compile(LOGIN);
    private static final String EMAIL = "\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,6}";
    private static final String DATE = "^[0-3]?[0-9].[0-3]?[0-9].(?:[0-9]{2})?[0-9]{2}$";
    private static final Pattern PATTERN_DATE = Pattern.compile(DATE);


    public static boolean validate(String... data) {
        Matcher matcher;
        for (String arg : data) {
            matcher = PATTERN_REGEXP.matcher(arg);
            if (!matcher.matches()) {
                return false;
            }
        }
        return true;
    }

    public static boolean validate(int... data) {
        for (int arg : data) {
            if (arg < 0) {
                return false;
            }
        }
        return true;
    }


    public static boolean validateNumber(String data) {
        Matcher matcher;
        matcher = PATTERN_NUMBER.matcher(data);
        return matcher.matches();
    }


    public static boolean validateLogin(String login) {
        Matcher matcher;
        matcher = PATTERN_LOGIN.matcher(login);
        return matcher.matches();
    }

    public static boolean validateDate(String date) {
        Matcher matcher;
        matcher = PATTERN_DATE.matcher(date);
        return matcher.matches();
    }


    public static boolean validatePassword(byte[] password) {
        return password.length > 5;
    }


    public static boolean validatePassword(byte[] password, byte[] passwordrep) {
        return Arrays.equals(password, passwordrep);
    }


    public static boolean validateEmail(String email) {
        return email.matches(EMAIL);
    }

}
