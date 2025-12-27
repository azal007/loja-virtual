package br.com.lojavirtual.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final JwtAuthenticationEntryPoint jwtAuthEntryPoint;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter, JwtAuthenticationEntryPoint jwtAuthEntryPoint) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.jwtAuthEntryPoint = jwtAuthEntryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> {})
            .exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthEntryPoint))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // Endpoints publicos - Autenticacao
                .requestMatchers("/auth/**").permitAll()

                // Endpoints publicos - Produtos (apenas GET)
                .requestMatchers(HttpMethod.GET, "/produtos/**").permitAll()

                // Endpoints publicos - Categorias (apenas GET)
                .requestMatchers(HttpMethod.GET, "/categorias/**").permitAll()

                // Endpoint publico - Cadastro de usuario
                .requestMatchers(HttpMethod.POST, "/usuarios").permitAll()

                // Endpoints protegidos - Usuarios (GET, PUT, PATCH, DELETE)
                .requestMatchers("/usuarios/**").authenticated()

                // Endpoints protegidos - Produtos (POST, PUT, PATCH, DELETE)
                .requestMatchers(HttpMethod.POST, "/produtos/**").authenticated()
                .requestMatchers(HttpMethod.PUT, "/produtos/**").authenticated()
                .requestMatchers(HttpMethod.PATCH, "/produtos/**").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/produtos/**").authenticated()

                // Endpoints protegidos - Categorias (POST, PUT, PATCH, DELETE)
                .requestMatchers(HttpMethod.POST, "/categorias/**").authenticated()
                .requestMatchers(HttpMethod.PUT, "/categorias/**").authenticated()
                .requestMatchers(HttpMethod.PATCH, "/categorias/**").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/categorias/**").authenticated()

                // Qualquer outro endpoint requer autenticacao
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
