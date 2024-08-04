package com.g1RssApi.controllers;

import com.g1RssApi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired  // Injecao de dependencias
    private UserService userService;

    /**
     * Rota para inserir uma categoria na lista desejada pelo usuario.
     *
     * @param authorization token do usuario.
     * @param id            id da categoria.
     * @return ResponseEntity provido pelo UserService.
     */
    @PostMapping("/categories/{id}")
    public ResponseEntity setUserCategory(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization, @PathVariable("id") Long id) {
        return this.userService.setUserCategory(authorization, id);
    }

    /**
     * Rota para remover uma categoria da lista desejada do usuario.
     *
     * @param authorization token do usuario.
     * @param id            id da categoria.
     * @return ResponseEntity provida pelo UserService.
     */
    @DeleteMapping("/categories/{id}")
    public ResponseEntity removeUserCategory(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization, @PathVariable("id") Long id) {
        return this.userService.removeUserCategory(authorization, id);
    }

}
