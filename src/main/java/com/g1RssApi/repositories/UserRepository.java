package com.g1RssApi.repositories;

import com.g1RssApi.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

/**
 * @author Gilson Teixeira
 */
public interface UserRepository extends JpaRepository<UserModel, Long> {

    UserDetails findByLogin(String login);
    Optional<UserModel> findOptionalUserByLogin(String login);

}
