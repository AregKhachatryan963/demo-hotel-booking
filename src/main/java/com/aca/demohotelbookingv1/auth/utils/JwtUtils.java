package com.aca.demohotelbookingv1.auth.utils;

import com.aca.demohotelbookingv1.model.User;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import javax.naming.AuthenticationException;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtUtils {
    private final String secret_key = "qeri_dog";

    private final JwtParser jwtParser;

    private final String TOKEN_HEADER = "Authorization";
    private final String TOKEN_PREFIX = "Bearer ";

    public JwtUtils() {
        this.jwtParser = Jwts.parser().setSigningKey(secret_key);
    }
    public String createToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getEmail());
//      ____________________________________________________________
        claims.put("firstName", user.getFirstName()); // Why we put in claims only name?
//        ______________________________________________________
        claims.put("created", Date.from(Instant.now()));
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secret_key)
                .compact();
    }

    public Claims resolveClaims(HttpServletRequest req) {
        try {
            String token = resolveToken(req);
            if (token != null) {
                return parseJwtClaims(token);
            }
            return null;
        } catch (ExpiredJwtException ex) {
            req.setAttribute("expired", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            req.setAttribute("invalid", ex.getMessage());
            throw ex;
        }
    }


    public String resolveToken(HttpServletRequest request) {

        String bearerToken = request.getHeader(TOKEN_HEADER);
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    public boolean validateClaims(Claims claims) throws AuthenticationException {
        try {
            return (Date.from(Instant.ofEpochMilli((Long) claims.get("created")))).after(Date.from(Instant.now().minusSeconds(60 * 60 * 2)));
        } catch (InvalidClaimException e) {
            throw e;
        }
    }

    public String getEmail(Claims claims) {
        return claims.getSubject();
    }

//    private List<String> getRoles(Claims claims) {
//        return (List<String>) claims.get("roles");
//    }

    private Claims parseJwtClaims(String token) {
        return jwtParser.parseClaimsJws(token).getBody();
    }
}
