package com.agendadakota.agendadakota.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {


    private static final String SECRET = "1uD7ZkQpgL3sA9fV1wR8tKqF5nH2mC0eB7yVpL8xS9uT4rH6qN3wX1yZ9sL0kT";
    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());


    public String generarToken(String username, String rol) {
        return Jwts.builder()
                .setSubject(username)
                .addClaims(Map.of("rol", rol))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(key)
                .compact();
    }


    public String extraerEmail(String token) {

        return Jwts.parser()
                .verifyWith((SecretKey) key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }


    public boolean esTokenValido(String token) {
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) key)
                    .build()
                    .parse(token);
            return true;
        } catch (JwtException e) {

            System.err.println("Token inválido o expirado: " + e.getMessage());
            return false;
        }
    }
}

