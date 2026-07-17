package com.team.janja_fc.response;

public class AttendanceResponse {

    private int available;
    private int unavailable;
    private int pending;

    public AttendanceResponse() {
    }

    public AttendanceResponse(int available, int unavailable, int pending) {
        this.available = available;
        this.unavailable = unavailable;
        this.pending = pending;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public int getUnavailable() {
        return unavailable;
    }

    public void setUnavailable(int unavailable) {
        this.unavailable = unavailable;
    }

    public int getPending() {
        return pending;
    }

    public void setPending(int pending) {
        this.pending = pending;
    }
}