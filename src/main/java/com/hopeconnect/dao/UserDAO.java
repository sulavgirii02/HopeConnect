package com.hopeconnect.dao;

import com.hopeconnect.model.User;
import com.hopeconnect.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * UserDAO
 * Data Access Object for users table. Provides CRUD operations.
 */
public class UserDAO {

    /**
     * Inserts a new User into the database.
     * Returns generated id on success, -1 on failure.
     */
    public int insert(User user) {
        String sql = "INSERT INTO users (full_name,email,phone,password_hash,age,role,status) VALUES (?,?,?,?,?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, new String[]{"id"})) {
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getPasswordHash());
            if (user.getAge() != null) ps.setInt(5, user.getAge()); else ps.setNull(5, Types.INTEGER);
            ps.setString(6, user.getRole());
            ps.setString(7, user.getStatus());
            int affected = ps.executeUpdate();
            if (affected == 0) return -1;
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("User insert failed: " + e.getMessage());
        }
        return -1;
    }

    /**
     * Finds a User by id.
     */
    public User findById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User u = new User();
                    u.setId(rs.getInt("id"));
                    u.setFullName(rs.getString("full_name"));
                    u.setEmail(rs.getString("email"));
                    u.setPhone(rs.getString("phone"));
                    u.setPasswordHash(rs.getString("password_hash"));
                    int age = rs.getInt("age"); if (!rs.wasNull()) u.setAge(age);
                    u.setRole(rs.getString("role"));
                    u.setStatus(rs.getString("status"));
                    u.setCreatedAt(rs.getTimestamp("created_at"));
                    u.setUpdatedAt(rs.getTimestamp("updated_at"));
                    return u;
                }
            }
        } catch (SQLException e) {
            System.err.println("User findById failed: " + e.getMessage());
        }
        return null;
    }

    /**
     * Finds a user by email address.
     */
    public User findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User u = new User();
                    u.setId(rs.getInt("id"));
                    u.setFullName(rs.getString("full_name"));
                    u.setEmail(rs.getString("email"));
                    u.setPhone(rs.getString("phone"));
                    u.setPasswordHash(rs.getString("password_hash"));
                    int age = rs.getInt("age"); if (!rs.wasNull()) u.setAge(age);
                    u.setRole(rs.getString("role"));
                    u.setStatus(rs.getString("status"));
                    u.setCreatedAt(rs.getTimestamp("created_at"));
                    u.setUpdatedAt(rs.getTimestamp("updated_at"));
                    return u;
                }
            }
        } catch (SQLException e) {
            System.err.println("User findByEmail failed: " + e.getMessage());
        }
        return null;
    }

    /**
     * Finds a user by phone number.
     */
    public User findByPhone(String phone) {
        String sql = "SELECT * FROM users WHERE phone = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, phone);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User u = new User();
                    u.setId(rs.getInt("id"));
                    u.setFullName(rs.getString("full_name"));
                    u.setEmail(rs.getString("email"));
                    u.setPhone(rs.getString("phone"));
                    u.setPasswordHash(rs.getString("password_hash"));
                    u.setRole(rs.getString("role"));
                    u.setStatus(rs.getString("status"));
                    u.setCreatedAt(rs.getTimestamp("created_at"));
                    u.setUpdatedAt(rs.getTimestamp("updated_at"));
                    return u;
                }
            }
        } catch (SQLException e) {
            System.err.println("User findByPhone failed: " + e.getMessage());
        }
        return null;
    }

    /**
     * Returns all users.
     */
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY id DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setFullName(rs.getString("full_name"));
                u.setEmail(rs.getString("email"));
                u.setPhone(rs.getString("phone"));
                int age = rs.getInt("age"); if (!rs.wasNull()) u.setAge(age);
                u.setRole(rs.getString("role"));
                u.setStatus(rs.getString("status"));
                u.setCreatedAt(rs.getTimestamp("created_at"));
                u.setUpdatedAt(rs.getTimestamp("updated_at"));
                list.add(u);
            }
        } catch (SQLException e) {
            System.err.println("User findAll failed: " + e.getMessage());
        }
        return list;
    }

    /**
     * Updates an existing user record.
     * Returns true if update affected a row.
     */
    public boolean update(User user) {
        String sql = "UPDATE users SET full_name=?,email=?,phone=?,password_hash=?,age=?,role=?,status=?,updated_at=NOW() WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getPasswordHash());
            if (user.getAge() != null) ps.setInt(5, user.getAge()); else ps.setNull(5, Types.INTEGER);
            ps.setString(6, user.getRole());
            ps.setString(7, user.getStatus());
            ps.setInt(8, user.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("User update failed: " + e.getMessage());
        }
        return false;
    }

    /**
     * Deletes a user record by id.
     * Returns true if deletion affected a row.
     */
    public boolean delete(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("User delete failed: " + e.getMessage());
        }
        return false;
    }
}
