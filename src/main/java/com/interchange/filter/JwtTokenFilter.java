package com.interchange.filter;

import com.interchange.jwt.JwtTokenUtil;
import com.interchange.service.impl.UserDetailServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final UserDetailServiceImpl userDetailService;

    private final JwtTokenUtil jwtTokenUtil;


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            if (isBypassToken(request)) {
                filterChain.doFilter(request, response); //enable bypass
                return;
            }

                final String authHeader = request.getHeader("Authorization");
                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                }
                if (authHeader != null
                    && authHeader.startsWith("Bearer ")) {
                final String token = authHeader.substring(7);
                final String userId = jwtTokenUtil.extractUserId(token);
                if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailService.loadUserByUsername(userId);
                    if (jwtTokenUtil.validateToken(token, userDetails)) {
                        UsernamePasswordAuthenticationToken authenticationToken =
                                new UsernamePasswordAuthenticationToken(
                                        userDetails, null, userDetails.getAuthorities());
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
            }

            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }
    }
    private boolean isBypassToken(@NonNull HttpServletRequest request) {
        final List<Pair<String, String>> bypassTokens = Arrays.asList(
                Pair.of("/api/auth/login", "POST"),
                Pair.of("/api/auth/registration", "POST"),
                Pair.of("/api/auth/registerOTPAuthentication", "POST"),
                Pair.of("/api/auth/forgetPassword", "POST"),
                Pair.of("/api/auth/forgetPasswordOTPAuthentication","PUT")

        );
        for (Pair<String, String> bypassToken : bypassTokens) {
            if(request.getServletPath().contains(bypassToken.getFirst()) &&
                    request.getMethod().equals(bypassToken.getSecond())) {
                return true;
            }
        }
//        return false;
        return true;
    }
}