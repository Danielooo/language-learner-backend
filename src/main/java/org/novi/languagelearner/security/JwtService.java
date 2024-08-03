package org.novi.languagelearner.security;

import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class JwtService {

    @Value("${jwt.SecretKey}")
    private String SECRET_KEY;

    @Value("${jwt.Audience}")
    private String AUDIENCE;

    private String ROLES_CLAIMS_NAME = "roles";

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {

        return extractClaim(token, Claims::getSubject);
    }

    public String extractAudience(String token) {
        return extractClaim(token, Claims::getAudience);
    }

    public List<String> extractRoles(String token) {

        final Claims claims = extractAllClaims(token);
        List<String> roles = claims.get(ROLES_CLAIMS_NAME, List.class);
        if (roles == null) return Collections.emptyList(); // Geen rollen gevonden, retourneer lege lijst
        return roles;
    }

    public List<GrantedAuthority> extractSimpleGrantedAuthorities(String token) {

        List<String> roles = extractRoles(token);
        if (roles == null) return Collections.emptyList(); // Geen rollen gevonden, retourneer lege lijst
        return GetAsListSimpleGrantedAuthorities(roles);
    }

    private static List<GrantedAuthority> GetAsListSimpleGrantedAuthorities(List<String> roles) {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T>
            claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(ApiUserDetails userDetails) {
        return generateToken(userDetails,1000 * 60 * 60 * 24 * 10L); //tien dagen in ms
    }

    public String generateToken(ApiUserDetails userDetails, Long milliSeconds) {
        Map<String, Object> claims = new HashMap<>();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        claims.put(ROLES_CLAIMS_NAME, roles);
        return createToken(claims, userDetails.getUsername(), milliSeconds); //time in milliseconds
    }

    private String createToken(Map<String, Object> claims, String
            subject, long milliSeconds) {

        long currentTime = System.currentTimeMillis();
        return createToken(claims, subject, currentTime, milliSeconds);
    }

    private String createToken(Map<String, Object> claims, String subject, long currentTime, long validPeriod) {
        return Jwts.builder()
                .setClaims(claims)
                .setAudience(AUDIENCE)
                .setSubject(subject)
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(currentTime + validPeriod))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Boolean validateToken(String token)  {
        try {
            String tokenAudience = extractAudience(token);
            boolean isAudienceValid = tokenAudience.equals(AUDIENCE);
            return !isTokenExpired(token) && isAudienceValid;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
