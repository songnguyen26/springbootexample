package com.songnguyen.example05.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.songnguyen.example05.Security.JWTFilter;
import com.songnguyen.example05.Service.Impl.UserDetailsServiceImpl;

import jakarta.servlet.http.HttpServletResponse;
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    private JWTFilter jwtFilter;
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf->csrf.disable()).
        authorizeHttpRequests(requests->requests
        .requestMatchers(AppConstants.PUBLIC_URLS).permitAll()
        .requestMatchers(AppConstants.USER_URLS).hasAnyAuthority("USER","ADMIN")
        .requestMatchers(AppConstants.ADMIN_URLS).hasAnyAuthority("ADMIN")
        .anyRequest()
        .authenticated())
        .exceptionHandling(handling->handling.authenticationEntryPoint(
            (request,respone,authException)->
            respone.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Unauthorized")))
            .sessionManagement(management->management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        http.authenticationProvider(daoAuthenticationProvider());
        DefaultSecurityFilterChain defaultSecurityFilterChain=http.build();
        return defaultSecurityFilterChain;
    }
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsServiceImpl);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }
}
