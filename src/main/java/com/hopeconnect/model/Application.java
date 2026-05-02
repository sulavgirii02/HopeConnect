package com.hopeconnect.model;

import java.sql.Timestamp;

/**
 * Application
 * Represents a user's application to an aid program.
 */
public class Application {
    private int id;
    private int userId;
    private int programId;
    private Timestamp appliedAt;
    private String status;
    private Integer assignedOfficer;
    private String notes;
    private Timestamp lastUpdated;

    public Application() {}

    public Application(int id, int userId, int programId, Timestamp appliedAt, String status,
                       Integer assignedOfficer, String notes, Timestamp lastUpdated) {
        this.id = id;
        this.userId = userId;
        this.programId = programId;
        this.appliedAt = appliedAt;
        this.status = status;
        this.assignedOfficer = assignedOfficer;
        this.notes = notes;
        this.lastUpdated = lastUpdated;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getProgramId() { return programId; }
    public void setProgramId(int programId) { this.programId = programId; }
    public Timestamp getAppliedAt() { return appliedAt; }
    public void setAppliedAt(Timestamp appliedAt) { this.appliedAt = appliedAt; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Integer getAssignedOfficer() { return assignedOfficer; }
    public void setAssignedOfficer(Integer assignedOfficer) { this.assignedOfficer = assignedOfficer; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public Timestamp getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(Timestamp lastUpdated) { this.lastUpdated = lastUpdated; }
}
