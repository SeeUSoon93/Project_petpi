package com.soon.petpi.provider;

import com.soon.petpi.model.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Component
public class JwtProvider {

    @Value("${jwt.secret_key}")
    private String secretKey;

    //jwt 토큰 생성 메소드
    public String create(User user){


        Date expiredDate = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        Claims claims = Jwts.claims()
                .setSubject(user.getUserEmail());

        claims.put("userIdx", user.getUserIdx());

        String jwt = Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .setClaims(claims)
                .setIssuedAt(new Date()).setExpiration(expiredDate)
                .compact();

        log.info("token = {}", jwt);

        return jwt;

    }

    // 토큰 유효성 검증 메소드
    public String validate (String jwt){
        String subject = null;
        log.info("JWT = {}", jwt);
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        try{
            subject = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody()
                    .getSubject();
        }catch (Exception exception){
            exception.printStackTrace();
            System.out.println("Invalid token: " + jwt);
            return null;
        }
        return subject;
    }
    private Claims getClaims(String token){
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Long getUserIdx(String token){
        Claims claims = getClaims(token);
        return claims.get("userIdx", Long.class);
    }
}
