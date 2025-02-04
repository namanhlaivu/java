package com.devteria.project1.configuration;

import com.devteria.project1.enums.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;
@Configuration
@EnableWebSecurity
public class SecurityConfig {


        private final String[] PUCLIC_ENDPOINTS = {"/user" , "/auth/token", "auth/introspect",};

        @Value("${jwt.signerKey}")
        private String signerKey;

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
            httpSecurity.authorizeHttpRequests(requests ->
                    requests.requestMatchers(HttpMethod.POST, PUCLIC_ENDPOINTS).permitAll()
                            .requestMatchers(HttpMethod.GET, "/user")
                            .hasRole(Role.ADMIN.name())//phải là ADMIN mới vào đc SCOPE Thay la ROLE
                            .requestMatchers(HttpMethod.POST, PUCLIC_ENDPOINTS).permitAll()
                            .requestMatchers(HttpMethod.PUT, PUCLIC_ENDPOINTS).permitAll()
                            .requestMatchers(HttpMethod.DELETE, PUCLIC_ENDPOINTS).permitAll()
                            .anyRequest().authenticated());

            httpSecurity.oauth2ResourceServer(oathu2 ->
                    oathu2.jwt(jwtConfigurer ->
                            jwtConfigurer.decoder(jwtDecoder())
                                    .jwtAuthenticationConverter(jwtAuthenticationConverter()))
            );

            httpSecurity.csrf(AbstractHttpConfigurer::disable);
            return httpSecurity.build();
        }

        @Bean
        JwtAuthenticationConverter jwtAuthenticationConverter() {
            JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
            jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

            JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
            jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
            return jwtAuthenticationConverter;
        };
    @Bean
        JwtDecoder jwtDecoder() {
            SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS256");

        return NimbusJwtDecoder.withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();
    }
    @Bean
    PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder(10);
    }
    }

