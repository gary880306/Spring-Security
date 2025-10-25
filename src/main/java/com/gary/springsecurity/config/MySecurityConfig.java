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
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity(debug = true) // 客製化SpringSecurity
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
                .csrf(csrf -> csrf.disable()) // 關掉CSRF保護，SpringSecurity預設開啟
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/welcome", "/register").permitAll() // url = welcome or register 允許所有人
                        .requestMatchers("/**").authenticated()
                        .requestMatchers("/hello").hasAnyRole("ADMIN", "NORMAL_MEMBER")
                        .requestMatchers("/authorization").hasAnyRole("ADMIN")
                        .anyRequest().denyAll()
                )
                .addFilterBefore(new MyFilter1(), BasicAuthenticationFilter.class)
                .addFilterBefore(new MyFilter2(), MyFilter1.class)
                .cors(cors -> cors.configurationSource(createCorsConfig()))
                .build();
    }

    private CorsConfigurationSource createCorsConfig() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*"); // 允許的請求來源有哪些
        config.addAllowedHeader("*"); // 允許 request header 有哪些
        config.addAllowedMethod("*"); // 允許 http method 有哪些
//        config.setAllowCredentials(true);  // 後端是否允許前端帶上 cookie
        config.setMaxAge(3600L); // 設定 Preflight 請求的結果，可以背瀏覽器 cache 幾秒
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
