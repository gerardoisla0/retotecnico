package com.seek.retotecnico.api.config;

import com.seek.retotecnico.model.util.enums.TechnicalMessage;
import com.seek.retotecnico.model.util.exception.BusinessException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import reactor.core.publisher.Mono;
import org.springframework.web.server.ServerWebExchange;

import java.util.Arrays;

public class JwtServerAuthenticationConverter implements
        ServerAuthenticationConverter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtServerAuthenticationConverter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        String token = extractTokenFromRequest(exchange.getRequest().getHeaders().getFirst("Authorization"));
        if (token != null) {
            if (jwtTokenProvider.validateToken(token)) {
                String username = jwtTokenProvider.getUsernameFromToken(token);
                return Mono.just(new UsernamePasswordAuthenticationToken(
                        username, token, Arrays.asList(new SimpleGrantedAuthority("USER"))));
            } else {
                return Mono.error(new BusinessException(TechnicalMessage.ERROR_TOKEN_EXPIRED));
            }
        }
        return Mono.empty();
    }

    private String extractTokenFromRequest(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }
}
