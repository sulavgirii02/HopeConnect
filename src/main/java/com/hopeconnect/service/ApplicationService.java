package com.hopeconnect.service;

import com.hopeconnect.dao.ApplicationDAO;
import com.hopeconnect.dao.NotificationDAO;
import com.hopeconnect.model.Application;
import com.hopeconnect.model.Notification;

import java.sql.Timestamp;

/**
 * ApplicationService
 * Business logic for handling applications and related operations.
 */
public class ApplicationService {

    private final ApplicationDAO applicationDAO = new ApplicationDAO();
    private final NotificationDAO notificationDAO = new NotificationDAO();

    /**
     * Approves an application and creates a notification within a transaction.
     */
    public void approveApplication(int applicationId, int actorUserId) throws IllegalArgumentException {
        if (applicationId <= 0) throw new IllegalArgumentException("Invalid application id");
        Application app = applicationDAO.findById(applicationId);
        if (app == null) throw new IllegalArgumentException("Application not found");
        app.setStatus("approved");
        if (!applicationDAO.update(app)) throw new IllegalArgumentException("Unable to approve application");

        Notification n = new Notification();
        n.setUserId(app.getUserId());
        n.setTitle("Application Approved");
        n.setMessage("Your application for program id " + app.getProgramId() + " has been approved.");
        n.setType("info");
        n.setRead(false);
        notificationDAO.insert(n);
    }

    /**
     * Rejects an application and creates a notification within a transaction.
     */
    public void rejectApplication(int applicationId, int actorUserId, String reason) throws IllegalArgumentException {
        if (applicationId <= 0) throw new IllegalArgumentException("Invalid application id");
        Application app = applicationDAO.findById(applicationId);
        if (app == null) throw new IllegalArgumentException("Application not found");
        app.setStatus("rejected");
        if (!applicationDAO.update(app)) throw new IllegalArgumentException("Unable to reject application");

        Notification n = new Notification();
        n.setUserId(app.getUserId());
        n.setTitle("Application Rejected");
        n.setMessage("Your application for program id " + app.getProgramId() + " has been rejected. Reason: " + (reason == null ? "" : reason));
        n.setType("warning");
        n.setRead(false);
        notificationDAO.insert(n);
    }

    /**
     * Submits a new application for the given user and program.
     * Enforces duplicate check and creates a notification.
     */
    public void submitApplication(int userId, int programId, String notes) throws IllegalArgumentException {
        if (userId <= 0) throw new IllegalArgumentException("Invalid user id");
        if (programId <= 0) throw new IllegalArgumentException("Invalid program id");
        Application existing = applicationDAO.findByUserAndProgram(userId, programId);
        if (existing != null) throw new IllegalArgumentException("You have already applied to this program");

        Application app = new Application();
        app.setUserId(userId);
        app.setProgramId(programId);
        app.setAppliedAt(new Timestamp(System.currentTimeMillis()));
        app.setStatus("pending");
        app.setNotes(notes);
        if (applicationDAO.insert(app) <= 0) throw new IllegalArgumentException("Unable to submit application");

        Notification n = new Notification();
        n.setUserId(userId);
        n.setTitle("Application Submitted");
        n.setMessage("Your application has been submitted successfully.");
        n.setType("info");
        n.setRead(false);
        notificationDAO.insert(n);
    }
}
