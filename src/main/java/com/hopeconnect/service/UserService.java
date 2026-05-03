package com.hopeconnect.service;

import com.hopeconnect.dao.UserDAO;
import com.hopeconnect.model.User;
import com.hopeconnect.util.PasswordUtil;
import com.hopeconnect.util.ValidationUtil;

/**
 * UserService
 * Provides higher-level user operations like registration and login.
 */
public class UserService {

    private final UserDAO userDAO = new UserDAO();

    /**
     * Registers a new user after validating input and checking uniqueness.
     * Returns success message or throws IllegalArgumentException with a clear message.
     */
    public String registerUser(User user) throws IllegalArgumentException {
        if (user == null) throw new IllegalArgumentException("User cannot be null");
        if (!ValidationUtil.isValidEmail(user.getEmail())) throw new IllegalArgumentException("Invalid email format");
        if (!ValidationUtil.isValidPhone(user.getPhone())) throw new IllegalArgumentException("Invalid phone format");
        if (!ValidationUtil.isValidName(user.getFullName())) throw new IllegalArgumentException("Invalid name format");
        if (user.getAge() == null || !ValidationUtil.isValidAge(user.getAge())) throw new IllegalArgumentException("Invalid age");
        // Check uniqueness
        if (userDAO.findByEmail(user.getEmail()) != null) throw new IllegalArgumentException("Email already registered");
        if (userDAO.findByPhone(user.getPhone()) != null) throw new IllegalArgumentException("Phone number already registered");

        // Hash password
        String hashed = PasswordUtil.hashPassword(user.getPasswordHash());
        user.setPasswordHash(hashed);

        int id = userDAO.insert(user);
        if (id <= 0) throw new IllegalArgumentException("Registration failed due to server error");
        return "Registration successful";
    }

    /**
     * Attempts to log in a user using email and password.
     * Returns the User object on success or throws IllegalArgumentException on failure.
     */
    public User loginUser(String email, String password) throws IllegalArgumentException {
        if (!ValidationUtil.isValidEmail(email)) throw new IllegalArgumentException("Invalid email format");
        User user = userDAO.findByEmail(email);
        if (user == null) throw new IllegalArgumentException("Invalid credentials");
        boolean ok;
        try {
            ok = PasswordUtil.verifyPassword(password, user.getPasswordHash());
        } catch (IllegalArgumentException e) {
            ok = false;
        }
        if (!ok) {
            // legacy SHA-256 fallback
            ok = PasswordUtil.verifyLegacySha256(password, user.getPasswordHash());
            if (ok) {
                // upgrade hash to BCrypt
                user.setPasswordHash(PasswordUtil.hashPassword(password));
                userDAO.update(user);
            }
        }
        if (!ok) throw new IllegalArgumentException("Invalid credentials");
        if ("suspended".equals(user.getStatus())) throw new IllegalArgumentException("Account is deactivated");
        return user;
    }
}
