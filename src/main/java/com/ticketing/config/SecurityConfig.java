package com.ticketing.config;

import com.ticketing.service.SecurityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    private final SecurityService securityService;

    private final AuthSuccessHandler authSuccessHandler;

    public SecurityConfig(SecurityService securityService, AuthSuccessHandler authSuccessHandler) {
        this.securityService = securityService;
        this.authSuccessHandler = authSuccessHandler;
    }

//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder encoder){
//
//        List<UserDetails> userList = new ArrayList<>();
//
//        userList.add(
//                new User("dgkn", encoder.encode("password"), Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"))));
//        userList.add(
//                new User("admin", encoder.encode("password"), Arrays.asList(new SimpleGrantedAuthority("ROLE_MANAGER"))));
//
//
//        return new InMemoryUserDetailsManager(userList);
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/user/**").hasAuthority("Admin")
                        .requestMatchers("/project/**").hasAuthority("Manager")
                        .requestMatchers("/task/employee/**").hasAuthority("Employee")
                        .requestMatchers("/task/**").hasAuthority("Manager")
//                        .requestMatchers("/task/**").hasAnyRole("EMPLOYEE", "ADMIN")
//                        .requestMatchers("task/**").hasAuthority("ROLE_EMPLOYEE")
                        .requestMatchers("/login", "/", "fragments/**", "/assets/**", "/images/**").permitAll()
                        .anyRequest()
                        .authenticated())
                .formLogin((form) -> form
                        .loginPage("/login")
//                        .defaultSuccessUrl("/welcome")
                        .successHandler(authSuccessHandler)
                        .failureUrl("/login?error=true")
                        .permitAll()
                ).logout((logout) -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login"))
                .rememberMe((remember) -> remember
                        .tokenValiditySeconds(120)
                        .key("dgkn")
                        .userDetailsService(securityService)
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}

