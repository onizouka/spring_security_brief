package org.example.spring_security_brief.config;

import org.example.spring_security_brief.service.AuthService;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    @Value("${jwt.secret}")
    private String jwtSecret;

    public SecurityConfig(AuthService authServiceInjected){
        this.userDetailsService = authServiceInjected;
    }
    private static final String BOOKS_API = "/api/books/**";
    private static final String ADMIN_SCOPE = "SCOPE_ROLE_ADMIN";
    private static final String USER_SCOPE = "SCOPE_ROLE_USER";
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)  {
       return http
               // CSRF disabled because the application is a stateless REST API using JWT
                .csrf( csrf -> csrf.disable())
               .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(Customizer.withDefaults())
               .authenticationProvider(authenticationProvider())
               .authorizeHttpRequests(auth -> auth
                       .requestMatchers("/auth/**").permitAll()
                       .requestMatchers(HttpMethod.GET, BOOKS_API)
                       .hasAnyAuthority(USER_SCOPE, ADMIN_SCOPE)
                       .requestMatchers(HttpMethod.POST, BOOKS_API)
                       .hasAuthority(ADMIN_SCOPE)
                       .requestMatchers(HttpMethod.PUT, BOOKS_API)
                       .hasAuthority(ADMIN_SCOPE)
                       .requestMatchers(HttpMethod.DELETE, BOOKS_API)
                       .hasAuthority(ADMIN_SCOPE)
                       .anyRequest().authenticated()
               )
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            {

        return configuration.getAuthenticationManager();
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(userDetailsService);

        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public JwtEncoder jwtEncoder() {
        var key = new SecretKeySpec(jwtSecret.getBytes(), "HmacSHA256");
        var immutableSecret = new ImmutableSecret<>(key);
        return new NimbusJwtEncoder(immutableSecret);
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        var originalKey = new SecretKeySpec(jwtSecret.getBytes(), "HmacSHA256");
        return NimbusJwtDecoder.withSecretKey(originalKey).build();
    }

}

