package com.team.janja_fc.response;

public class MatchResponseRequest {

    private Long matchId;
    private ResponseStatus status;

    public MatchResponseRequest() {
    }

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }
}