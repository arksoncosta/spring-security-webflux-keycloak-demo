package com.arkson.webfluxsecurityapi.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Map;

@RequiredArgsConstructor
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    public static final String REALM_ACCESS = "realm_access";
    public static final String ROLES = "roles";
    public static final String ROLE_PREFIX = "ROLE_";

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .authorizeExchange(this::buildPathPermissions)
                .oauth2ResourceServer(this::customizeOAuthResourceServer)
                .build();
    }

    private ServerHttpSecurity.AuthorizeExchangeSpec buildPathPermissions(ServerHttpSecurity.AuthorizeExchangeSpec exchange) {
        return exchange.pathMatchers("/customers/**").hasAnyRole("USERS")
                .pathMatchers("/search/**").hasAnyRole("COMMON")
                .anyExchange()
                .authenticated();
    }

    private ServerHttpSecurity.OAuth2ResourceServerSpec customizeOAuthResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec oauth2Configurer) {
        return oauth2Configurer.jwt(jwtConfigurer ->
                jwtConfigurer.jwtAuthenticationConverter(jwt -> {
                    final Map<String, Collection<String>> realmAccess = jwt.getClaim(REALM_ACCESS);
                    var grantedAuthorities = realmAccess.get(ROLES)
                            .stream()
                            .map(role -> new SimpleGrantedAuthority(ROLE_PREFIX + role))
                            .toList();
                    return Mono.just(new JwtAuthenticationToken(jwt, grantedAuthorities));
                }));
    }
}
