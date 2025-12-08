package com.cs520group8.roommatematcher.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    
    @Value("${jwt.secret:RoomMatcherSecretKeyForJWT2024ThisMustBe256BitsLongAtMinimum}")
    private String secret;
    
    @Value("${jwt.expiration:86400000}") // 24 hours in milliseconds
    private long expiration;
    
    private SecretKey getSigningKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    
    // Generate token for user
    public String generateToken(Long userId, String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("email", email);
        
        return Jwts.builder()
                .claims(claims)
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }
    
    // Extract all claims from token
    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    
    // Extract user ID from token
    public Long extractUserId(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("userId", Long.class);
    }
    
    // Extract email from token
    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }
    
    // Check if token is expired
    public boolean isTokenExpired(String token) {
        try {
            return extractAllClaims(token).getExpiration().before(new Date());
        } catch (Exception e) {
            return true;
        }
    }
    
    // Validate token
    public boolean validateToken(String token, String email) {
        try {
            final String tokenEmail = extractEmail(token);
            return (tokenEmail.equals(email) && !isTokenExpired(token));
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
    
    // Validate token (simpler version - just check if valid)
    public boolean isTokenValid(String token) {
        try {
            extractAllClaims(token);
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }
}

