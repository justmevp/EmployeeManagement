package com.example.management.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.example.management.filter.CsrfCookiesFilter;
import com.example.management.filter.JwtTokenGeneratorFilter;
import com.example.management.filter.JwtTokenValidatiorFilter;
import com.example.management.filter.AuthoritiesLoggingfAfterFilter;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName("_csrf");
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .cors().configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest httpServletRequest) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setExposedHeaders(Arrays.asList("Authorization"));
                        config.setMaxAge(3600L);
                        return config;
                    }
                }).and().csrf((csrf) -> csrf.csrfTokenRequestHandler(requestHandler)
                        .ignoringRequestMatchers("/api/users/add")
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .addFilterAfter(new CsrfCookiesFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new AuthoritiesLoggingfAfterFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new JwtTokenGeneratorFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new JwtTokenValidatiorFilter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests()
                .requestMatchers("/api/employees/add","/api/employees/{employeeId}",
                        "/api/employees/{employeeId}/update", "/api/total-cost",
                        "/api/attendance/leave-requests", "/api/employees/by-position/{positionName}",
                        "api/employees/sorted-by-salary","api/employees/sorted-by-salary-slice")
                .hasRole("ADMIN")
                .requestMatchers("/api/attendance/checkin", "/api/attendance/checkout", "/api/attendance/onleave",
                        "/api/attendance/{employeeId}/working-hours", "/api/department/employee-count")
                .hasAnyRole("USER", "ADMIN")
                .requestMatchers("/api/users").authenticated()
                .requestMatchers("/api/department/with-positions").authenticated()
                .requestMatchers("/api/attendance/employee-count").authenticated()
                .requestMatchers("/api/users/add").permitAll()
                .requestMatchers("/welcome").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/v3/api-docs/**").permitAll()
                .and().formLogin()
                .and().httpBasic();

        httpSecurity.headers().frameOptions().disable();
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
