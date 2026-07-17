package com.team.janja_fc.match;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Match {

    private Long id;
    private String opponent;
    private String venue;
    private LocalDate matchDate;
    private LocalTime kickoffTime;
    private MatchStatus status;
    private LocalDateTime createdAt;
    private Long createdBy;

    public Match() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public LocalDate getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(LocalDate matchDate) {
        this.matchDate = matchDate;
    }

    public LocalTime getKickoffTime() {
        return kickoffTime;
    }

    public void setKickoffTime(LocalTime kickoffTime) {
        this.kickoffTime = kickoffTime;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public MatchStatus getStatus() {
        return status;
    }

    public void setStatus(MatchStatus status) {
        this.status = status;
    }

}