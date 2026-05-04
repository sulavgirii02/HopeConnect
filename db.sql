-- HopeConnect Database Schema
-- Database: hopeconnect_db

CREATE DATABASE IF NOT EXISTS hopeconnect_db;
USE hopeconnect_db;

-- users: authentication, roles, account status
CREATE TABLE IF NOT EXISTS users (
                                     id INT AUTO_INCREMENT PRIMARY KEY,
                                     full_name VARCHAR(150) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(30) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    age INT DEFAULT NULL,
    role ENUM('admin','user') NOT NULL DEFAULT 'user',
    status ENUM('active','suspended','deleted') NOT NULL DEFAULT 'active',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_users_email (email),
    INDEX idx_users_phone (phone)
    ) ;

-- user_profiles: demographic & socioeconomic data (1:1 with users)
CREATE TABLE IF NOT EXISTS user_profiles (
                                             id INT AUTO_INCREMENT PRIMARY KEY,
                                             user_id INT NOT NULL UNIQUE,
                                             date_of_birth DATE DEFAULT NULL,
                                             gender ENUM('male','female','other') DEFAULT NULL,
    address_line1 VARCHAR(255) DEFAULT NULL,
    address_line2 VARCHAR(255) DEFAULT NULL,
    city VARCHAR(100) DEFAULT NULL,
    state VARCHAR(100) DEFAULT NULL,
    postal_code VARCHAR(20) DEFAULT NULL,
    country VARCHAR(100) DEFAULT NULL,
    household_size SMALLINT UNSIGNED DEFAULT NULL,
    monthly_income DECIMAL(10,2) DEFAULT NULL,
    additional_info TEXT DEFAULT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_user_profiles_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
    INDEX idx_user_profiles_user_id (user_id)
    ) ;

-- aid_programs: welfare programs created by admins
CREATE TABLE IF NOT EXISTS aid_programs (
                                            id INT AUTO_INCREMENT PRIMARY KEY,
                                            title VARCHAR(255) NOT NULL,
    slug VARCHAR(255) NOT NULL UNIQUE,
    description TEXT NOT NULL,
    category VARCHAR(100) DEFAULT NULL,
    eligibility TEXT DEFAULT NULL,
    start_date DATE DEFAULT NULL,
    end_date DATE DEFAULT NULL,
    capacity INT DEFAULT NULL,
    created_by INT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_published TINYINT NOT NULL DEFAULT 0,
    CONSTRAINT fk_aid_programs_creator FOREIGN KEY (created_by) REFERENCES users(id) ON DELETE SET NULL ON UPDATE CASCADE,
    INDEX idx_aid_programs_created_by (created_by)
    );

