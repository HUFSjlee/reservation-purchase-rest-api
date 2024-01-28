//package com.reservationpurchase.reservationpurchase.config.jwt;
//
//import java.util.*;
//
//import io.jsonwebtoken.SignatureAlgorithm;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.*;
//import org.springframework.stereotype.Component;
//import io.jsonwebtoken.Jwts;
//import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;
//
//@Component
//public class JwtProvider {
//
//    private final String SECRET_KEY;
//    private final long EXPIRE_TIME;
//
//    // 생성자 메소드
//    public JwtProvider(@Value("${jwt.token.secret-key}") String secretKey, @Value("${jwt.token.expire-time}") long expireTime) {
//        this.SECRET_KEY = secretKey;
//        this.EXPIRE_TIME = expireTime;
//    }
//
//    /**
//     * Authentication 기반 토큰 생성 메소드.
//     * {@link #generateToken(String, Collection)}
//     * @param authentication
//     * @return JWT(String)
//     */
//    public String generateToken(Authentication authentication) {
//        return generateToken(authentication.getName(), authentication.getAuthorities());
//    }
//
//    /**
//     * Username 및 Authorities 기반 토큰 생성 메소드.
//     * @param username
//     * @param authorities
//     * @return JWT(String)
//     */
//    public String generateToken(String username, Collection<? extends GrantedAuthority> authorities) {
//        return Jwts.builder()
//                .setSubject(username)
//                .claim("role", authorities.stream().findFirst().get().toString())
//                .setExpiration(getExpireDate())
//                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
//                .compact();
//    }
//
//    /**
//     * 토큰으로부터 받은 정보를 기반으로 Authentication 객체를 반환하는 메소드.
//     * @param accessToken
//     * @return Authentication
//     */
//    public Authentication getAuthentication(String accessToken) {
//        //return new UsernamePasswordAuthenticationToken(getUsername(accessToken), "", createAuthorityList(getUserSeq(accessToken)));
//        return new UsernamePasswordAuthenticationToken("", createAuthorityList(getUserSeq(accessToken)));
//    }
//
//    /**
//     * 사용자가 보낸 요청 헤더의 'userSeq' 필드에서 토큰을 추출하는 메소드.
//     * @param request
//     * @return token(String)
//     */
//    public String resolveToken(HttpServletRequest request) {
//        return request.getHeader("userSeq");
//    }
//
//    public boolean validateToken(String accessToken) {
//        if (accessToken == null) {
//            return false;
//        }
//
//        try {
//            return Jwts.parser()
//                    .setSigningKey(SECRET_KEY)
//                    .parseClaimsJws(accessToken)
//                    .getBody()
//                    .getExpiration()
//                    .after(new Date());
//        }
//        catch (Exception e) {
//            return false;
//        }
//    }
//
////    private String getUsername(String accessToken) {
////        return Jwts.parser()
////                .setSigningKey(SECRET_KEY)
////                .parseClaimsJws(accessToken)
////                .getBody()
////                .getSubject();
////    }
//
//    private String getUserSeq(String accessToken) {
//        return (String) Jwts.parser()
//                .setSigningKey(SECRET_KEY)
//                .parseClaimsJws(accessToken)
//                .getBody()
//                .get("userSeq", String.class);
//
//    }
//
//    private Date getExpireDate() {
//        Date now = new Date();
//        return new Date(now.getTime() + EXPIRE_TIME);
//    }
//}
