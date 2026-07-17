package com.team.janja_fc.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import com.team.janja_fc.auth.TokenType;
import com.team.janja_fc.user.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {

    private final JwtProperties jwtProperties;

    public JwtService(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    private Key getSigningKey() {

        return new SecretKeySpec(
                jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8),
                SignatureAlgorithm.HS256.getJcaName());

    }

    public String generateAccessToken(User user) {
        return generateToken(
                user,
                TokenType.ACCESS,
                1000L * 60 * 60 * 24); // 24 hours
    }

    public String generateActivationToken(User user) {
        return generateToken(
                user,
                TokenType.ACTIVATION,
                1000L * 60 * 10); // 10 minutes
    }

    private String generateToken(
            User user,
            TokenType tokenType,
            long expiryMillis) {

        Date now = new Date();
        Date expiry = new Date(now.getTime() + expiryMillis);

        return Jwts.builder()
                .subject(user.getPhoneNumber())
                .claim("userId", user.getId())
                .claim("role", user.getRole().name())
                .claim("fullName", user.getFullName())
                .claim("type", tokenType.name())
                .issuedAt(now)
                .expiration(expiry)
                .signWith(getSigningKey())
                .compact();
    }

    public String extractPhoneNumber(String token) {

        return Jwts.parser()
                .verifyWith((SecretKey) getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();

    }

    public Long extractUserId(String token) {

        return Jwts.parser()
                .verifyWith((SecretKey) getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("userId", Long.class);

    }

    public String extractRole(String token) {

        return Jwts.parser()
                .verifyWith((SecretKey) getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role", String.class);

    }

    public TokenType extractTokenType(String token) {

        String type = Jwts.parser()
                .verifyWith((SecretKey) getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("type", String.class);

        return TokenType.valueOf(type);

    }

    public boolean isExpired(String token) {

        Date expiration = Jwts.parser()
                .verifyWith((SecretKey) getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();

        return expiration.before(new Date());

    }

    public boolean isValid(String token) {
        return !isExpired(token);
    }

}