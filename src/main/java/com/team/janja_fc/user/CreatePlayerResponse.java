package com.team.janja_fc.user;

public class CreatePlayerResponse {

    private String activationCode;

    public CreatePlayerResponse() {
    }

    public CreatePlayerResponse(String activationCode) {
        this.activationCode = activationCode;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }
}