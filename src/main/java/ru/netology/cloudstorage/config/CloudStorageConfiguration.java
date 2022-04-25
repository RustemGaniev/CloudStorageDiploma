package ru.netology.cloudstorage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.netology.cloudstorage.repository.UserRepository;
import ru.netology.cloudstorage.security.JWTFilter;
import ru.netology.cloudstorage.security.JWTUtil;
import ru.netology.cloudstorage.security.MyUserDetailsService;

/**
 * @author VladSemikin
 */

@Configuration
public class CloudStorageConfiguration {

    @Bean
    public JWTFilter jwtFilter(UserDetailsService userDetailsService) {
        return new JWTFilter(jwtUtil(userDetailsService));
    }

    @Bean
    public JWTUtil jwtUtil(UserDetailsService userDetailsService) {
        return new JWTUtil(userDetailsService);
    }

    @Bean
    public MyUserDetailsService myUserDetailsService(UserRepository userRepository){
        return new MyUserDetailsService(userRepository);
    }
}
