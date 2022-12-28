package com.trollmarket.utility;


import io.jsonwebtoken.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtToken {

    private String SECRET_KEY = "liberate-tutume-ex-inferis-ad-astra-per-aspera";
//    private String audience = "trollmarket"; //Troll Market, disabled because for what

    private Claims getClaims(String token) {

        JwtParser parser = Jwts.parser().setSigningKey(SECRET_KEY);
        Jws<Claims> signatureAndClaims = parser.parseClaimsJws(token);
        Claims claims = signatureAndClaims.getBody();

        return claims;
    }

    public String getUsername(String token) {

        Claims claims = getClaims(token);

        return claims.get("username", String.class);
    }

    public String generateToken(
            String username,
            String secretKey,
            String role) {

        JwtBuilder builder = Jwts.builder();
        builder = builder.claim("username", username)
                .claim("role", role)
                .setIssuer("http://localhost:8080/troll-market")
                .setIssuedAt(new Date())
//                .setExpiration(new Date((new Date()).getTime() + 60000))
                .signWith(SignatureAlgorithm.HS256, secretKey);

        return builder.compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {

        Claims claims = getClaims(token);
        String user = claims.get("username", String.class);
        String role = claims.get("role",String.class);

        return (user.equals(userDetails.getUsername())
                && role.equals(userDetails.getAuthorities().toArray()[0].toString()));
    }
}

