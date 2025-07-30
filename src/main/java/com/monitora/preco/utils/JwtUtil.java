package com.monitora.preco.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    private final Key secretKey;
    private final long expiration = 1000 * 60 * 60 * 24; // 24h

    public JwtUtil(@Value("${jwt.secret}") String base64Secret) {
        System.out.println("jwt.secret recebido: '" + base64Secret + "'");
        this.secretKey = Keys.hmacShaKeyFor(java.util.Base64.getDecoder().decode(base64Secret));
    }

    public String gerarToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("roles", userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList())
                )
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }


    public String extrairUsername(String token) {
        return getClaims(token).getSubject();
    }

    public boolean validarToken(String token, String username) {
        return extrairUsername(token).equals(username) && !isTokenExpirado(token);
    }

    private boolean isTokenExpirado(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
