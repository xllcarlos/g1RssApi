package com.g1RssApi.services;

import com.g1RssApi.models.CategoryModel;
import com.g1RssApi.models.UserModel;
import com.g1RssApi.repositories.CategoryRepository;
import com.g1RssApi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired  // Injecao de dependencias
    private UserRepository userRepository;

    @Autowired  // Injecao de dependencias
    private TokenService tokenService;

    @Autowired  // Injecao de dependencias
    private CategoryRepository categoryRepository;

    /**
     * Insere uma categoria na lista de categorias desejadas de um usuario.
     *
     * @param authorization token do usuario.
     * @param id            id da categoria.
     * @return ResponseEntity.OK se a categoria for encontrada e adicionada na lista do usuario
     * ou ResponseEntity.BAD_REQUEST se nao forem encontradas informacoes do usuario ou da categoria.
     */
    public ResponseEntity setUserCategory(String authorization, Long id) {
        if (authorization == null || id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        authorization = authorization.replace("Bearer ", "");
        String login = this.tokenService.validateToken(authorization);
        UserDetails user = this.userRepository.findByLogin(login);
        Optional<UserModel> optionalUser = this.userRepository.findOptionalUserByLogin(user.getUsername());
        Optional<CategoryModel> optionalCategory = this.categoryRepository.findById(id);

        if (optionalCategory.isEmpty() || optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        UserModel userObject = optionalUser.get();
        CategoryModel categoryObject = optionalCategory.get();
        List<CategoryModel> userCategories = userObject.getCategories();
        userCategories.add(categoryObject);
        userObject.setCategories(userCategories);
        this.userRepository.save(userObject);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * Remove uma categoria a lista de categorias desejadas de um usuario.
     *
     * @param authorization token do usuario.
     * @param id            id da categoria.
     * @return ResponseEntity.OK se a categoria for encontrada e removida da lista do usuario
     * ou ResponseEntity.BAD_REQUEST se nao forem encontradas informacoes do usuario ou da categoria.
     */
    public ResponseEntity removeUserCategory(String authorization, Long id) {
        if (authorization == null || id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        authorization = authorization.replace("Bearer ", "");
        String login = this.tokenService.validateToken(authorization);
        UserDetails user = this.userRepository.findByLogin(login);
        Optional<UserModel> optionalUser = this.userRepository.findOptionalUserByLogin(user.getUsername());
        Optional<CategoryModel> optionalCategory = this.categoryRepository.findById(id);

        if (optionalCategory.isEmpty() || optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        UserModel userObject = optionalUser.get();
        CategoryModel categoryObject = optionalCategory.get();
        List<CategoryModel> userCategories = userObject.getCategories();
        for (CategoryModel category : userCategories) {
            int index = 0;
            if (category.getId() == categoryObject.getId()) {
                userCategories.remove(index);
                break;
            }
            index++;
        }
        userObject.setCategories(userCategories);
        this.userRepository.save(userObject);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
