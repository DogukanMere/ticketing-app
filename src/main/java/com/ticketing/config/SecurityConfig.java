package com.ticketing.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder){

        List<UserDetails> userList = new ArrayList<>();

        userList.add(
                new User("dgkn", encoder.encode("password"), Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"))));
        userList.add(
                new User("admin", encoder.encode("password"), Arrays.asList(new SimpleGrantedAuthority("ROLE_MANAGER"))));


        return new InMemoryUserDetailsManager(userList);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/user/**").hasRole("ADMIN")
                        .requestMatchers("/project/**").hasRole("MANAGER")
                        .requestMatchers("/task/employee/**").hasRole("EMPLOYEE")
                        .requestMatchers("/task/**").hasRole("MANAGER")
//                        .requestMatchers("/task/**").hasAnyRole("EMPLOYEE", "ADMIN")
//                        .requestMatchers("task/**").hasAuthority("ROLE_EMPLOYEE")
                        .requestMatchers("/login", "/", "fragments/**", "/assets/**", "/images/**").permitAll()
                        .anyRequest().authenticated()).httpBasic(Customizer.withDefaults());

        return http.build();
    }
}

