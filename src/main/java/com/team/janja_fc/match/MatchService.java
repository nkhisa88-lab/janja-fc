package com.team.janja_fc.match;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class MatchService {

    private final MatchRepository matchRepository;

    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public void createMatch(CreateMatchRequest request) {

        matchRepository.createMatch(
                request.getOpponent(),
                request.getVenue(),
                LocalDate.parse(request.getMatchDate()),
                LocalTime.parse(request.getKickoffTime()),
                1L);
    }

    public List<MatchResponse> getAllMatches() {
        return matchRepository.findAll();
    }

    public void cancelMatch(Long matchId) {
        matchRepository.cancelMatch(matchId);
    }

    public void completeMatch(Long matchId) {
        matchRepository.completeMatch(matchId);
    }

    public Match getMatch(Long matchId) {
        return matchRepository.findById(matchId);
    }
}