package com.g1RssApi.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.g1RssApi.models.UserModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    /**
     * Gera um token para o usuario que realizou login.
     *
     * @param userModel usuario para ser criado um token.
     * @return String com token do usuario.
     */
    public String generateToken(UserModel userModel) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.secret);
            String token = JWT
                    .create()
                    .withIssuer("auth-api")
                    .withSubject(userModel.getLogin())
                    .withExpiresAt(this.generateExpirationDate())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while generate token: ", exception);
        }
    }

    /**
     * Valida um token recebido na requisicao.
     *
     * @param token String do token recebido na requisicao.
     * @return String do usuario refente ao token
     * ou String "" referente ao erro na validacao do token.
     */
    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.secret);
            return JWT
                    .require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTCreationException exception) {
            return "";
        }
    }

    /**
     * Gera o instante para o token expirar.
     *
     * @return Instante com tempo determinado.
     */
    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

}
