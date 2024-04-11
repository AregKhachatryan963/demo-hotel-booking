package com.aca.demohotelbookingv1.security;

import com.aca.demohotelbookingv1.auth.utils.JwtUtils;
import com.aca.demohotelbookingv1.model.User;
import com.aca.demohotelbookingv1.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.InvalidClaimException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    public JwtAuthorizationFilter(JwtUtils jwtUtils, ObjectMapper objectMapper, UserRepository userRepository) {
        this.jwtUtils = jwtUtils;
        this.objectMapper = objectMapper;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String accessToken = jwtUtils.resolveToken(request);
            if (accessToken == null) {
                filterChain.doFilter(request, response);
                return;
            }
            Claims claims = jwtUtils.resolveClaims(request);
            if (claims != null & jwtUtils.validateClaims(claims)) {
                String email = claims.getSubject();
                Optional<User> userOptional = userRepository.findByEmail(email);
                if (userOptional.isEmpty()) {
                    throw new AuthenticationException("Invalid Username Password Exception");
                }
                System.out.println("email : " + email);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(email,
                                userOptional.get().getPassword(), List.of(new Authority(userOptional.get().getRole())));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                throw new AuthenticationException("Token Expired");
            }

        } catch (InvalidClaimException | AuthenticationException e) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
        }
        filterChain.doFilter(request, response);
    }
    private static class Authority implements GrantedAuthority {
        private String authority;

        public Authority(String authority) {
            this.authority = authority;
        }

        @Override
        public String getAuthority() {
            return authority;
        }
    }
}

