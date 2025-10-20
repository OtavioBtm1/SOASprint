package com.fiap.checkpoint1.service;

import com.fiap.checkpoint1.model.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    private final long expirationHours = 2; 

    /**
     * Gera o token JWT para o usuário autenticado.
     */
    public String generateToken(Usuario usuario) {
        Instant now = Instant.now();
        Instant expiration = now.plus(expirationHours, ChronoUnit.HOURS);
        
        return Jwts.builder()
                .setIssuer("API Checkpoint FIAP")
                .setSubject(usuario.getEmail()) 
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

     
    public String validateToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                     .build() 
                    .parseClaimsJws(token) 
                    .getBody()
                    .getSubject(); 
        } catch (Exception e) {
             return null;
        }
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}