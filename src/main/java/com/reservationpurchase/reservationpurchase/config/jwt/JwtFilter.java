//package com.reservationpurchase.reservationpurchase.config.jwt;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//import org.springframework.util.StringUtils;
//import java.io.IOException;
//
//
//public class JwtFilter extends BasicAuthenticationFilter {
//    public static final String USERSEQEUNCE_HEADER = "userSeq";
//
//    public JwtFilter(JwtProvider jwtProvider){
//        super(new AuthenticationManager() {
//            @Override
//            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//                return null;
//            }
//        });
//        this.jwtProvider = jwtProvider;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//        String jwt = resolveToken(request);
//        String requestURI = request.getRequestURI();
////        if(requestURI.equals("/error")){
////            log.error("JWT ERROR");
////        }
//        // 토큰 유효성 검증 후 정상이면 SecurityContext에 저장
//        //else
//            if(StringUtils.hasText(jwt) && jwtProvider.validateToken(jwt)){
//            Authentication authentication = jwtProvider.getAuthentication(jwt);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
////        else {
////            log.debug("Valid JWT Not found " + requestURI );
////        }
//
//        // 생성한 필터 실행
//        chain.doFilter(request,response);
//    }
//
//    private String resolveToken(HttpServletRequest request){
//        String bearerToken = request.getHeader(USERSEQEUNCE_HEADER);
//
//        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
//            return bearerToken.replace("Bearer ", "");
//        }
//        return null;
//    }
//}
