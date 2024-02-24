package com.kbtg.bootcamp.posttest.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain appSecurity(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests((authorize) -> {
            authorize.requestMatchers("/lotteries/**").permitAll();
            authorize.requestMatchers("/users/**").permitAll();
            authorize.requestMatchers("/admin/**").hasRole("ADMIN");
            authorize.anyRequest().authenticated();
        });

        return http.build();
    }

    @Bean
    public UserDetailsManager inMemoryUserDetailsManager() {
        UserDetails user1 = User.builder()
                .username("username")
                .password("{noop}password")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user1);
    }

}
