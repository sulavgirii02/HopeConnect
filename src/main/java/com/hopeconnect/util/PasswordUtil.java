package com.hopeconnect.util;

import org.mindrot.jbcrypt.BCrypt;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * PasswordUtil
 * Utility for hashing and verifying passwords.
 * Uses BCrypt for secure password hashing.
 */
public class PasswordUtil {

    /**
     * Generates a BCrypt hash for the given plaintext password.
     * @param plainText the plain text password
     * @return the BCrypt hash
     */
    public static String hashPassword(String plainText) {
        return BCrypt.hashpw(plainText, BCrypt.gensalt());
    }

    /**
     * Verifies a plain text password against the stored BCrypt hash.
     * @param plainText the plain text password provided
     * @param stored the stored hash
     * @return true if password matches, false otherwise
     */
    public static boolean verifyPassword(String plainText, String stored) {
        if (plainText == null || stored == null) return false;
        return BCrypt.checkpw(plainText, stored);
    }

    /**
     * Verifies a legacy SHA-256 salted hash in the format base64(salt):base64(hash).
     * @param plainText the plain text password provided
     * @param stored the stored legacy hash
     * @return true if password matches, false otherwise
     */
    public static boolean verifyLegacySha256(String plainText, String stored) {
        if (plainText == null || stored == null) return false;
        String[] parts = stored.split(":");
        if (parts.length != 2) return false;
        byte[] salt;
        byte[] hash;
        try {
            salt = Base64.getDecoder().decode(parts[0]);
            hash = Base64.getDecoder().decode(parts[1]);
        } catch (IllegalArgumentException e) {
            return false;
        }
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] computed = md.digest(plainText.getBytes(StandardCharsets.UTF_8));
            if (computed.length != hash.length) return false;
            int diff = 0;
            for (int i = 0; i < computed.length; i++) diff |= computed[i] ^ hash[i];
            return diff == 0;
        } catch (NoSuchAlgorithmException e) {
            return false;
        }
    }
}