package com.hopeconnect.util;

import com.hopeconnect.dao.UserDAO;
import com.hopeconnect.model.User;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * StartupSeeder
 * Runs once when the web app starts. Ensures the default admin
 * account exists with a valid BCrypt password hash.
 *
 * Default admin: admin@hopeconnect.local / Admin@123
 * Default user:  user@hopeconnect.local  / User@123
 */
@WebListener
public class StartupSeeder implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        SchemaMigrator.migrate();

        UserDAO dao = new UserDAO();

        // Seed admin
        if (dao.findByEmail("admin@hopeconnect.local") == null) {
            User admin = new User();
            admin.setFullName("System Admin");
            admin.setEmail("admin@hopeconnect.local");
            admin.setPhone("9800000000");
            admin.setPasswordHash(PasswordUtil.hashPassword("Admin@123"));
            admin.setRole("admin");
            admin.setStatus("active");
            dao.insert(admin);
            System.out.println("[StartupSeeder] Created default admin: admin@hopeconnect.local / Admin@123");
        }

        // Seed demo user
        if (dao.findByEmail("user@hopeconnect.local") == null) {
            User user = new User();
            user.setFullName("Demo User");
            user.setEmail("user@hopeconnect.local");
            user.setPhone("9811111111");
            user.setPasswordHash(PasswordUtil.hashPassword("User@123"));
            user.setAge(30);
            user.setRole("user");
            user.setStatus("active");
            dao.insert(user);
            System.out.println("[StartupSeeder] Created demo user: user@hopeconnect.local / User@123");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // No cleanup needed
    }
}
