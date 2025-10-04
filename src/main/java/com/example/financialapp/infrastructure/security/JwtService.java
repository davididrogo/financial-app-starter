package com.example.financialapp.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Component
public class JwtService {
    private final SecretKey key;
    private final long ttlSeconds;
    public JwtService(Environment env){
        String secret = env.getProperty("app.jwt.secret"
                ,"change-this-dev-secret-32chars-min");
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.ttlSeconds = Long.parseLong(env.getProperty("app.jwt.ttlSeconds"
                ,"3600"));
    }
    public String issue(String subject, Map<String, Object> claims){
        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(subject)
                .setClaims(claims)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(ttlSeconds)))
                .signWith(key)
                .compact();
    }
    public Claims verify(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
