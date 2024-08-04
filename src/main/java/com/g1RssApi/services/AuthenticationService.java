package com.g1RssApi.services;

import com.g1RssApi.dtos.AuthenticationUserDTO;
import com.g1RssApi.dtos.LoginResponseDTO;
import com.g1RssApi.dtos.RegisterUserDTO;
import com.g1RssApi.enuns.UserRole;
import com.g1RssApi.models.UserModel;
import com.g1RssApi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class AuthenticationService {

    @Autowired  // Injecao de dependencias
    private AuthenticationManager authenticationManager;

    @Autowired  // Injecao de dependencias
    private UserRepository userRepository;

    @Autowired  // Injecao de dependencias
    private TokenService tokenService;

    /**
     * Verifica se o login informado corresponde com algum login do
     * cadastrado no banco de dados.
     *
     * @param authenticationDTO
     * @return
     */
    public ResponseEntity login(AuthenticationUserDTO authenticationDTO) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDTO.login(), authenticationDTO.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = this.tokenService.generateToken((UserModel) auth.getPrincipal());
        return ResponseEntity.status(HttpStatus.OK).body(new LoginResponseDTO(token));
    }

    /**
     * Cadastra um novo usuario no banco de dados.
     *
     * @param registerUserDTO dto com informacoes do novo usuario.
     * @return ResponseEntity.OK se o usuario for validado e cadastrado
     * ou ResponseEntity.BAD_REQUEST se o login informado ja for utilizado.
     */
    public ResponseEntity register(RegisterUserDTO registerUserDTO) {
        if (this.userRepository.findByLogin(registerUserDTO.login()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        UserModel newUser = new UserModel();

        newUser.setName(registerUserDTO.name());

        newUser.setLogin(registerUserDTO.login());

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerUserDTO.password());
        newUser.setPassword(encryptedPassword);

        newUser.setEmail(registerUserDTO.email());

        newUser.setPhone(registerUserDTO.phone());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(registerUserDTO.birthdate(), formatter);
        newUser.setBirthdate(localDate);

        newUser.setRole(UserRole.USER);

        this.userRepository.save(newUser);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
