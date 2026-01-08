package com.robo.RideWithUs.security;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
	
	private static final String SECRET_KEY ="RIDEWITHUS_SECRET_KEY_256_BIT_FOR_JWT_TOKEN";
	private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000; // 1 day
	
	private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    }

	// Generate JWT
    public String generateToken(String mobile, String role) {

        return Jwts.builder()
                .setSubject(mobile)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + EXPIRATION_TIME)
                )
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
	    
	 //  Extract mobile number
	    public String extractMobile(String token) {
	        return Jwts.parserBuilder()
	                .setSigningKey(getSigningKey())
	                .build()
	                .parseClaimsJws(token)
	                .getBody()
	                .getSubject();
	    }
	    
	
	    // Extract role
	    public String extractRole(String token) {
	        return Jwts.parserBuilder()
	                .setSigningKey(getSigningKey())
	                .build()
	                .parseClaimsJws(token)
	                .getBody()
	                .get("role", String.class);
	    }
	
	    //  Validate token
	    public boolean validateToken(String token) {
	        try {
	            Claims claims = Jwts.parserBuilder()
	                    .setSigningKey(getSigningKey())
	                    .build()
	                    .parseClaimsJws(token)
	                    .getBody();

	            return claims.getExpiration().after(new Date());
	        } catch (Exception e) {
	            return false;
	        }
	    }

}