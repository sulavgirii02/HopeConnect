package com.hopeconnect.service;

import com.hopeconnect.dao.ApplicationDAO;
import com.hopeconnect.dao.AidProgramDAO;
import com.hopeconnect.model.AidProgram;
import com.hopeconnect.model.Application;
import com.hopeconnect.util.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * ApplicationService
 * Business logic for handling applications and related operations.
 */
public class ApplicationService {

    private final ApplicationDAO applicationDAO = new ApplicationDAO();
    private final NotificationService notificationService = new NotificationService();

    /**
     * Approves an application and creates a notification within a transaction.
     */
    public void approveApplication(int applicationId, int actorUserId) throws IllegalArgumentException {
        if (applicationId <= 0) throw new IllegalArgumentException("Invalid application id");
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            try {
                conn.setAutoCommit(false);
                Application app = applicationDAO.findByIdWithProgramDetails(applicationId);
                if (app == null) throw new IllegalArgumentException("Application not found");
                app.setStatus("approved");
                applicationDAO.update(app);
                notificationService.notifyUser(
                        app.getUserId(),
                        "Application Approved",
                        "Your application for " + app.getProgramTitle() + " has been approved.",
                        "application_approved"
                );
                conn.commit();
            } catch (Exception e) {
                if (conn != null) try { conn.rollback(); } catch (SQLException ex) { /* ignore */ }
                throw e;
            } finally {
                if (conn != null) try { conn.setAutoCommit(true); } catch (SQLException ex) { /* ignore */ }
            }
        } catch (SQLException sqle) {
            throw new IllegalArgumentException("Database error while approving application: " + sqle.getMessage());
        }
    }

    /**
     * Rejects an application and creates a notification within a transaction.
     */
    public void rejectApplication(int applicationId, int actorUserId, String reason) throws IllegalArgumentException {
        if (applicationId <= 0) throw new IllegalArgumentException("Invalid application id");
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            try {
                conn.setAutoCommit(false);
                Application app = applicationDAO.findByIdWithProgramDetails(applicationId);
                if (app == null) throw new IllegalArgumentException("Application not found");
                app.setStatus("rejected");
                applicationDAO.update(app);
                notificationService.notifyUser(
                        app.getUserId(),
                        "Application Rejected",
                        "Your application for " + app.getProgramTitle() + " has been rejected." +
                                (reason == null || reason.trim().isEmpty() ? "" : " Reason: " + reason),
                        "application_rejected"
                );
                conn.commit();
            } catch (Exception e) {
                if (conn != null) try { conn.rollback(); } catch (SQLException ex) { /* ignore */ }
                throw e;
            } finally {
                if (conn != null) try { conn.setAutoCommit(true); } catch (SQLException ex) { /* ignore */ }
            }
        } catch (SQLException sqle) {
            throw new IllegalArgumentException("Database error while rejecting application: " + sqle.getMessage());
        }
    }

    /**
     * Submits a new application for the given user and program.
     * Enforces duplicate check, remaining capacity check, decrements quota, and creates a notification.
     */
    public boolean submitApplication(int userId, int programId, String notes) throws IllegalArgumentException {
        if (userId <= 0) throw new IllegalArgumentException("Invalid user id");
        if (programId <= 0) throw new IllegalArgumentException("Invalid program id");
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            try {
                conn.setAutoCommit(false);
                // Duplicate check
                Application existing = applicationDAO.findByUserAndProgram(userId, programId);
                if (existing != null) throw new IllegalArgumentException("You have already applied to this program");

                // Capacity check
                AidProgramDAO programDAO = new AidProgramDAO();
                AidProgram program = programDAO.findById(programId);
                if (program == null || !program.isPublished() || !"open".equals(program.getProgramStatus())) {
                    throw new IllegalArgumentException("Program not found or no longer available");
                }
                boolean capacityAvailable = programDAO.decrementCapacity(programId, conn);
                if (!capacityAvailable) throw new IllegalArgumentException("This program is no longer accepting applications (quota full or program closed)");

                Application app = new Application();
                app.setUserId(userId);
                app.setProgramId(programId);
                app.setAppliedAt(new java.sql.Timestamp(System.currentTimeMillis()));
                app.setStatus("pending");
                app.setNotes(notes);
                applicationDAO.insert(app, conn);
                boolean autoClosed = programDAO.closeIfFull(programId, conn);

                conn.commit();
                notificationService.notifyUser(
                        userId,
                        "Application Submitted",
                        "Your application for " + program.getTitle() + " has been submitted successfully.",
                        "application"
                );
                notificationService.notifyAdmins(
                        "New Application Submitted",
                        "User #" + userId + " submitted an application for " + program.getTitle() + ".",
                        "admin_application"
                );
                if (autoClosed) {
                    notificationService.notifyAdmins(
                            "Program Quota Full",
                            program.getTitle() + " has reached full capacity and was automatically closed.",
                            "program_quota"
                    );
                }
                return autoClosed;
            } catch (Exception e) {
                if (conn != null) try { conn.rollback(); } catch (SQLException ex) { /* ignore */ }
                throw e;
            } finally {
                if (conn != null) try { conn.setAutoCommit(true); } catch (SQLException ex) { /* ignore */ }
            }
        } catch (SQLException sqle) {
            throw new IllegalArgumentException("Database error while submitting application: " + sqle.getMessage());
        }
    }
}
