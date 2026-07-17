package com.team.janja_fc.user;

import java.time.LocalDateTime;

public class User {

    private Long id;

    private String fullName;

    private String phoneNumber;

    private Role role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String activationCodeHash() {
        return activationCodeHash;
    }

    public void setactivationCodeHash(String activationCode) {
        this.activationCodeHash = activationCode;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String password) {
        this.passwordHash = password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    private String activationCodeHash;

    private String passwordHash;

    private LocalDateTime createdAt;

    public User() {
    }

    public boolean isActivated() {
        return passwordHash != null;
    }

    public boolean needsActivation() {
        return activationCodeHash != null;
    }

}
