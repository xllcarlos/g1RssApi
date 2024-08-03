package com.g1RssApi.controllers;

import com.g1RssApi.dtos.AuthenticationUserDTO;
import com.g1RssApi.dtos.RegisterUserDTO;
import com.g1RssApi.services.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired  // Injecao de dependencias
    private AuthenticationService authenticationService;

    /**
     * Rota para verificar login informado.
     *
     * @param authenticationDTO dto com informacoes de login.
     * @return ResponseEntity provido pelo AuthenticationService.
     */
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationUserDTO authenticationDTO) {
        return this.authenticationService.login(authenticationDTO);
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterUserDTO registerUserDTO) {
        return this.authenticationService.register(registerUserDTO);
    }
}
