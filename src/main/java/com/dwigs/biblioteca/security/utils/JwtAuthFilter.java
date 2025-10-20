package com.dwigs.biblioteca.security.utils;

import com.dwigs.biblioteca.service.UsuarioService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioService usuarioService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws ServletException, IOException
    {
        try {
            String token = jwtUtil.resolveToken(request);
            if(token == null){
                System.out.println("No token found!");
                return;
            }
            Claims claims = jwtUtil.parseClaims(token);
            if(jwtUtil.isTokenValid(claims)){
                String username = jwtUtil.getUsername(claims);
                List<String> roles = jwtUtil.getRoles(claims);
                UserDetails userDetails = usuarioService.loadUserByUsername(username); // Lanza un error si no existe
                var authorities = roles.stream()
                        // map(r -> "ROLE_" + r) // Quitamos los prefijos, para que funcione el SecurityConfig
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                auth.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(auth);

            } else {
                System.out.println("Token is not valid: " + claims.getSubject() + ", " + claims.getExpiration());
            }

        } catch(ExpiredJwtException ex){
            request.setAttribute("expired", ex.getMessage());
        } catch(Exception e){
            request.setAttribute("invalid", e.getMessage());
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request){
        String path = request.getServletPath();
        return path.startsWith("/auth/") || path.startsWith("/api/auth/") || path.startsWith("/public/");
    }
}
