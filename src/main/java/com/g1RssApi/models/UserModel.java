package com.g1RssApi.models;

import com.g1RssApi.enuns.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Gilson Teixeira
 */
@Getter
@Setter
@Entity(name = "User")
@Table(name = "TB_USERS")
public class UserModel implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;  // Id do usuario

    @Column(name = "name", nullable = false)
    private String name;  // Nome do usuario

    @Column(name = "login", nullable = false, unique = true)
    private String login;  // Nome de login

    @Column(name = "password", nullable = false)
    private String password;  // Senha

    @Column(name = "email", nullable = false)
    private String email;  // Email

    @Column(name = "phone")
    private String phone;  // Telefone

    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;  // Data de nascimento

    @Column(name = "status", nullable = false)
    private Boolean status;  // Status do usuario

    @Column(name = "role", nullable = false)
    private UserRole role;  // Role do usuario

    @ManyToMany
    @JoinTable(
            name = "TB_USERS_CATEGORIES",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<CategoryModel> categories = new ArrayList<>();

    public UserModel() {
        this.status = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRole.ADMIN) {
            return List.of(
                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_USER")
            );
        }
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.status;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.status;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.status;
    }

    @Override
    public boolean isEnabled() {
        return this.status;
    }
}
