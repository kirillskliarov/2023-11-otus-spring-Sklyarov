package ru.otus.hw.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authz -> authz
                        .requestMatchers("/").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                        .requestMatchers(HttpMethod.GET, "/authors").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                        .requestMatchers(HttpMethod.POST, "/authors").hasAnyAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/genres").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                        .requestMatchers(HttpMethod.POST, "/genres").hasAnyAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/books/*").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                        .requestMatchers(HttpMethod.POST, "/books").hasAnyAuthority("ROLE_ADMIN")
                        .requestMatchers("/books/*/comment").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                        .requestMatchers("/books/*/edit").hasAnyAuthority("ROLE_ADMIN")
                        .requestMatchers("/books/*/delete").hasAnyAuthority("ROLE_ADMIN")
                        .requestMatchers("/login").permitAll()
                        .anyRequest().authenticated()

                )
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(formLogin ->
                        formLogin
                                .permitAll()
                                .defaultSuccessUrl("/")
                );

        return http.build();
    }

    @SuppressWarnings("deprecation")
    @Bean
    public NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }
}
