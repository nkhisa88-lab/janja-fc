package com.team.janja_fc.match;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/matches")
public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public void createMatch(@RequestBody CreateMatchRequest request) {
        matchService.createMatch(request);
    }

    @GetMapping
    public List<MatchResponse> getAllMatches() {
        return matchService.getAllMatches();
    }

    @GetMapping("/{id}")
    public Match getMatch(@PathVariable Long id) {
        return matchService.getMatch(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/cancel")
    public void cancelMatch(@PathVariable Long id) {
        matchService.cancelMatch(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/complete")
    public void completeMatch(@PathVariable Long id) {
        matchService.completeMatch(id);
    }

}