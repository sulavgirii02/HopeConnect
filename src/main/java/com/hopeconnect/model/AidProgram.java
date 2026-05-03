package com.hopeconnect.model;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * AidProgram
 * Represents a welfare program created by admins.
 */
public class AidProgram {
    private int id;
    private String title;
    private String slug;
    private String description;
    private String category;
    private String eligibility;
    private Date startDate;
    private Date endDate;
    private Integer capacity;
    private Integer createdBy;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private boolean isPublished;

    public AidProgram() {}

    public AidProgram(int id, String title, String slug, String description, String category, String eligibility,
                      Date startDate, Date endDate, Integer capacity, Integer createdBy, Timestamp createdAt,
                      Timestamp updatedAt, boolean isPublished) {
        this.id = id;
        this.title = title;
        this.slug = slug;
        this.description = description;
        this.category = category;
        this.eligibility = eligibility;
        this.startDate = startDate;
        this.endDate = endDate;
        this.capacity = capacity;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isPublished = isPublished;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getEligibility() { return eligibility; }
    public void setEligibility(String eligibility) { this.eligibility = eligibility; }
    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }
    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }
    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }
    public Integer getCreatedBy() { return createdBy; }
    public void setCreatedBy(Integer createdBy) { this.createdBy = createdBy; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }
    public boolean isPublished() { return isPublished; }
    public void setPublished(boolean published) { isPublished = published; }
}
