package com.sns.platform.api.config.security;

import com.sns.platform.api.config.jwt.JwtAuthenticationFilter;
import com.sns.platform.api.config.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                // REST API?대?濡?basic auth 諛?csrf 蹂댁븞???ъ슜?섏? ?딆쓬
                .httpBasic().disable()
                .csrf().disable()
                // JWT瑜??ъ슜?섍린 ?뚮Ц???몄뀡???ъ슜?섏? ?딆쓬
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                // ?대떦 API????댁꽌??紐⑤뱺 ?붿껌???덇?
                .requestMatchers("/users/**").permitAll()
                // USER 沅뚰븳???덉뼱???붿껌?????덉쓬
                //.requestMatchers("/members/test").hasRole("USER")
                // ??諛뽰뿉 紐⑤뱺 ?붿껌????댁꽌 ?몄쬆???꾩슂濡??쒕떎???ㅼ젙
                .anyRequest().permitAll()
                .and()
                // JWT ?몄쬆???꾪븯??吏곸젒 援ы쁽???꾪꽣瑜?UsernamePasswordAuthenticationFilter ?꾩뿉 ?ㅽ뻾
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class).build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

