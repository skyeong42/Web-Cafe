package com.example.cafemanagement.config.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.example.cafemanagement.config.InMemoryTokenBlacklist;
import com.example.cafemanagement.util.JwtUtil;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final InMemoryTokenBlacklist tokenBlacklist;

    public JwtFilter(JwtUtil jwtUtil, InMemoryTokenBlacklist tokenBlacklist) {
        this.jwtUtil = jwtUtil;
        this.tokenBlacklist = tokenBlacklist;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            try {
                // Check if the token is blacklisted
                if (tokenBlacklist.isBlacklisted(token)) {
                    filterChain.doFilter(request, response);
                }

                String username = jwtUtil.validateToken(token);

                if (username != null) {
                    List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList("USER");
                    var auth = new UsernamePasswordAuthenticationToken(username, token, authorities);
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            } catch (Exception e) {
                filterChain.doFilter(request, response);
            }
        }

        filterChain.doFilter(request, response);
    }
}
