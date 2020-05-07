package com.autohome.config;

import com.autohome.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtTokenUtil implements Serializable {

    private static final long ACCESS_TOKEN_VALIDITY_SECONDS = 5*60*60;
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String ROLE = "role";
    private static final Logger log = LoggerFactory.getLogger(JwtTokenUtil.class);

    @Value("${jwt.signature.key}")
    private String signatureKey;



    public String generateToken(User user){
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put(FIRST_NAME, user.getFirstName());
        claims.put(LAST_NAME, user.getLastName());
        claims.put(ROLE,user.getRole());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("autohome")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date((System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS*1000)))
                .signWith(SignatureAlgorithm.HS256,signatureKey)
                .compact();
    }

    public Optional<User> parseToken(String token){
        try {
            Claims claims = Jwts.parser().setSigningKey(signatureKey).parseClaimsJws(token).getBody();

            User user = new User();
            user.setEmail(claims.getSubject());
            user.setFirstName(String.valueOf(claims.get(FIRST_NAME)));
            user.setLastName(String.valueOf(claims.get(LAST_NAME)));
            user.setRole(String.valueOf(claims.get(ROLE)));

            return Optional.ofNullable(user);
        } catch (JwtException e){
            log.error("Error while parsing Token {}",token,e);
            return Optional.empty();
        }
    }


}
