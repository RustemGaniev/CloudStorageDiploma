package ru.netology.cloudstorage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.netology.cloudstorage.security.JWTFilter;
import ru.netology.cloudstorage.security.JWTUtil;

/**
 * @author VladSemikin
 */

@Configuration
public class CloudStorageConfiguration {

    private final UserDetailsService userDetailsService;


    public CloudStorageConfiguration(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public JWTFilter jwtFilter() {
        return new JWTFilter(jwtUtil());
    }

    @Bean
    public JWTUtil jwtUtil() {
        return new JWTUtil(userDetailsService);
    }
}
