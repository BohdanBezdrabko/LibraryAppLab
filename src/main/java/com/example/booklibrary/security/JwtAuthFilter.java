package com.example.booklibrary.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        System.out.println("Incoming request: " + request.getRequestURI());

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("No Authorization header or incorrect format");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        System.out.println("Token received: " + token);

        try {
            String username = jwtUtil.extractUsername(token);
            Claims claims = jwtUtil.extractClaims(token);

            System.out.println("Extracted username: " + username);
            System.out.println("Extracted role: " + claims.get("role"));

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);

                System.out.println("User authenticated: " + username);
                System.out.println("Granted authorities: " + userDetails.getAuthorities());
            }
        } catch (Exception e) {
            System.out.println("JWT Authentication failed: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}
