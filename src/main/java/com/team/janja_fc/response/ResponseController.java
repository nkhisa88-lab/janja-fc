package com.team.janja_fc.response;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/responses")
public class ResponseController {

    private final ResponseService service;

    public ResponseController(ResponseService service) {
        this.service = service;
    }

    @PreAuthorize("hasRole('PLAYER')")
    @PostMapping
    public void respond(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody MatchResponseRequest request) {

        service.respond(authHeader, request);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/attendance/{matchId}")
    public AttendanceResponse attendance(
            @PathVariable Long matchId) {

        return service.getAttendance(matchId);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/report/{matchId}")
    public AttendanceReportResponse report(
            @PathVariable Long matchId) {

        return service.getAttendanceReport(matchId);

    }

}