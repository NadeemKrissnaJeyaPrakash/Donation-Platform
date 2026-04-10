package com.codelock.donation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // Added /error and /h2-console to the public list
                .requestMatchers("/", "/register", "/login", "/error", "/h2-console/**").permitAll()
                .anyRequest().authenticated()
            )
            // Officially tells Spring to use our custom login page
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/", true) // Send them to the home page after logging in
                .permitAll()
            )
            .csrf(csrf -> csrf.disable()) 
            // The H2 console uses frames, so we have to tell Spring not to block them
            .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));

        return http.build();
    }
}