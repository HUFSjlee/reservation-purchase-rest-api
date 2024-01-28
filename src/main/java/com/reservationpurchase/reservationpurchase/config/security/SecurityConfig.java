package com.reservationpurchase.reservationpurchase.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .cors()
                .and()
//                .authorizeHttpRequests()
                //.requestMatchers("/auth/**","/exc","/swagger-ui/**","/v3/**").permitAll()
                //.requestMatchers(HttpMethod.GET,"/exc","/routines/**").permitAll()
                //.requestMatchers("/members/**","/member-event","/personal-trainings/{ptSeq}/review","/openvidu/**","/exercise-history/*").hasAuthority("member")
                //.requestMatchers("/exercises/*","/routines").hasAuthority("admin")
                //.requestMatchers("/trainer","/personal-trainings","/openvidu/**","/trainer-feedbacks").hasAuthority("trainer")
//                .and()
                .authorizeHttpRequests()
                //.anyRequest().authenticated()
                .anyRequest().permitAll()
                .and()
                //.addFilterBefore(filterFactory.testAuthorization(), BasicAuthenticationFilter.class)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .build();
    }
}
