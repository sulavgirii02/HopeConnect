package com.hopeconnect.model;

import java.sql.Timestamp;

/**
 * ApplicationDocument
 * Represents an uploaded supporting file attached to an application.
 */
public class ApplicationDocument {
    private int id;
    private int applicationId;
    private String fileName;
    private String filePath;
    private String fileType;
    private int uploadedBy;
    private Timestamp uploadedAt;

    public ApplicationDocument() {}

    public ApplicationDocument(int id, int applicationId, String fileName, String filePath, String fileType, int uploadedBy, Timestamp uploadedAt) {
        this.id = id;
        this.applicationId = applicationId;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
        this.uploadedBy = uploadedBy;
        this.uploadedAt = uploadedAt;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getApplicationId() { return applicationId; }
    public void setApplicationId(int applicationId) { this.applicationId = applicationId; }
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
    public String getFileType() { return fileType; }
    public void setFileType(String fileType) { this.fileType = fileType; }
    public int getUploadedBy() { return uploadedBy; }
    public void setUploadedBy(int uploadedBy) { this.uploadedBy = uploadedBy; }
    public Timestamp getUploadedAt() { return uploadedAt; }
    public void setUploadedAt(Timestamp uploadedAt) { this.uploadedAt = uploadedAt; }
}