-- applications: user applications to programs (UNIQUE on user+program)
CREATE TABLE IF NOT EXISTS applications (
                                            id INT AUTO_INCREMENT PRIMARY KEY,
                                            user_id INT NOT NULL,
                                            program_id INT NOT NULL,
                                            applied_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                            status ENUM('pending','approved','rejected','withdrawn') NOT NULL DEFAULT 'pending',
    assigned_officer INT DEFAULT NULL,
    notes TEXT DEFAULT NULL,
    last_updated TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_applications_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_applications_program FOREIGN KEY (program_id) REFERENCES aid_programs(id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_applications_officer FOREIGN KEY (assigned_officer) REFERENCES users(id) ON DELETE SET NULL ON UPDATE CASCADE,
    UNIQUE KEY ux_applications_user_program (user_id, program_id),
    INDEX idx_applications_user_id (user_id),
    INDEX idx_applications_program_id (program_id)
    );

-- notifications: per-user or broadcast notifications
CREATE TABLE IF NOT EXISTS notifications (
                                             id INT AUTO_INCREMENT PRIMARY KEY,
                                             user_id INT DEFAULT NULL,
                                             title VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    type VARCHAR(50) DEFAULT 'info',
    is_read TINYINT(1) NOT NULL DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_notifications_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
    INDEX idx_notifications_user_id (user_id)
    ) ;

-- program_updates: admin announcements tied to a program
CREATE TABLE IF NOT EXISTS program_updates (
                                               id INT AUTO_INCREMENT PRIMARY KEY,
                                               program_id INT NOT NULL,
                                               title VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    created_by INT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_program_updates_program FOREIGN KEY (program_id) REFERENCES aid_programs(id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_program_updates_creator FOREIGN KEY (created_by) REFERENCES users(id) ON DELETE SET NULL ON UPDATE CASCADE,
    INDEX idx_program_updates_program_id (program_id)
    );

-- audit_logs: immutable record of all admin actions
CREATE TABLE IF NOT EXISTS audit_logs (
                                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                          actor_id INT,
                                          action VARCHAR(100) NOT NULL,
    entity_type VARCHAR(100) DEFAULT NULL,
    entity_id INT DEFAULT NULL,
    details TEXT DEFAULT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_audit_logs_actor FOREIGN KEY (actor_id) REFERENCES users(id) ON DELETE SET NULL ON UPDATE CASCADE,
    INDEX idx_audit_logs_actor_id (actor_id),
    INDEX idx_audit_logs_entity (entity_type, entity_id)
    );

-- wishlists: M:N junction table: users bookmarking programs
CREATE TABLE IF NOT EXISTS wishlists (
                                         id INT AUTO_INCREMENT PRIMARY KEY,
                                         user_id INT NOT NULL,
                                         program_id INT NOT NULL,
                                         created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                         CONSTRAINT fk_wishlists_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_wishlists_program FOREIGN KEY (program_id) REFERENCES aid_programs(id) ON DELETE CASCADE ON UPDATE CASCADE,
    UNIQUE KEY ux_wishlists_user_program (user_id, program_id),
    INDEX idx_wishlists_user_id (user_id),
    INDEX idx_wishlists_program_id (program_id)
    );

-- application_documents: supporting files per application
CREATE TABLE IF NOT EXISTS application_documents (
                                                     id INT AUTO_INCREMENT PRIMARY KEY,
                                                     application_id INT NOT NULL,
                                                     file_name VARCHAR(255) NOT NULL,
    file_path VARCHAR(500) NOT NULL,
    file_type VARCHAR(100) DEFAULT NULL,
    uploaded_by INT,
    uploaded_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_application_documents_application FOREIGN KEY (application_id) REFERENCES applications(id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_application_documents_uploader FOREIGN KEY (uploaded_by) REFERENCES users(id) ON DELETE SET NULL ON UPDATE CASCADE,
    INDEX idx_application_documents_application_id (application_id)
    );

-- contact_messages: inquiries from users or anonymous visitors
CREATE TABLE IF NOT EXISTS contact_messages (
                                                id INT AUTO_INCREMENT PRIMARY KEY,
                                                user_id INT DEFAULT NULL,
                                                name VARCHAR(150) DEFAULT NULL,
    email VARCHAR(255) DEFAULT NULL,
    phone VARCHAR(30) DEFAULT NULL,
    subject VARCHAR(255) DEFAULT NULL,
    message TEXT NOT NULL,
    status ENUM('new','responded','closed') NOT NULL DEFAULT 'new',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_contact_messages_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL ON UPDATE CASCADE,
    INDEX idx_contact_messages_user_id (user_id)
    );

-- user_sessions: session/cookie tracking for users
CREATE TABLE IF NOT EXISTS user_sessions (
                                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                             user_id INT NOT NULL,
                                             session_token VARCHAR(255) NOT NULL,
    ip_address VARCHAR(45) DEFAULT NULL,
    user_agent VARCHAR(500) DEFAULT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP NULL DEFAULT NULL,
    is_active TINYINT(1) NOT NULL DEFAULT 1,
    CONSTRAINT fk_user_sessions_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
    UNIQUE KEY ux_user_sessions_token (session_token),
    INDEX idx_user_sessions_user_id (user_id)
    );

-- password_reset_tokens: token-based password reset
CREATE TABLE IF NOT EXISTS password_reset_tokens (
                                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                                     user_id INT NOT NULL,
                                                     token VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP NOT NULL,
    used TINYINT(1) NOT NULL DEFAULT 0,
    CONSTRAINT fk_password_reset_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE,
    INDEX idx_password_reset_user_id (user_id)
    );

-- login_logs: login history & failed attempts
CREATE TABLE IF NOT EXISTS login_logs (
                                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                          user_id INT DEFAULT NULL,
                                          ip_address VARCHAR(45) DEFAULT NULL,
    user_agent VARCHAR(500) DEFAULT NULL,
    success TINYINT(1) NOT NULL DEFAULT 0,
    attempted_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    reason VARCHAR(255) DEFAULT NULL,
    CONSTRAINT fk_login_logs_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL ON UPDATE CASCADE,
    INDEX idx_login_logs_user_id (user_id)
    );

-- search_logs: search query analytics
CREATE TABLE IF NOT EXISTS search_logs (
                                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                           user_id INT DEFAULT NULL,
                                           query_text VARCHAR(500) NOT NULL,
    filters JSON DEFAULT NULL,
    result_count INT DEFAULT NULL,
    searched_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_search_logs_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL ON UPDATE CASCADE,
    INDEX idx_search_logs_user_id (user_id)
    ) ;

-- program_stats: daily snapshots of program analytics
CREATE TABLE IF NOT EXISTS program_stats (
                                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                             program_id INT NOT NULL,
                                             stat_date DATE NOT NULL,
                                             views INT NOT NULL DEFAULT 0,
                                             applications_count INT NOT NULL DEFAULT 0,
                                             wishlists_count INT NOT NULL DEFAULT 0,
                                             created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                             CONSTRAINT fk_program_stats_program FOREIGN KEY (program_id) REFERENCES aid_programs(id) ON DELETE CASCADE ON UPDATE CASCADE,
    UNIQUE KEY ux_program_stats_program_date (program_id, stat_date),
    INDEX idx_program_stats_program_id (program_id)
    ) ;

-- site_content: admin-managed CMS content (About page, etc.)
CREATE TABLE IF NOT EXISTS site_content (
                                            id INT AUTO_INCREMENT PRIMARY KEY,
                                            key_name VARCHAR(150) NOT NULL UNIQUE,
    title VARCHAR(255) DEFAULT NULL,
    body TEXT DEFAULT NULL,
    meta JSON DEFAULT NULL,
    created_by INT DEFAULT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_site_content_creator FOREIGN KEY (created_by) REFERENCES users(id) ON DELETE SET NULL ON UPDATE CASCADE,
    INDEX idx_site_content_key (key_name)
    );

-- application_status_history: historical status changes for applications
CREATE TABLE IF NOT EXISTS application_status_history (
                                                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                                          application_id INT NOT NULL,
                                                          previous_status ENUM('pending','approved','rejected','withdrawn') DEFAULT NULL,
    new_status ENUM('pending','approved','rejected','withdrawn') NOT NULL,
    changed_by INT DEFAULT NULL,
    change_reason TEXT DEFAULT NULL,
    changed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_app_status_history_application FOREIGN KEY (application_id) REFERENCES applications(id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_app_status_history_user FOREIGN KEY (changed_by) REFERENCES users(id) ON DELETE SET NULL ON UPDATE CASCADE,
    INDEX idx_app_status_history_application_id (application_id)
    );

-- Default admin account for first milestone testing.
-- Login email: admin@hopeconnect.com
-- Login password: Admin@123
-- The stored password is BCrypt hashed, not plain text.
INSERT INTO users (full_name, email, phone, password_hash, age, role, status)
VALUES (
           'HopeConnect Admin',
           'admin@hopeconnect.com',
           '+9779800000000',
           '$2a$12$otUOY1qOarufz6oP1fYByu8551X5QVXVxEtLk2rm5aQkdfK7B5YBO',
           30,
           'admin',
           'active'
       )
    ON DUPLICATE KEY UPDATE
                         password_hash = VALUES(password_hash),
                         role = 'admin',
                         status = 'active',
                         updated_at = CURRENT_TIMESTAMP;