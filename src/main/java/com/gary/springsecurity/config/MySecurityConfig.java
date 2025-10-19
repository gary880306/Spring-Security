package com.gary.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 客製化SpringSecurity
@EnableMethodSecurity
public class MySecurityConfig {

//        @Bean
//        public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
//            UserDetails user1 = User
//                    .withUsername("test1")
//                    .password("{noop}111")
//                    .roles("ADMIN", "USER")
//                    .build();
//            UserDetails user2 = User
//                    .withUsername("test2")
//
//            .password("{noop}222") // 222
//                    .roles("USER")
//                    .build();
//            return new InMemoryUserDetailsManager(user1, user2);
//        }

    @Bean
    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/welcome", "/register").permitAll() // url = welcome or register 允許所有人
                        .requestMatchers("/**").authenticated()
                        .requestMatchers("/hello").hasAnyRole("ADMIN", "NORMAL_MEMBER")
                        .requestMatchers("/authorization").hasAnyRole("ADMIN")
                        .anyRequest().denyAll()
                )
                .build();
    }
}
