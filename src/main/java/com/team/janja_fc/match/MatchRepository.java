package com.team.janja_fc.match;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MatchRepository {

    private final JdbcTemplate jdbcTemplate;

    public MatchRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createMatch(
            String opponent,
            String venue,
            java.time.LocalDate matchDate,
            java.time.LocalTime kickoffTime,
            Long createdBy) {

        String sql = """
                INSERT INTO matches(
                    opponent,
                    venue,
                    match_date,
                    kickoff_time,
                    status,
                    created_at,
                    created_by
                )
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        jdbcTemplate.update(
                sql,
                opponent,
                venue,
                matchDate,
                kickoffTime,
                MatchStatus.SCHEDULED.name(),
                LocalDateTime.now(),
                createdBy);
    }

    public List<MatchResponse> findAll() {

        String sql = """
                SELECT *
                FROM matches
                ORDER BY match_date, kickoff_time
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {

            MatchResponse response = new MatchResponse();

            response.setId(rs.getLong("id"));
            response.setOpponent(rs.getString("opponent"));
            response.setVenue(rs.getString("venue"));
            response.setMatchDate(
                    rs.getDate("match_date").toString());
            response.setKickoffTime(
                    rs.getTime("kickoff_time")
                            .toLocalTime()
                            .toString());
            response.setStatus(
                    rs.getString("status"));

            return response;

        });
    }

    public Match findById(Long id) {

        String sql = """
                SELECT *
                FROM matches
                WHERE id = ?
                """;

        return jdbcTemplate.queryForObject(
                sql,
                (rs, rowNum) -> {

                    Match match = new Match();

                    match.setId(rs.getLong("id"));
                    match.setOpponent(rs.getString("opponent"));
                    match.setVenue(rs.getString("venue"));
                    match.setMatchDate(
                            rs.getDate("match_date").toLocalDate());
                    match.setKickoffTime(
                            rs.getTime("kickoff_time").toLocalTime());
                    match.setStatus(
                            MatchStatus.valueOf(
                                    rs.getString("status")));
                    match.setCreatedAt(
                            rs.getTimestamp("created_at")
                                    .toLocalDateTime());
                    match.setCreatedBy(
                            rs.getLong("created_by"));

                    return match;

                },
                id);
    }

    public void cancelMatch(Long matchId) {

        String sql = """
                UPDATE matches
                SET status = ?
                WHERE id = ?
                """;

        jdbcTemplate.update(
                sql,
                MatchStatus.CANCELLED.name(),
                matchId);
    }

    public void completeMatch(Long matchId) {

        String sql = """
                UPDATE matches
                SET status = ?
                WHERE id = ?
                """;

        jdbcTemplate.update(
                sql,
                MatchStatus.COMPLETED.name(),
                matchId);
    }
}