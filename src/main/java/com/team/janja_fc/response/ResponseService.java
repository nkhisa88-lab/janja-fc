package com.team.janja_fc.response;

import org.springframework.stereotype.Service;

import com.team.janja_fc.match.Match;
import com.team.janja_fc.match.MatchRepository;
import com.team.janja_fc.security.JwtService;
import com.team.janja_fc.user.UserRepository;

@Service
public class ResponseService {

        private final ResponseRepository responseRepository;
        private final UserRepository userRepository;
        private final MatchRepository matchRepository;
        private final JwtService jwtService;

        public ResponseService(
                        ResponseRepository responseRepository,
                        UserRepository userRepository,
                        MatchRepository matchRepository,
                        JwtService jwtService) {

                this.responseRepository = responseRepository;
                this.userRepository = userRepository;
                this.matchRepository = matchRepository;
                this.jwtService = jwtService;
        }

        public void respond(
                        String authHeader,
                        MatchResponseRequest request) {

                String token = authHeader.replace("Bearer ", "");

                Long userId = jwtService.extractUserId(token);

                responseRepository.saveResponse(
                                userId,
                                request.getMatchId(),
                                request.getStatus());

        }

        public AttendanceResponse getAttendance(Long matchId) {

                AttendanceResponse response = responseRepository.getAttendance(matchId);

                int totalPlayers = userRepository.countPlayers();

                response.setPending(
                                totalPlayers
                                                - response.getAvailable()
                                                - response.getUnavailable());

                return response;
        }

        public AttendanceReportResponse getAttendanceReport(Long matchId) {

                Match match = matchRepository.findById(matchId);

                AttendanceReportResponse report = new AttendanceReportResponse();

                report.setOpponent(match.getOpponent());

                report.setMatchDate(
                                match.getMatchDate().toString());

                report.setAvailable(
                                responseRepository.findAvailablePlayers(matchId));

                report.setUnavailable(
                                responseRepository.findUnavailablePlayers(matchId));

                report.setPending(
                                userRepository.findPendingPlayers(matchId));

                return report;
        }

}