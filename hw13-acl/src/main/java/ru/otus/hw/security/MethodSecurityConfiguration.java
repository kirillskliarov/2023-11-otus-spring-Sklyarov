package ru.otus.hw.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableMethodSecurity(
        securedEnabled = true,
        prePostEnabled = false
)
public class MethodSecurityConfiguration {
}
