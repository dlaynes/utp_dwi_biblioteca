package com.dwigs.biblioteca.security.utils;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.List;

// Para poder funcionar, esta clase utilitaria usa una versión antigua del paquete jsonwebtoken
@Component
public class JwtUtil {
    @Value("${app.jwt.secret}")
    private String secretKey;

    @Value("${app.jwt.expiration-minutes}")
    private long accessTokenValidityMinutes;

    private final String TOKEN_HEADER = "Authorization";

    private final String TOKEN_PREFIX = "Bearer ";

    public String createToken(String username, List<String> roles){
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", roles);
        Date issuedAt = new Date();
        Date expiration = new Date(issuedAt.getTime() + Duration.ofMinutes(accessTokenValidityMinutes).toMillis());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String resolveToken(HttpServletRequest request){
        String bearer = request.getHeader(TOKEN_HEADER);
        if(bearer != null && bearer.startsWith(TOKEN_PREFIX)){
            return bearer.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    public Claims parseClaims(String token) throws ExpiredJwtException, JwtException {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    public boolean isTokenValid(Claims claims){
        return claims.getExpiration().after(new Date());
    }

    public String getUsername(Claims claims){
        return claims.getSubject();
    }

    @SuppressWarnings("unchecked")
    public List<String> getRoles(Claims claims) { return (List<String>) claims.get("roles"); }

}
