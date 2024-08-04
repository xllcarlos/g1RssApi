package com.g1RssApi.services;

import com.g1RssApi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Chamado automaticamente pelo SpringSecurity
 * toda vez que um usuario precisar se autenticar
 * implementando o UserDetailsService.
 *
 * @author Jhonatan Isaias
 */
@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired // Injecao de dependencias
    private UserRepository userRepository;

    /**
     * Consulta dos usuarios para o SpringSecurity.
     *
     * @param username usuario a ser buscado no banco de dados.
     * @return objeto da consulta feita pelo repository.
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByLogin(username);
    }

}
