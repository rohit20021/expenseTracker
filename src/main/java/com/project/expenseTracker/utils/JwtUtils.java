package com.project.expenseTracker.utils;

import com.project.expenseTracker.exceptions.InvalidTokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@Slf4j
@Component
public class JwtUtils {

    @Value("${spring.app.jwtSecret}")
    private String jwtSecret;

    @Value("${spring.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public static final String ISSUER = "EXPENSE_TRACKER_USER_AUTH_SERVICE";
    private static final Key SecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateAccessToken(String email){
        var issuedAt= new Date();
        var expiredAt = DateUtils.addMinutes(issuedAt, 15);
        return Jwts.builder()
                .setIssuer(ISSUER)
                .setSubject(email)
                .setIssuedAt(issuedAt)
                .setExpiration(expiredAt)
                .signWith(key())
                .compact();
    }

    public String generateTokenFromUsername(UserDetails userDetails) {
        String username = userDetails.getUsername();
        var issuedAt= new Date();
        var expiredAt=new Date((new Date()).getTime() + jwtExpirationMs);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(issuedAt)
                .setExpiration(expiredAt)
                .signWith(key())
                .compact();
    }

    public String getJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        log.info("Authorization Header: {}", bearerToken);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public Optional<String> getUserNameFromJwtToken(String token) {
        if(validateJwtToken(token)){
            return Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject().describeConstable();
        }
        return Optional.empty();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
    public boolean validateJwtToken(String authToken) {
        try {
            System.out.println("Validate");
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(key()) // Set the signing key for verification
                    .build()
                    .parseClaimsJws(authToken);// Parse and validate the token
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            throw new InvalidTokenException("Invalid JWT token: " + e.getMessage(),"e401");
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
            throw new InvalidTokenException("Invalid JWT token: " + e.getMessage(),"e401");
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
            throw new InvalidTokenException("Invalid JWT token: " + e.getMessage(),"e401");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
            throw new InvalidTokenException("Invalid JWT token: " + e.getMessage(),"e401");
        } catch(Exception e){
            log.error("all other jwt exception");
            throw new InvalidTokenException("Invalid JWT token: " + e.getMessage(),"e401");
        }
    }

}