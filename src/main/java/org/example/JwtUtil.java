package org.example;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // 256-bit secret key used to cryptographically sign and verify tokens
    private static final String SECRET_STRING = "MySuperSecretKeyForQuickBiteDeliveryPlatform2026!";
    private final Key key = Keys.hmacShaKeyFor(SECRET_STRING.getBytes());

    // Token expiration: 24 Hours (in milliseconds)
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24;

    // 1. Generate Token for authenticated user
    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 2. Extract Username from Token
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // 3. Extract Role from Token
    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    // 4. Validate Token (check signature and expiration)
    public boolean validateToken(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (Exception e) {
            return false; // Signature invalid or token expired
        }
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}