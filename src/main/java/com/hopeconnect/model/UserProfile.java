package com.hopeconnect.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * UserProfile
 * Stores demographic and socioeconomic information for a user (1:1 relationship with User).
 */
public class UserProfile {
    private int id;
    private int userId;
    private Date dateOfBirth;
    private String gender;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private Integer householdSize;
    private BigDecimal monthlyIncome;
    private String preferredCategory;
    private String additionalInfo;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public UserProfile() {}

    public UserProfile(int id, int userId, Date dateOfBirth, String gender, String addressLine1, String addressLine2,
                       String city, String state, String postalCode, String country, Integer householdSize,
                       BigDecimal monthlyIncome, String preferredCategory, String additionalInfo, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.userId = userId;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
        this.householdSize = householdSize;
        this.monthlyIncome = monthlyIncome;
        this.preferredCategory = preferredCategory;
        this.additionalInfo = additionalInfo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public Date getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(Date dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getAddressLine1() { return addressLine1; }
    public void setAddressLine1(String addressLine1) { this.addressLine1 = addressLine1; }
    public String getAddressLine2() { return addressLine2; }
    public void setAddressLine2(String addressLine2) { this.addressLine2 = addressLine2; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public Integer getHouseholdSize() { return householdSize; }
    public void setHouseholdSize(Integer householdSize) { this.householdSize = householdSize; }
    public BigDecimal getMonthlyIncome() { return monthlyIncome; }
    public void setMonthlyIncome(BigDecimal monthlyIncome) { this.monthlyIncome = monthlyIncome; }
    public String getAdditionalInfo() { return additionalInfo; }
    public void setAdditionalInfo(String additionalInfo) { this.additionalInfo = additionalInfo; }
    public String getPreferredCategory() { return preferredCategory; }
    public void setPreferredCategory(String preferredCategory) { this.preferredCategory = preferredCategory; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    public Timestamp getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }
}
