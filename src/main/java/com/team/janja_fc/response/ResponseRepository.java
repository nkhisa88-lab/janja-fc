package com.team.janja_fc.response;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ResponseRepository {

    private final JdbcTemplate jdbcTemplate;

    public ResponseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveResponse(
            Long userId,
            Long matchId,
            ResponseStatus status) {

        Integer count = jdbcTemplate.queryForObject(
                """
                        SELECT COUNT(*)
                        FROM responses
                        WHERE user_id = ?
                        AND match_id = ?
                        """,
                Integer.class,
                userId,
                matchId);

        if (count != null && count > 0) {

            jdbcTemplate.update(
                    """
                            UPDATE responses
                            SET status = ?,
                                responded_at = ?
                            WHERE user_id = ?
                            AND match_id = ?
                            """,
                    status.name(),
                    LocalDateTime.now(),
                    userId,
                    matchId);

        } else {

            jdbcTemplate.update(
                    """
                            INSERT INTO responses(
                                user_id,
                                match_id,
                                status,
                                responded_at
                            )
                            VALUES (?, ?, ?, ?)
                            """,
                    userId,
                    matchId,
                    status.name(),
                    LocalDateTime.now());

        }
    }

    public AttendanceResponse getAttendance(Long matchId) {

        String sql = """
                SELECT
                    COUNT(CASE WHEN status = 'AVAILABLE' THEN 1 END) AS available,
                    COUNT(CASE WHEN status = 'UNAVAILABLE' THEN 1 END) AS unavailable
                FROM responses
                WHERE match_id = ?
                """;

        return jdbcTemplate.queryForObject(
                sql,
                (rs, rowNum) -> {

                    int available = rs.getInt("available");
                    int unavailable = rs.getInt("unavailable");

                    return new AttendanceResponse(
                            available,
                            unavailable,
                            0);

                },
                matchId);

    }

    public List<String> findAvailablePlayers(Long matchId) {

        String sql = """
                SELECT u.full_name
                FROM responses r
                JOIN users u
                    ON r.user_id = u.id
                WHERE r.match_id = ?
                AND r.status = 'AVAILABLE'
                ORDER BY u.full_name
                """;

        return jdbcTemplate.queryForList(
                sql,
                String.class,
                matchId);

    }

    public List<String> findUnavailablePlayers(Long matchId) {

        String sql = """
                SELECT u.full_name
                FROM responses r
                JOIN users u
                    ON r.user_id = u.id
                WHERE r.match_id = ?
                AND r.status = 'UNAVAILABLE'
                ORDER BY u.full_name
                """;

        return jdbcTemplate.queryForList(
                sql,
                String.class,
                matchId);

    }

    public List<String> findPendingPlayers(Long matchId) {

        String sql = """
                SELECT u.full_name
                FROM users u
                WHERE u.role = 'PLAYER'
                AND NOT EXISTS (

                    SELECT 1
                    FROM responses r
                    WHERE r.user_id = u.id
                    AND r.match_id = ?

                )
                ORDER BY u.full_name
                """;

        return jdbcTemplate.queryForList(
                sql,
                String.class,
                matchId);

    }

}