package com.team.janja_fc.user;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

        private final JdbcTemplate jdbcTemplate;

        public UserRepository(JdbcTemplate jdbcTemplate) {
                this.jdbcTemplate = jdbcTemplate;
        }

        public boolean adminExists() {

                String sql = """
                                SELECT COUNT(*)
                                FROM users
                                WHERE role = ?
                                """;

                Integer count = jdbcTemplate.queryForObject(
                                sql,
                                Integer.class,
                                Role.ADMIN.name());

                return count != null && count > 0;
        }

        public void createAdmin(
                        String fullName,
                        String phoneNumber,
                        String activationCodeHash) {

                String sql = """
                                INSERT INTO users(
                                    full_name,
                                    phone_number,
                                    role,
                                    activation_code,
                                    password,
                                    created_at
                                )
                                VALUES (?, ?, ?, ?, ?, ?)
                                """;

                jdbcTemplate.update(
                                sql,
                                fullName,
                                phoneNumber,
                                Role.ADMIN.name(),
                                activationCodeHash,
                                null,
                                java.time.LocalDateTime.now());
        }

        public User findByPhoneNumber(String phoneNumber) {

                String sql = """
                                SELECT *
                                FROM users
                                WHERE phone_number = ?
                                """;

                return jdbcTemplate.queryForObject(
                                sql,
                                (rs, rowNum) -> {

                                        User user = new User();

                                        user.setId(rs.getLong("id"));
                                        user.setFullName(rs.getString("full_name"));
                                        user.setPhoneNumber(rs.getString("phone_number"));
                                        user.setRole(Role.valueOf(rs.getString("role")));
                                        user.setactivationCodeHash(rs.getString("activation_code"));
                                        user.setPasswordHash(rs.getString("password"));
                                        user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

                                        return user;

                                },
                                phoneNumber);
        }

        public void activateUser(
                        String phoneNumber,
                        String passwordHash) {

                String sql = """
                                UPDATE users
                                SET password = ?,
                                    activation_code = NULL
                                WHERE phone_number = ?
                                """;

                jdbcTemplate.update(
                                sql,
                                passwordHash,
                                phoneNumber);

        }

        public void createPlayer(
                        String fullName,
                        String phoneNumber,
                        String activationCodeHash) {

                String sql = """
                                INSERT INTO users(
                                    full_name,
                                    phone_number,
                                    role,
                                    activation_code,
                                    password,
                                    created_at
                                )
                                VALUES (?, ?, ?, ?, ?, ?)
                                """;

                jdbcTemplate.update(
                                sql,
                                fullName,
                                phoneNumber,
                                Role.PLAYER.name(),
                                activationCodeHash,
                                null,
                                java.time.LocalDateTime.now());

        }

        public int countPlayers() {

                String sql = """
                                SELECT COUNT(*)
                                FROM users
                                WHERE role = 'PLAYER'
                                """;

                return jdbcTemplate.queryForObject(sql, Integer.class);

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
