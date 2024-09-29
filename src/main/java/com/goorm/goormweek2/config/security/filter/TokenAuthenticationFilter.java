package com.goorm.goormweek2.config.security.filter;

import com.goorm.goormweek2.auth.application.dto.TokenDTO;
import com.goorm.goormweek2.config.security.provider.TokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader("Authorization");
        TokenDTO jwtTokenDto = tokenProvider.resolveToken(request);
        if (token != null && token.startsWith("Bearer ")) {
            if (!tokenProvider.validateToken(액세스토큰) {
                response.sendError(401, "만료되었습니다.");
                throw new ExpiredJwtException(null, null, "Token has expired");
            }
            Authentication authentication = tokenProvider.getAuthentication(액세스토큰);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}
