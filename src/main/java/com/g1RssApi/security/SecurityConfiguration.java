package com.g1RssApi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Altera as configuracoes padroes do SpringSecurity
 * e definindo novos padroes.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired  // Injecao de dependencias
    private SecurityFilter securityFilter;

    /**
     * Aplica filtros a requisicao, fazendo a corrente de segurancao.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Sem guardar estado
                .authorizeHttpRequests((authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/auth/register", "auth/login").permitAll()  // AuthenticationController
                        .requestMatchers(HttpMethod.POST, "/categories", "/notices").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/categories", "/notices").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/categories", "/notices").permitAll()
                        .anyRequest().authenticated()))
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /**
     * Gera a instancia do AuthenticationManeger.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Gera a instancia do PasswordEncoder para realizar o hash das senhas
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
