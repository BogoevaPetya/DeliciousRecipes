package com.softuni.DeliciousRecipes.config;

import com.softuni.DeliciousRecipes.repository.UserRepository;
import com.softuni.DeliciousRecipes.service.UserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .authorizeHttpRequests(
                        authorizeRequests -> {
                            authorizeRequests
                                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                    .requestMatchers("/", "/users/login", "/users/login-error", "/users/register", "error").permitAll()
                                    .anyRequest().authenticated();
                        }
                )
                .formLogin(formLogin -> {
                    formLogin
                            .loginPage("/users/login")
                            .usernameParameter("username")
                            .passwordParameter("password")
                            .defaultSuccessUrl("/")
                            .failureForwardUrl("/users/login-error");
                })
                .logout(logout -> {
                    logout
                            .logoutUrl("/users/logout")
                            .logoutSuccessUrl("/")
                            .invalidateHttpSession(true);
                })
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository){
        return new UserDetailsService(userRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new Pbkdf2PasswordEncoder();
    }
}
