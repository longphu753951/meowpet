package com.phutl.meowpet.core.filters;

import java.io.IOException;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import com.phutl.meowpet.core.components.JwtTokenUtil;
import com.phutl.meowpet.modules.database.User;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;

//filter theo từng request một
//  
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    @Value("${api.prefix}")
    private String apiPrefix;

    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        try {
            if (isBypassToken(request)) {
                filterChain.doFilter(request, response); // được phép đi qua
                return;
            }
            final String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                return;
            }
            final String token = authHeader.substring(7);
            final String email = jwtTokenUtil.extractEmail(token);
            if (email != null && SecurityContextHolder.getContext().getAuthentication() != null) {
                // Ép kiểu UserDetails sang User vì User implement UserDetails
                User existingUser = (User) userDetailsService.loadUserByUsername(email);
                if (jwtTokenUtil.validateToken(token, existingUser)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            existingUser,
                            null,
                            existingUser.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }

    }

    private boolean isBypassToken(@NonNull HttpServletRequest request) {
        final List<Pair<String, String>> bypassTokens = Arrays.asList(
                //health check
                Pair.of(String.format("%s/actuator/**", apiPrefix), "GET"),
                Pair.of(String.format("%s/healthcheck/**", apiPrefix), "GET"),


                Pair.of(String.format("%s/products", apiPrefix), "GET"),
                Pair.of(String.format("%s/categories", apiPrefix), "GET"),
                Pair.of(String.format("%s/users/register", apiPrefix), "POST"),
                Pair.of(String.format("%s/users/login", apiPrefix), "POST")
                );
        
        String requestPath = request.getServletPath();
        String requestMethod = request.getMethod();

        for (Pair<String, String> bypassToken : bypassTokens) {
            String tokenPath = bypassToken.getFirst();
            String tokenMethod = bypassToken.getSecond();
            if(tokenPath.contains("**")) {
                String regexPath = tokenPath.replace("**", ".*");
                //Create a pattern to match the request path
                Pattern pattern = Pattern.compile(regexPath);
                Matcher matcher = pattern.matcher(request.getServletPath());
                if(matcher.matches() && requestMethod.equals(tokenMethod)) {
                    return true;
                }
            }
            else if(requestPath.equals(tokenPath) && requestMethod.equals(tokenMethod)) {
                return true;
            }
        }
        return false;
    }
}
