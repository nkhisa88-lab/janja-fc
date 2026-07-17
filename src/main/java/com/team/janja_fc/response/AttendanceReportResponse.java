package com.team.janja_fc.response;

import java.util.List;

public class AttendanceReportResponse {

    private String opponent;
    private String matchDate;

    private List<String> available;
    private List<String> unavailable;
    private List<String> pending;

    public AttendanceReportResponse() {
    }

    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }

    public String getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(String matchDate) {
        this.matchDate = matchDate;
    }

    public List<String> getAvailable() {
        return available;
    }

    public void setAvailable(List<String> available) {
        this.available = available;
    }

    public List<String> getUnavailable() {
        return unavailable;
    }

    public void setUnavailable(List<String> unavailable) {
        this.unavailable = unavailable;
    }

    public List<String> getPending() {
        return pending;
    }

    public void setPending(List<String> pending) {
        this.pending = pending;
    }
}