package com.gary.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity // 客製化SpringSecurity
public class MySecurityConfig {

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        UserDetails user1 = User
                .withUsername("test1")
                .password("{noop}111")
                .roles("ADMIN", "USER")
                .build();
        UserDetails user2 = User
                .withUsername("test2")
                .password("{bcrypt}$2y$10$/G0Z0OQ.5pmwkth5z//WIuGzdungM/JO5gr/vJ3GBhFfSO2i0Tyha") // 222
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user1, user2);
    }
}
