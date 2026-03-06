package com.sns.platform.api.config.jwt;

import com.sns.platform.api.module.user.presentation.dto.UserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JwtTokenProvider implements InitializingBean {
    private final String secret;
    private final long accessTokenValidityInMilliseconds;
    private final long refreshTokenValidityInMilliseconds;
    private Key key;

    public JwtTokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-token-validity-in-seconds}") long accessTokenValidityInSeconds,
            @Value("${jwt.refresh-token-validity-in-seconds}") long refreshTokenValidityInSeconds
    ) {
        this.secret = secret;
        this.accessTokenValidityInMilliseconds = accessTokenValidityInSeconds * 1000L;
        this.refreshTokenValidityInMilliseconds = refreshTokenValidityInSeconds * 1000L;
    }

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createAccessToken(String userEmail, Long userId, String userName) {
        long now = (new Date()).getTime();
        Date validity = new Date(now + this.accessTokenValidityInMilliseconds);

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("userName", userName);

        return Jwts.builder()
                .addClaims(claims)
                .setSubject(userEmail)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    public String createRefreshToken(String userEmail, Long userId) {
        long now = (new Date()).getTime();
        Date validity = new Date(now + this.refreshTokenValidityInMilliseconds);

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("type", "refresh");

        return Jwts.builder()
                .addClaims(claims)
                .setSubject(userEmail)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    public String destroyToken(String token) {
        if (getTokenInfo(token)) {
            Date validity = new Date((new Date()).getTime());
            Map<String, Object> claims = new HashMap<>();

            return Jwts.builder()
                    .addClaims(claims)
                    .signWith(key, SignatureAlgorithm.HS512)
                    .setExpiration(validity)
                    .compact();
        }
        return "유효하지 않습니다.";
    }

    public UserDTO.FindResponse getUserInfo(String token) {
        if (getTokenInfo(token)) {
            token = token.replace("Bearer ", "");
            Claims claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody();
            return UserDTO.FindResponse.builder()
                    .id(Long.parseLong(claims.get("userId").toString()))
                    .build();
        }
        return null;
    }

    public Boolean getTokenInfo(String token) {
        token = token.replace("Bearer ", "");
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
        return claims.getExpiration().after(new Date());
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                authorityOf(String.valueOf(claims.get("userId")));

        User principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public List<GrantedAuthority> authorityOf(String userId) {
        if (userId == null) {
            return Collections.emptyList();
        }
        return Stream.of(new SimpleGrantedAuthority(userId)).collect(Collectors.toList());
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            System.out.println("유효하지 않은 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            System.out.println("JWT가 만료되었습니다.");
        } catch (IllegalArgumentException e) {
            System.out.println("올바르지 않은 JWT입니다.");
        }
        return false;
    }
}
