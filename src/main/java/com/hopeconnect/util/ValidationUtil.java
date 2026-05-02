package com.hopeconnect.util;

import java.util.regex.Pattern;

/**
 * ValidationUtil
 * Simple validation helpers for common fields.
 */
public class ValidationUtil {

    private static final Pattern EMAIL_RE = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final Pattern PHONE_RE = Pattern.compile("^\\+?[0-9]{7,15}$");
    private static final Pattern NAME_RE = Pattern.compile("^[A-Za-z ]{1,150}$");

    /**
     * Validates basic email format.
     */
    public static boolean isValidEmail(String email) {
        if (email == null) return false;
        return EMAIL_RE.matcher(email).matches();
    }

    /**
     * Validates phone number. Accepts optional leading + and 7-15 digits.
     */
    public static boolean isValidPhone(String phone) {
        if (phone == null) return false;
        return PHONE_RE.matcher(phone).matches();
    }

    /**
     * Validates that a name contains only letters and spaces.
     */
    public static boolean isValidName(String name) {
        if (name == null) return false;
        return NAME_RE.matcher(name).matches();
    }

    /**
     * Validates age is between 1 and 120.
     */
    public static boolean isValidAge(int age) {
        return age >= 1 && age <= 120;
    }
}