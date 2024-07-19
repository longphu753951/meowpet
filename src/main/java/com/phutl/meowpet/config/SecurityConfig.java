package com.phutl.meowpet.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import com.phutl.meowpet.filters.JwtRequestFilter;

@EnableWebSecurity
@EnableRedisHttpSession
public class SecurityConfig {

        @Value("${api.prefix}")
        private String apiPrefix;

        @Bean
        public JwtRequestFilter jwtRequestFilter() {
                return new JwtRequestFilter();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http.csrf((csrf) -> csrf.disable())
                                .sessionManagement((sessionManagement) -> sessionManagement
                                                .sessionConcurrency((sessionConcurrency) -> sessionConcurrency
                                                                .maximumSessions(1)
                                                                .expiredUrl("/login?expired"))
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authorizeHttpRequests(authorize -> authorize
                                                .requestMatchers(String.format("%users/register", apiPrefix),
                                                                "%users/register", apiPrefix)
                                                .permitAll()
                                                .anyRequest().authenticated())
                                .addFilterBefore(new JwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
                return http.build();
        }

}
