package jav.bot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import jav.bot.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserService userDetailsService;

    public SecurityConfig(UserService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorizeRequests) -> {
                    authorizeRequests
                            .requestMatchers(HttpMethod.GET, "/jokes", "/jokes/*").permitAll()
                            .requestMatchers(HttpMethod.POST, "/jokes").authenticated()
                            .requestMatchers(HttpMethod.PUT, "/jokes/*").hasRole("MODERATOR")
                            .requestMatchers(HttpMethod.DELETE, "/jokes/*").hasRole("MODERATOR")
                            .requestMatchers("/register", "/registerView", "/login", "/users/register").permitAll()
                            .requestMatchers("/users/**").hasRole("ADMIN")
                            .requestMatchers("/actuator/**").permitAll()
                            .anyRequest().authenticated();
                }).formLogin((form) -> {
                    form.loginPage("/login").defaultSuccessUrl("/jokes", true).permitAll();
                }).logout((logout) -> {
                    logout.logoutUrl("/logout").logoutSuccessUrl("/login");
                })
                .csrf(csrf -> csrf.disable());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
