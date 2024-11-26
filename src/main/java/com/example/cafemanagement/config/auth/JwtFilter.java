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
import jakarta.servlet.http.Cookie;
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
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("Authorization".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    try {
                        String username = jwtUtil.validateToken(token);
                        if (username != null) {
                            List<GrantedAuthority> authorities =
                                    AuthorityUtils.commaSeparatedStringToAuthorityList("USER");
                            var auth = new UsernamePasswordAuthenticationToken(username, token, authorities);
                            SecurityContextHolder.getContext().setAuthentication(auth);
                        }
                    } catch (Exception e) {
                        // 유효하지 않은 토큰 처리
                    }
                }
            }
        }

        filterChain.doFilter(request, response);
    }

}
