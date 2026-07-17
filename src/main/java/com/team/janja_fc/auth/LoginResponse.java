package com.team.janja_fc.auth;

public class LoginResponse {

    private boolean success;
    private boolean mustSetPassword;
    private String token;

    public LoginResponse() {
    }

    public LoginResponse(boolean success, boolean mustSetPassword, String token) {
        this.success = success;
        this.mustSetPassword = mustSetPassword;
        this.token = token;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isMustSetPassword() {
        return mustSetPassword;
    }

    public void setMustSetPassword(boolean mustSetPassword) {
        this.mustSetPassword = mustSetPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}