package com.intern.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TokenProvider {

    private final Logger log = LoggerFactory.getLogger(TokenProvider.class);
    private static final String AUTHORITIES_KEY = "auth";

    private Key key;
    private long tokenValidityInMilliseconds;
    private long tokenValidityInMillisecondsForRememberMe;

    public TokenProvider() {
    }

    @PostConstruct
    public void init() {
        // Khai báo chuỗi bí mật key dùng để mã hóa và giải mã JWT
        String secret = "NmZhZmQwOGJjZDBiNjNmZDBjYzRjNGIxNmNlZWRhMDdkNTI0YTdiYWFmNDQ5NjVlMmM2OGZhYjlmOWJmNjA1YjRhMGVlZmRkOGZmMjM5Mjg5NmVhZDEwN2IwN2I4Y2ZlYzUzNDg4NTdkZGNhZjg1ZGVhNWQxZjUyZTQyNWY1YTk=";
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        this.key = Keys.hmacShaKeyFor(keyBytes);

        // Khai báo lượng thời gian có hiệu lực của JWT
        this.tokenValidityInMilliseconds = 1000 * 60 * 60 * 24; // 24H = 1000 * 60 * 60 * 24 ms
    }

    /*Hàm tạo Token*/
    public String createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();

        // Thời gian hết hạn của JWT = Hiện tại + Lượng thời gian hiệu lực
        Date validity = new Date(now + this.tokenValidityInMilliseconds);

        // Dùng Jwts để tạo ra chuỗi JWT
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    // Giải mã JWT và Tạo Authentication: Dùng khi 1 request gửi lên chúng ta verify JWT
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.info("Invalid JWT token.");
            log.trace("Invalid JWT token trace.", e);
        }
        return false;
    }
}