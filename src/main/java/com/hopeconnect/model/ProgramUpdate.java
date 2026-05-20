package com.hopeconnect.model;

import java.sql.Timestamp;

/**
 * ProgramUpdate
 * Represents an announcement or update related to an aid program.
 */
public class ProgramUpdate {
    private int id;
    private int programId;
    private String title;
    private String message;
    private int createdBy;
    private Timestamp createdAt;

    public ProgramUpdate() {}

    public ProgramUpdate(int id, int programId, String title, String message, int createdBy, Timestamp createdAt) {
        this.id = id;
        this.programId = programId;
        this.title = title;
        this.message = message;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getProgramId() { return programId; }
    public void setProgramId(int programId) { this.programId = programId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public int getCreatedBy() { return createdBy; }
    public void setCreatedBy(int createdBy) { this.createdBy = createdBy; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
